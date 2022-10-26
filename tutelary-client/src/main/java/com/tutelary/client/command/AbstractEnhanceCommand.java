package com.tutelary.client.command;

import cn.hutool.core.io.FileUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
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
import com.tutelary.client.enhance.listener.AdviceListener;
import com.tutelary.client.enhance.interceptor.SpyInvokeInterceptor;
import com.tutelary.client.enhance.listener.InvokeTraceListener;
import com.tutelary.client.enhance.interceptor.SpyInterceptor;
import com.tutelary.client.loader.ClassLoaderWrapper;
import com.tutelary.client.util.ClassUtil;

import java.io.File;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
import java.util.*;

public abstract class AbstractEnhanceCommand<T> implements Command<T>, ClassFileTransformer {

    private static final Log LOG = LogFactory.get();

    private final Instrumentation inst;

    private boolean isTrace;

    protected AbstractEnhanceCommand(Instrumentation inst) {
        this.inst = inst;
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
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
                    for (Location location : locations) {
                        if (location instanceof MethodInsnNodeWare) {
                            MethodInsnNodeWare methodInsnNodeWare = (MethodInsnNodeWare) location;
                            MethodInsnNode methodInsnNode = methodInsnNodeWare.methodInsnNode();

                            // TODO
                            LOG.info("enhance success, owner : {}, methodName : {}, methodDesc : {}, listener : {}",
                                    methodInsnNode.owner, methodInsnNode.name, methodInsnNode.desc, processor.getClass().getName());
                        }
                    }
                } catch (Exception e) {
                    LOG.error("enhance error, class : {}, method : {}, interceptor : {}",
                            classNode.name, methodNode.name, processor.getClass().getName(), e);
                }
            }

            // enter/exist
            // TODO
        }

        byte[] enhanceClassByteArray = AsmUtils.toBytes(classNode, loader, classReader);

        // TODO
        dumpClassIfNecessary(className, enhanceClassByteArray);

        return enhanceClassByteArray;
    }

    /**
     * 执行命令
     *
     * @param param
     */
    @Override
    public void execute(T param) {
        Class<?> targetClass = ClassUtil.searchClass(inst, targetClass());

        // 校验类是否可以增强
        boolean canEnhance = targetClassCanEnhance(targetClass);
        if (!canEnhance) {
            // TODO
            return;
        }

        LOG.info("enhance class : [ {} ]", targetClass());

        AdviceListener listener = getListener();
        isTrace = listener instanceof InvokeTraceListener;

        synchronized (AbstractEnhanceCommand.class) {
            inst.addTransformer(this, true);
            try {
                inst.retransformClasses(targetClass);
            } catch (Exception e) {
                LOG.error("Enhance class [ {} ] error : {}", targetClass(), e);
                // TODO
            } finally {
                inst.removeTransformer(this);
            }

        }
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

    private void registerListener(AdviceListener adviceListener) {

    }

    private boolean targetClassCanEnhance(Class<?> clazz) {
        if (clazz == null) {
            return false;
        }
        if (!Objects.equals(clazz.getClassLoader(), ClassLoaderWrapper.getApplicationClassLoader())) {
            LOG.warn("class [ {} ] loaded by another ClassLoader [ {} ], cannot be enhanced", clazz.getName(),
                    clazz.getClassLoader() == null ? "BootstrapClassLoader" : clazz.getClassLoader().getClass().getName());
            return false;
        }
        if (ClassUtil.isLambdaClass(clazz)) {
            LOG.warn("class [ {} ] is lambda class");
            return false;
        }
        if (clazz.isInterface()) {
            LOG.warn("class [ {} ] is interface");
            return false;
        }
        return true;
    }

    private static void dumpClassIfNecessary(String className, byte[] data) {
        final File dumpClassFile = new File("./tutelary-class-dump/" + className + ".class");
        final File classPath = new File(dumpClassFile.getParent());

        // 创建类所在的包路径
        if (!classPath.mkdirs() && !classPath.exists()) {
            LOG.warn("create dump classpath:{} failed.", classPath);
            return;
        }

        // 将类字节码写入文件
        FileUtil.writeBytes(data, dumpClassFile);
        LOG.info("dump enhanced class: {}, path: {}", className, dumpClassFile);

    }

}
