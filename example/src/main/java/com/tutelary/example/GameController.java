package com.tutelary.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

    @Autowired
    private GameService gameService;

    @GetMapping(value = "hello")
    public String hello() {
        gameService.pause();
        try {
            gameService.throwException();
        } catch (Exception e) {
            // ignore
        }
        gameService.recursion(0);
        return "Ok";
    }

}
