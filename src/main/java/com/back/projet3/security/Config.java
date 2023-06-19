package com.back.projet3.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class Config {

  @Autowired
  JwtEntryPoint jwtEntryPoint;

  @Autowired
  JwtFilter jwtFilter;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
 

    http.cors().and().csrf().disable()
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .exceptionHandling()
        .authenticationEntryPoint(jwtEntryPoint)
        .and()
        .authorizeRequests()
        .antMatchers("/", "/barters", "/offer-a-barter", "/login", "/signup","/barters/{id}/image","/streamMessages","/custumLogout")
        .permitAll()
        .antMatchers("/users").hasRole("ADMIN")
        .anyRequest().authenticated(); 
        
   
    http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  
  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
