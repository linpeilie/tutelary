package com.tutelary.example.inner;

public class InnerClassTest23_c {
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
