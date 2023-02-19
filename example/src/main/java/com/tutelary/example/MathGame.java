package com.tutelary.example;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.RandomUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class MathGame {
    private static Random random = new Random();

    private int illegalArgumentCount = 0;

    private static final int THREAD_SIZE = 10;

    private final ExecutorService executorService =
        new ThreadPoolExecutor(THREAD_SIZE, THREAD_SIZE, 1, TimeUnit.DAYS, new LinkedBlockingQueue<>(),
                               ThreadFactoryBuilder.create().setNamePrefix("math-game-").build());

    @PostConstruct
    public void start() {
        for (int i = 0; i < THREAD_SIZE; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        exec();
                        ThreadUtil.sleep(1);
                    }
                }
            });
        }
    }

    public synchronized void exec() {
        try {
            int number = random.nextInt() / 10000;
            List<Integer> primeFactors = primeFactors(number);
            print(number, primeFactors);
            Game.pause();
            int len = RandomUtil.randomInt(1000, 2000000);
            String[] arr = new String[len];
            for (int i = 0; i < len; i++) {
                arr[i] = RandomUtil.randomString(RandomUtil.randomInt(5, 15));
            }
        } catch (Exception e) {
//            System.out.println(String.format("illegalArgumentCount:%3d, ", illegalArgumentCount) + e.getMessage());
        }
    }

    public static void print(int number, List<Integer> primeFactors) {
        StringBuffer sb = new StringBuffer(number + "=");
        for (int factor : primeFactors) {
            sb.append(factor).append('*');
        }
        if (sb.charAt(sb.length() - 1) == '*') {
            sb.deleteCharAt(sb.length() - 1);
        }
//        System.out.println(sb);
    }

    public List<Integer> primeFactors(int number) {
        if (number < 2) {
            illegalArgumentCount++;
            throw new IllegalArgumentException("number is: " + number + ", need >= 2");
        }

        List<Integer> result = new ArrayList<Integer>();
        int i = 2;
        while (i <= number) {
            if (number % i == 0) {
                result.add(i);
                number = number / i;
                i = 2;
            } else {
                i++;
            }
        }

        return result;
    }
}
