package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//Moved to config for testing purposes

@Configuration
@EnableJpaRepositories(basePackages = {"com.example.demo.repository"})
public class GlobalConfiguration {

}

