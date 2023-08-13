package com.tutelary.example.inner;

import java.util.function.Function;

public class InnerClassTest54d {
    public static void main(String[] args) {
        // CFR doesn't decompile inner class
        class Mess {
            String a = "hello world";
        }

        Function<Object, String> f = x -> new Mess().a;
        System.out.println(f.apply(0));
        
        System.out.println(new Mess().a);
    }

}
