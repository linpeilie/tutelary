package com.tutelary.example.lambda;

import java.util.function.Supplier;

/**
 * Created with IntelliJ IDEA.
 * User: lee
 * Date: 20/08/2013
 * Time: 18:36
 */
@FunctionalInterface
public interface LambdaRecursiveSupplier extends Supplier<LambdaRecursiveSupplier> {
}
