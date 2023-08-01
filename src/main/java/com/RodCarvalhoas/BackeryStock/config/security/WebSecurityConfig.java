package com.RodCarvalhoas.BackeryStock.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
       http
               .csrf().disable()
               .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
               .authorizeHttpRequests()
               .requestMatchers("/auth/login").permitAll()
               .requestMatchers(HttpMethod.POST, "/categorias").hasRole("ADMIN")
               .requestMatchers(HttpMethod.DELETE, "/categorias/**").hasRole("ADMIN")
               .requestMatchers(HttpMethod.PUT, "/categorias/**").hasRole("ADMIN")
               .requestMatchers(HttpMethod.GET, "/categorias/**").permitAll()//hasRole("USER")
               .requestMatchers(HttpMethod.POST, "/item").hasRole("ADMIN")
               .requestMatchers(HttpMethod.DELETE, "/item/**").hasRole("ADMIN")
               .requestMatchers(HttpMethod.PUT, "/item/**").hasRole("ADMIN")
               .requestMatchers(HttpMethod.PATCH, "/item/**").hasRole("ADMIN")
               .requestMatchers(HttpMethod.GET, "/item/**").hasRole("USER")
               .requestMatchers("/Usuario/**").hasRole("ADMIN")
               .requestMatchers("/h2-console/**").permitAll()
               .anyRequest().permitAll()
               .and()
               .formLogin()
               .and()
               .headers().frameOptions().sameOrigin()
               .and()
               .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
       return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}

