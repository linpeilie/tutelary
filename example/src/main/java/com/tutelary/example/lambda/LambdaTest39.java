package com.tutelary.example.lambda;

import java.util.function.IntFunction;

public class LambdaTest39 {
    public static byte[] foo() {
        IntFunction<byte[]> factory = byte[]::new;
        return factory.apply(3);
    }
}
