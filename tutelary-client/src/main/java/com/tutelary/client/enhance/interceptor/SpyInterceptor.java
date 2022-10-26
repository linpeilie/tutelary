package com.tutelary.client.enhance.interceptor;

import com.alibaba.bytekit.asm.binding.Binding;
import com.alibaba.bytekit.asm.interceptor.annotation.AtEnter;
import com.alibaba.bytekit.asm.interceptor.annotation.AtExceptionExit;
import com.alibaba.bytekit.asm.interceptor.annotation.AtExit;
import java.tutelary.WeaveSpy;

public class SpyInterceptor {

    @AtEnter(inline = true)
    public static void atEnter(@Binding.This Object target,
                               @Binding.Class Class<?> clazz,
                               @Binding.MethodInfo String methodInfo,
                               @Binding.Args Object[] args) {
        WeaveSpy.atEnter(clazz, methodInfo, target, args);
    }

    @AtExit(inline = true)
    public static void atExit(@Binding.This Object target,
                              @Binding.Class Class<?> clazz,
                              @Binding.MethodInfo String methodInfo,
                              @Binding.Args Object[] args,
                              @Binding.Return Object returnObj) {
        WeaveSpy.atExit(clazz, methodInfo, target, args, returnObj);
    }

    @AtExceptionExit(inline = true)
    public static void atExceptionExit(@Binding.This Object target,
                                       @Binding.Class Class<?> clazz,
                                       @Binding.MethodInfo String methodInfo,
                                       @Binding.Args Object[] args,
                                       @Binding.Throwable Throwable throwable) {
        WeaveSpy.atExceptionExit(clazz, methodInfo, target, args, throwable);
    }

}
