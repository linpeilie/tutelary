package cn.easii.tutelary.client.decompile;

import cn.easii.tutelary.client.core.compiler.CharSequenceCompiler;
import cn.hutool.core.io.FileUtil;
import java.io.File;
import org.junit.jupiter.api.Test;

public class CharSequenceCompilerTest {

    @Test
    public void quickStart() throws Exception {
        String quickStartStr = "package com.tutelary.client.decompile;\n" +
                               "\n" +
                               "public class QuickStart {\n" +
                               "\n" +
                               "    public static int run() {\n" +
                               "        System.out.println(\"hello world\");\n" +
                               "        return 101;\n" +
                               "    }\n" +
                               "\n" +
                               "}";
        CharSequenceCompiler compiler = new CharSequenceCompiler(Thread.currentThread().getContextClassLoader());
        byte[] bytes = compiler.compile("com.tutelary.client.decompile.QuickStart", quickStartStr);

        // write to .class file
        String compilerClassFolder = FileUtil.getUserHomePath();
        compilerClassFolder = compilerClassFolder + File.separator + "QuickStart$1.class";

        File file = new File(compilerClassFolder);
        FileUtil.writeBytes(bytes, file);
    }

}
