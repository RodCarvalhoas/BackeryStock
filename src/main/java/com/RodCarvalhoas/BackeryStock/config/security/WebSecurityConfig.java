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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

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
               .requestMatchers(HttpMethod.POST, "/categorias").permitAll() //.hasRole("ADMIN")
               .requestMatchers(HttpMethod.DELETE, "/categorias/**").permitAll() //hasRole("ADMIN")
               .requestMatchers(HttpMethod.PUT, "/categorias/**").permitAll() //hasRole("ADMIN")
               .requestMatchers(HttpMethod.GET, "/categorias/**").permitAll() //.hasRole("ADMIN")
               .requestMatchers(HttpMethod.POST, "/item").permitAll()//.hasRole("ADMIN")
               .requestMatchers(HttpMethod.DELETE, "/item/**").permitAll()//.hasRole("ADMIN")
               .requestMatchers(HttpMethod.PUT, "/item/**").permitAll()//.hasRole("ADMIN")
               .requestMatchers(HttpMethod.PATCH, "/item/**").permitAll()//.hasRole("ADMIN")
               .requestMatchers(HttpMethod.GET, "/item/**").permitAll()//.hasRole("USER")
               .requestMatchers("/Usuario/**").permitAll()//hasRole("ADMIN")
               .requestMatchers("/h2-console/**").permitAll()
               .anyRequest().permitAll()
               .and()
               .formLogin()
               .and()
               .headers().frameOptions().sameOrigin()
               .and()
               .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
               .cors();
       return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("http://localhost:4200");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
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

