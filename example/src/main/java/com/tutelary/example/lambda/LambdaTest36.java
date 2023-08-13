package com.tutelary.example.lambda;

import java.util.concurrent.ExecutorService;
import java.util.function.Supplier;

public class LambdaTest36 {

    private ExecutorService executorService;

    public void doThing(final double d) {
        executorService.submit(() -> {
            new Thread(){

                @Override
                public void run() {
                    executorService.submit(() -> {
                        System.out.println("" + d);
                    });
                }
            }.start();
        });
    }
}
