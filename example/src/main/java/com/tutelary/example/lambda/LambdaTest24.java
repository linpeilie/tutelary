package com.tutelary.example.lambda;

import com.tutelary.example.support.SetFactory;
import java.util.Set;
import java.util.stream.Stream;

public class LambdaTest24 {
    public void test(Stream<String> in) {
        Set<String> s = SetFactory.newSet();
        s.getClass();
        Stream<Boolean> numbers = in.map(s::contains);
    }
}
