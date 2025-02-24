package org.kgromov;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;


@TestConfiguration(proxyBeanMethods = false)
class MysqlTestcontainersConfiguration {

    @Container
    @ServiceConnection
    static  MySQLContainer<?> mysqlContainer = new MySQLContainer<>(DockerImageName.parse("mysql:latest"))
            .withReuse(true);

//    @Bean
////    @Profile("mysql")
//    @ServiceConnection
//     MySQLContainer<?> mysqlContainer() {
//        return new MySQLContainer<>(DockerImageName.parse("mysql:latest"))
//                .withReuse(true);
//    }

}
