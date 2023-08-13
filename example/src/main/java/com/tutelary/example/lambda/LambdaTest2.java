package com.tutelary.example.lambda;

import com.tutelary.example.support.UnaryFunction;

/**
 * Created by IntelliJ IDEA.
 * User: lee
 * Date: 05/05/2011
 * Time: 18:48
 * To change this template use File | Settings | File Templates.
 */
public class LambdaTest2 {

    Integer invoker(int arg, UnaryFunction<Integer, Integer> fn) {
        return fn.invoke(arg);
    }

    public int test(int y) {
        return invoker(3, x -> x + y + 1);
//        return 3;
    }

}
