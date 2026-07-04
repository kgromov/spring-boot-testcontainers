package org.kgromov;

import org.springframework.boot.devtools.restart.RestartScope;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;


@TestConfiguration(proxyBeanMethods = false)
class MysqlTestcontainersConfiguration {

    @Profile("dev-test")
    @RestartScope
    @Bean
    @ServiceConnection
    static MySQLContainer<?> mysqlContainer() {
        return new MySQLContainer<>(DockerImageName.parse("mysql:8.0.29"))
                .withReuse(true);  // keep container running after test execution - ryuk is skipped
    }

    @Container
    @ServiceConnection
    static MySQLContainer<?> mysqlContainer = mysqlContainer();


}
