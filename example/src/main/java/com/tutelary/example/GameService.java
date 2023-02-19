package com.tutelary.example;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.RandomUtil;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    public void pause() {
        ThreadUtil.safeSleep(200);
    }

    public void throwException() {
        ThreadUtil.safeSleep(RandomUtil.randomInt(10, 100));
        throw new RuntimeException("抛出异常");
    }

    public void recursion(int n) {
        if (n > 2) {
            return;
        }
        pause();
        recursion(n + 1);
    }

}
