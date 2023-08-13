package com.tutelary.example.lambda;


import com.tutelary.example.support.UnaryFunction;
import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: lee
 * Date: 05/05/2011
 * Time: 18:48
 * To change this template use File | Settings | File Templates.
 */
public class LambdaTest1 {

    Integer invoker(int arg, UnaryFunction<Integer, Integer> fn) {
        return fn.invoke(arg);
    }

    public int test() {
        return invoker(3, x -> x + 1);
//        return 1;
    }

}
