package org.kgromov;

import org.springframework.boot.SpringApplication;

// this is very fragile = since testcontainers traverses environment and PATh:
// split PATh by ';' and failed if path segment contains double quotes
public class TestSpringBootTestcontainersApplication {

    public static void main(String[] args) {
        SpringApplication.from(SpringBootTestcontainersApplication::main)
                .with(MysqlTestcontainersConfiguration.class)
                .withAdditionalProfiles("dev-test")
                .run(args);
    }

}
