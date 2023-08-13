package com.tutelary.example.lambda;




import com.tutelary.example.support.UnaryFunction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: lee
 * Date: 05/05/2011
 * Time: 18:48
 * To change this template use File | Settings | File Templates.
 */
public class LambdaTest5a {



    static <T,R> void map(UnaryFunction<T,R> function, List<T> source) {
        List<R> destiny = new ArrayList<>();
        for (T item : source) {
            R value = function.invoke(item);
            destiny.add(value);
        }
    }

}
