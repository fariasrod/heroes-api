package com.heroes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
public class HeroesApplication {
    public static void main(String[] args) {
        SpringApplication.run(HeroesApplication.class, args);
    }
}
