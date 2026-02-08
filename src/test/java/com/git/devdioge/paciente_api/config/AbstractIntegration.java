package com.git.devdioge.paciente_api.config;


import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.lifecycle.Startables;

import java.util.Map;
import java.util.stream.Stream;

@ContextConfiguration(initializers = AbstractIntegration.Initializers.class)
public class AbstractIntegration {


    static class Initializers implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        static MySQLContainer<?> mySQL = new MySQLContainer<>("mysql:8.0.33");

        private static void startContainers(){
            Startables.deepStart(Stream.of(mySQL)).join();
        }

        private static Map<String, String> createConnection() {
            return Map.of(
                    "spring.datasource.url",mySQL.getJdbcUrl(),
                    "spring.datasource.username",mySQL.getUsername(),
                    "spring.datasource.password",mySQL.getPassword()
            );
        }


        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            startContainers();
            ConfigurableEnvironment environment = applicationContext.getEnvironment();

            MapPropertySource containers = new MapPropertySource("testContainers",
                    (Map) createConnection());
            environment.getPropertySources().addFirst(containers);
        }
    }

}
