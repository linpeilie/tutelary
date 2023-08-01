package com.tutelary.client.command;

import cn.hutool.core.io.FileUtil;
import com.alibaba.bytekit.asm.MethodProcessor;
import com.alibaba.bytekit.asm.interceptor.InterceptorProcessor;
import com.alibaba.bytekit.asm.interceptor.parser.DefaultInterceptorClassParser;
import com.alibaba.bytekit.asm.location.Location;
import com.alibaba.bytekit.asm.location.MethodInsnNodeWare;
import com.alibaba.bytekit.utils.AsmUtils;
import com.alibaba.deps.org.objectweb.asm.ClassReader;
import com.alibaba.deps.org.objectweb.asm.Opcodes;
import com.alibaba.deps.org.objectweb.asm.tree.ClassNode;
import com.alibaba.deps.org.objectweb.asm.tree.MethodInsnNode;
import com.alibaba.deps.org.objectweb.asm.tree.MethodNode;
import com.tutelary.client.constants.EnhanceResponseCode;
import com.tutelary.client.core.file.FileManager;
import com.tutelary.client.enhance.callback.CompletionHandler;
import com.tutelary.client.enhance.callback.RCallback;
import com.tutelary.client.enhance.interceptor.SpyInterceptor;
import com.tutelary.client.enhance.interceptor.SpyInvokeInterceptor;
import com.tutelary.client.enhance.listener.AdviceListener;
import com.tutelary.client.enhance.listener.AdviceListenerManager;
import com.tutelary.client.enhance.listener.InvokeTraceListener;
import com.tutelary.client.exception.EnhanceNotAllowedException;
import com.tutelary.client.loader.ClassLoaderWrapper;
import com.tutelary.common.exception.BaseException;
import com.tutelary.common.log.Log;
import com.tutelary.common.log.LogFactory;
import com.tutelary.common.utils.ClassUtil;
import com.tutelary.message.command.result.EnhanceAffect;
import java.io.File;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public abstract class AbstractEnhanceCommand<Param, Result> implements Command<EnhanceAffect>, ClassFileTransformer {

    private static final Log LOGGER = LogFactory.get(AbstractEnhanceCommand.class);
    protected final CompletableFuture<EnhanceAffect> future;
    protected final AdviceListener listener;
    protected final EnhanceAffect enhanceAffect;
    private final Instrumentation inst;
    protected RCallback<Result> rCallback;
    protected CompletionHandler completionHandler;
    private boolean isTrace;

    protected AbstractEnhanceCommand(Instrumentation inst) {
        this.inst = inst;
        future = new CompletableFuture<>();
        listener = getListener();
        isTrace = listener instanceof InvokeTraceListener;
        enhanceAffect = new EnhanceAffect();
    }

    private static void dumpClassIfNecessary(String className, byte[] data) {
        final File dumpClassFile =
            new File(FileManager.enhanceClassDumpFolder() + File.separator + className + ".class");
        final File classPath = new File(dumpClassFile.getParent());

        // 创建类所在的包路径
        if (!classPath.mkdirs() && !classPath.exists()) {
            LOGGER.warn("create dump classpath:{} failed.", classPath);
            return;
        }

        // 将类字节码写入文件
        FileUtil.writeBytes(data, dumpClassFile);
        LOGGER.info("dump enhanced class: {}, path: {}", className, dumpClassFile);

    }

    @Override
    public byte[] transform(ClassLoader loader,
        String className,
        Class<?> classBeingRedefined,
        ProtectionDomain protectionDomain,
        byte[] classfileBuffer) throws IllegalClassFormatException {
        try {
            ClassNode classNode = new ClassNode(Opcodes.ASM9);
            ClassReader classReader = AsmUtils.toClassNode(classfileBuffer, classNode);
            // remove jsr
            classNode = AsmUtils.removeJSRInstructions(classNode);

            DefaultInterceptorClassParser interceptorClassParser = new DefaultInterceptorClassParser();

            final List<InterceptorProcessor> processors = new ArrayList<>();

            processors.addAll(interceptorClassParser.parse(SpyInterceptor.class));
            if (isTrace) {
                processors.addAll(interceptorClassParser.parse(SpyInvokeInterceptor.class));
            }

            List<String> targetMethods = targetMethods();
            List<MethodNode> methodNodes = new ArrayList<>();
            for (MethodNode method : classNode.methods) {
                if (targetMethods.contains(method.name)) {
                    methodNodes.add(method);
                }
            }

            for (MethodNode methodNode : methodNodes) {
                MethodProcessor methodProcessor = new MethodProcessor(classNode, methodNode);
                for (InterceptorProcessor processor : processors) {
                    try {
                        List<Location> locations = processor.process(methodProcessor);
                        if (isTrace) {
                            for (Location location : locations) {
                                if (location instanceof MethodInsnNodeWare) {
                                    MethodInsnNodeWare methodInsnNodeWare = (MethodInsnNodeWare) location;
                                    MethodInsnNode methodInsnNode = methodInsnNodeWare.methodInsnNode();

                                    LOGGER.info(
                                        "enhance success, owner : {}, methodName : {}, methodDesc : {}, listener : {}",
                                        methodInsnNode.owner, methodInsnNode.name, methodInsnNode.desc,
                                        processor.getClass().getName()
                                    );
                                    AdviceListenerManager.registerTraceAdviceListener(
                                        loader, className, methodInsnNode.owner, methodInsnNode.name,
                                        methodInsnNode.desc, listener
                                    );
                                }
                            }
                        }
                    } catch (Exception e) {
                        LOGGER.error(
                            "enhance error, class : {}, method : {}, interceptor : {}", classNode.name, methodNode.name,
                            processor.getClass().getName(), e
                        );
                    }
                }

                AdviceListenerManager.registerAdviceListener(
                    loader, className, methodNode.name, methodNode.desc, listener);
                enhanceAffect.addMethodAndCount(loader, className, methodNode.name, methodNode.desc);
            }

            byte[] enhanceClassByteArray = AsmUtils.toBytes(classNode, loader, classReader);

            // TODO
            dumpClassIfNecessary(className, enhanceClassByteArray);

            enhanceAffect.cCnt();

            return enhanceClassByteArray;
        } catch (Throwable e) {
            LOGGER.error("transform loader[ {} ] class [ {} ] failed", loader, className, e);
            enhanceAffect.failed(e.getMessage());
        }
        return classfileBuffer;
    }

    /**
     * 执行命令
     */
    @Override
    public EnhanceAffect execute() {
        try {
            enhance();
        } catch (BaseException e) {
            enhanceAffect.failed(e.getErrorMessage());
        }
        return enhanceAffect;
    }

    private EnhanceAffect enhance() {
        Class<?> targetClass = ClassUtil.searchClass(inst, targetClass());

        // 校验类是否可以增强
        targetClassCanEnhance(targetClass);

        LOGGER.info("enhance class : [ {} ]", targetClass());

        synchronized (AbstractEnhanceCommand.class) {
            inst.addTransformer(this, true);
            listener.create();
            try {
                inst.retransformClasses(targetClass);
            } catch (Exception e) {
                LOGGER.error("Enhance class [ {} ] error : {}", targetClass(), e);
                // TODO
            } finally {
                inst.removeTransformer(this);
            }
        }
        return enhanceAffect;
    }

    @Override
    public void terminated() {
        // TODO:中断命令
        future.cancel(false);
    }

    protected abstract AdviceListener getListener();

    /**
     * 目标类
     */
    protected abstract String targetClass();

    /**
     * 目标方法集合
     */
    protected abstract List<String> targetMethods();

    private void targetClassCanEnhance(Class<?> clazz) {
        if (clazz == null) {
            throw new EnhanceNotAllowedException(EnhanceResponseCode.ENHANCE_CLASS_IS_NULL);
        }
        if (!Objects.equals(clazz.getClassLoader(), ClassLoaderWrapper.getApplicationClassLoader())) {
            LOGGER.warn(
                "class [ {} ] loaded by another ClassLoader [ {} ], cannot be enhanced", clazz.getName(),
                clazz.getClassLoader() == null ? "BootstrapClassLoader" : clazz.getClassLoader()
                    .getClass()
                    .getName()
            );
            throw new EnhanceNotAllowedException(EnhanceResponseCode.ENHANCE_CLASS_NOT_FOUND_BY_CLASSLOADER,
                ClassLoaderWrapper.getApplicationClassLoader());
        }
        if (ClassUtil.isLambdaClass(clazz)) {
            LOGGER.warn("class [ {} ] is lambda class");
            throw new EnhanceNotAllowedException(EnhanceResponseCode.INTERFACE_CANNOT_BE_ENHANCED);
        }
        if (clazz.isInterface()) {
            LOGGER.warn("class [ {} ] is interface");
            throw new EnhanceNotAllowedException(EnhanceResponseCode.INTERFACE_CANNOT_BE_ENHANCED);
        }
    }

    public void registerResultCallback(RCallback<Result> callback) {
        this.rCallback = callback;
    }

    protected void callbackResult(Result result) {
        if (rCallback != null) {
            rCallback.callback(result);
        }
    }

    public void completionHandler(CompletionHandler completionHandler) {
        this.completionHandler = completionHandler;
    }

    public void complete() {
        if (completionHandler != null) {
            completionHandler.handle();
        }
        // TODO
        AdviceListenerManager.removeListener(listener);
    }

}
