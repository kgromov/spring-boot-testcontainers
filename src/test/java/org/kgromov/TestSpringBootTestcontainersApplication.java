package org.kgromov;

import org.springframework.boot.SpringApplication;

public class TestSpringBootTestcontainersApplication {

    public static void main(String[] args) {
        SpringApplication.from(SpringBootTestcontainersApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
