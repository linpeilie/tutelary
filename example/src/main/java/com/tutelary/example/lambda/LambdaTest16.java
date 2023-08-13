package com.tutelary.example.lambda;

import java.util.stream.Stream;

// Samuel Dennis Borlongan
public class LambdaTest16 {
    public void foo(Stream<String> s) {
        s.toArray(String[]::new);
    }
}
