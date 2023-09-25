package com.tutelary.client.command.decompile;

import cn.hutool.core.collection.CollectionUtil;
import com.tutelary.client.command.Command;
import com.tutelary.client.enhance.transformer.ClassDumpTransformer;
import com.tutelary.client.util.Decompiler;
import com.tutelary.client.util.InstrumentationUtils;
import com.tutelary.common.log.Log;
import com.tutelary.common.log.LogFactory;
import com.tutelary.common.utils.ClassUtil;
import com.tutelary.message.command.param.DecompileRequest;
import com.tutelary.message.command.result.DecompileResponse;
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
        final Class<?> targetClass = ClassUtil.searchClass(inst, param.getClassName());
        if (targetClass == null) {
            LOG.warn("decompile class failure, target class : {} was not found", param.getClassName());
            decompileResponse.failed(param.getClassName() + " was not found.");
            return decompileResponse;
        }

        final HashSet<Class<?>> classes = CollectionUtil.newHashSet(targetClass);
        // interClass
        final Set<Class<?>> interClass = ClassUtil.fuzzySearchClass(inst, param.getClassName() + "\\$.*");
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
