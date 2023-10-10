package com.tutelary.example;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.RandomUtil;
import com.tutelary.example.domain.StaticDomain;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GameService {

    private static StaticDomain staticDomain;

    private static List<Integer> list = new ArrayList<>();

    private static Map<Integer, String> map = new HashMap<>();

    static {
        staticDomain = new StaticDomain();
        staticDomain.setI(RandomUtil.randomInt());
        staticDomain.setStr(RandomUtil.randomString(9));
        staticDomain.setL(RandomUtil.randomLong());
        staticDomain.setD(RandomUtil.randomDouble());

        log.info("static domain : {}", staticDomain);

        int loop = RandomUtil.randomInt(5, 10);
        for (int i = 0; i < loop; i++) {
            list.add(RandomUtil.randomInt());
        }

        log.info("random int list : {}", list);

        for (int i = 0; i < loop; i++) {
            map.put(RandomUtil.randomInt(), RandomUtil.randomString(8));
        }

        log.info("random map : {}", map);

    }

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
