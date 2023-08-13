package com.tutelary.example.lambda;

import java.util.function.IntFunction;

public class LambdaTest39a {
    public static byte[] foo() {
        IntFunction<byte[]> factory = x -> new byte[x];
        return factory.apply(3);
    }
}
