package com.RodCarvalhoas.BackeryStock.config;

import com.RodCarvalhoas.BackeryStock.service.DBservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class DevConfig {

    @Autowired
    private DBservice dBservice;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String strategy;

    @Bean
    public boolean instanciaBancoDeDados(){
        if(strategy.equals("create")){
            this.dBservice.instanciaBaseDeDados();
        }
        return false;
    }
}
