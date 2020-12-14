package com.n8id.jerseyBody;

import java.util.*;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author mng
 */
@EnableCaching
@ComponentScan(basePackages = { "com.n8id.jerseyBody" })
@EntityScan(basePackages = { "com.n8id.jerseyBody" })
@SpringBootApplication(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
// @EnableJpaRepositories(basePackages={"com.n8id.elm.core"})
public class Startup extends SpringBootServletInitializer {
    private static Logger log = LogManager.getLogger(Startup.class);
    private static ConfigurableApplicationContext REST_SERVER;

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(Startup.class);
    }

    public static void main(String[] args) throws Exception {
        log.info("Test is starting up.");
        java.util.Locale.setDefault(Locale.US);

        REST_SERVER = SpringApplication.run(Startup.class, args);
    }


    /**
     * shutdown cron jobs -> rest server
     */
    public static void shutDown() {
        REST_SERVER.close();
    }
}