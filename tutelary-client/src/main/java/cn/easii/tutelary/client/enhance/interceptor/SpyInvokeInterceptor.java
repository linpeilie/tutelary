package cn.easii.tutelary.client.enhance.interceptor;

import com.alibaba.bytekit.asm.binding.Binding;
import com.alibaba.bytekit.asm.interceptor.annotation.AtInvoke;
import com.alibaba.bytekit.asm.interceptor.annotation.AtInvokeException;
import java.tutelary.WeaveSpy;

public class SpyInvokeInterceptor {

    @AtInvoke(name = "", inline = true, whenComplete = false, excludes = {
        "java.tutelary.WeaveSpy",
        "java.lang.Byte",
        "java.lang.Boolean",
        "java.lang.Short",
        "java.lang.Character",
        "java.lang.Integer",
        "java.lang.Float",
        "java.lang.Long",
        "java.lang.Double"
    })
    public static void onInvokeBefore(@Binding.This Object target,
        @Binding.Class Class<?> clazz,
        @Binding.InvokeInfo String invokeInfo) {
        WeaveSpy.atBeforeInvoke(clazz, invokeInfo, target);
    }

    @AtInvoke(name = "", inline = true, whenComplete = true, excludes = {
        "java.tutelary.WeaveSpy",
        "java.lang.Byte",
        "java.lang.Boolean",
        "java.lang.Short",
        "java.lang.Character",
        "java.lang.Integer",
        "java.lang.Float",
        "java.lang.Long",
        "java.lang.Double"
    })
    public static void onInvokeAfter(@Binding.This Object target,
        @Binding.Class Class<?> clazz,
        @Binding.InvokeInfo String invokeInfo) {
        WeaveSpy.atAfterInvoke(clazz, invokeInfo, target);
    }

    @AtInvokeException(name = "", inline = true, excludes = {
        "java.tutelary.WeaveSpy",
        "java.lang.Byte",
        "java.lang.Boolean",
        "java.lang.Short",
        "java.lang.Character",
        "java.lang.Integer",
        "java.lang.Float",
        "java.lang.Long",
        "java.lang.Double"
    })
    public static void onInvokeException(@Binding.This Object target,
        @Binding.Class Class<?> clazz,
        @Binding.InvokeInfo String invokeInfo,
        @Binding.Throwable Throwable throwable) {
        WeaveSpy.atInvokeException(clazz, invokeInfo, target, throwable);
    }

}
