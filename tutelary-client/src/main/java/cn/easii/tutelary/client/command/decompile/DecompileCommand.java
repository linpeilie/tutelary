package cn.easii.tutelary.client.command.decompile;

import cn.easii.tutelary.client.enhance.transformer.ClassDumpTransformer;
import cn.easii.tutelary.common.log.Log;
import cn.easii.tutelary.common.log.LogFactory;
import cn.easii.tutelary.common.utils.ClassUtil;
import cn.easii.tutelary.message.command.param.DecompileRequest;
import cn.easii.tutelary.message.command.result.DecompileResponse;
import cn.hutool.core.collection.CollectionUtil;
import cn.easii.tutelary.client.command.Command;
import cn.easii.tutelary.client.util.Decompiler;
import cn.easii.tutelary.client.util.InstrumentationUtils;
import java.io.File;
import java.lang.instrument.Instrumentation;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DecompileCommand implements Command<DecompileResponse> {

    private static final Log LOG = LogFactory.get(DecompileCommand.class);

    private final DecompileRequest param;
    private final Instrumentation inst;

    public DecompileCommand(final DecompileRequest param, final Instrumentation inst) {
        this.param = param;
        this.inst = inst;
    }

    @Override
    public DecompileResponse execute() {
        final DecompileResponse decompileResponse = new DecompileResponse();
        final Class<?> targetClass = ClassUtil.searchClass(inst, param.getQualifiedClassName());
        if (targetClass == null) {
            LOG.warn("decompile class failure, target class : {} was not found", param.getQualifiedClassName());
            decompileResponse.failed(param.getQualifiedClassName() + " was not found.");
            return decompileResponse;
        }

        final HashSet<Class<?>> classes = CollectionUtil.newHashSet(targetClass);
        // interClass
        final Set<Class<?>> interClass = ClassUtil.fuzzySearchClass(inst, param.getQualifiedClassName() + "\\$.*");
        classes.addAll(interClass);

        final ClassDumpTransformer classDumpTransformer =
            new ClassDumpTransformer(classes);
        InstrumentationUtils.retransformClasses(inst, classDumpTransformer, classes);

        final Map<Class<?>, File> classFileMap = classDumpTransformer.getDumpResult();
        final File classFile = classFileMap.get(targetClass);

        final String source = Decompiler.decompile(classFile.getAbsolutePath(), param.getMethodName());

        decompileResponse.setSource(source);

        return decompileResponse;
    }

    @Override
    public void terminated() {

    }
}
