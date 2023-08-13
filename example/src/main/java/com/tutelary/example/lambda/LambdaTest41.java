package com.tutelary.example.lambda;

import java.util.function.Consumer;

public class LambdaTest41 {
    Consumer<?> test1() {
        return (String a) -> {};
    }

    Consumer<?> test2() {
        return a -> {};
    }
}
