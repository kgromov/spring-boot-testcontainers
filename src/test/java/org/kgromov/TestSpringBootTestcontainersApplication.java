package org.kgromov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

public class TestSpringBootTestcontainersApplication {

    public static void main(String[] args) {
        SpringApplication.from(SpringBootTestcontainersApplication::main)
                .with(MysqlTestcontainersConfiguration.class)
                .run(args);

//        new SpringApplicationBuilder(SpringBootTestcontainersApplication.class) //
//                .profiles("mysql")
//                .run(args);
    }

}
