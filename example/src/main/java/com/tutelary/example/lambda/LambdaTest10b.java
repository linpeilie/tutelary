package com.tutelary.example.lambda;


import com.tutelary.example.support.SetFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

/**
 * Created by IntelliJ IDEA.
 * User: lee
 * Date: 05/05/2011
 * Time: 18:48
 * To change this template use File | Settings | File Templates.
 */
public class LambdaTest10b {

    static <T,R> List<R> map(Function<T,R> function, List<T> source) {
        List<R> destiny = new ArrayList<>();
        for (T item : source) {
            R value = function.apply(item);
            destiny.add(value);
        }
        return destiny;
    }

    public void test() {
        List<String> digits = Arrays.<String>asList("1", "2", "3", "4", "5");
        Set<String> s = SetFactory.newSet();
        List<Boolean> numbers = map(s::add, digits);
    }

}
