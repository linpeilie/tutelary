package com.tutelary.example.lambda;

import java.util.function.IntFunction;

public class LambdaTest40c {
    public static LambdaTest40c[][] foo() {
        IntFunction<LambdaTest40c[][]> factory = x -> new LambdaTest40c[14][x];
        return factory.apply(3);
    }
}
