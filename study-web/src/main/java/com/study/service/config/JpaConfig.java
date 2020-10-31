package com.study.service.config;


import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = {"com.study.service"})
@EnableConfigurationProperties({JpaProperties.class, HibernateProperties.class})
public class JpaConfig {
}
