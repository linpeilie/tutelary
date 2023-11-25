package cn.easii.tutelary.client.command.retransform;

import cn.easii.tutelary.common.utils.ClassUtil;
import cn.easii.tutelary.client.command.Command;
import cn.easii.tutelary.client.core.compiler.CharSequenceCompiler;
import cn.easii.tutelary.message.command.param.RetransformRequest;
import cn.easii.tutelary.message.command.result.RetransformResponse;
import java.lang.instrument.Instrumentation;

public class RetransformCommand implements Command<RetransformResponse> {

    private final RetransformRequest param;

    private final Instrumentation inst;

    public RetransformCommand(RetransformRequest param, Instrumentation inst) {
        this.param = param;
        this.inst = inst;
    }

    @Override
    public RetransformResponse execute() {
        RetransformResponse retransformResponse = new RetransformResponse();
        // find the class that want to retransform
        Class<?> targetClass = ClassUtil.searchClass(inst, param.getQualifiedClassName());
        if (targetClass == null) {
            retransformResponse.failed("class not found, class name : " + param.getQualifiedClassName());
            return retransformResponse;
        }

        // compile java source
        CharSequenceCompiler compiler = new CharSequenceCompiler(targetClass.getClassLoader());

        // compile java source char sequence
        byte[] bytes = compiler.compile(param.getQualifiedClassName(), param.getJavaSource());


        return retransformResponse;
    }

    @Override
    public void terminated() {

    }

}
