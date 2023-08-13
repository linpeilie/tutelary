package com.tutelary.example.inner;

import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class LoadInner {

    @PostConstruct
    public void start() throws ClassNotFoundException {
        new InnerClassTest23_b().test(true);
    }

}
