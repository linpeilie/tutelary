package com.tutelary.example.inner;

import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class LoadInner {

    @PostConstruct
    public void start() throws ClassNotFoundException {
        new InnerClassTest23_b().test(true);
        new InnerClassTest23_c();
        new InnerClassTest23_Strobel();
        new InnerClassTest46();
    }

}
