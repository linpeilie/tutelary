package com.tutelary.client.decompile;

import com.tutelary.client.util.Decompiler;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import org.junit.jupiter.api.Test;

public class DecompilerTest {

    @Test
    public void test() throws IOException {
        String dir = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();

        File classFile = new File(dir, this.getClass().getName().replace('.', '/') + ".class");

        String code = Decompiler.decompile(classFile.getAbsolutePath(), null);

        System.err.println(code);
    }


    public void aaa() {

        int jjj = 0;

        for (int i = 0; i < 100; ++i) {
            System.err.println(i);
        }
    }

    public void bbb(final boolean b) {
        class MethodScopedIterable implements Iterable<String> {
            private final boolean y = b;

            @Override
            public Iterator<String> iterator() {
                return new Iterator<String>() {
                    @Override
                    public boolean hasNext() {
                        return MethodScopedIterable.this.y;
                    }

                    @Override
                    public String next() {
                        return null;
                    }

                    @Override
                    public void remove() {
                    }
                };
            }
        }
        new MethodScopedIterable();
    }

    private static class C {
        public static void test() {
            final C c = null;
            c.new A(6);
        }

        class A {
            int j;

            A(final int j) {
                super();
                this.j = j;
            }
        }
    }

}
