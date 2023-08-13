package com.tutelary.example.lambda;

import java.util.stream.Stream;

// Samuel Dennis Borlongan
public class LambdaTest18 {
    public void foo(Stream<String> s) {
        s.toArray(x -> new String[x]);
    }
}
