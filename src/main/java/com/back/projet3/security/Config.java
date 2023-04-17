package com.back.projet3.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
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
    // Cross-Site Request Forgery (CSRF) is enabled by default in spring security
    // configuration
    /**
     * Let's consider the following GET request used by a logged-in user to transfer
     * money to a specific bank account 1234:
     * 
     * GET http://bank.com/transfer?accountNo=1234&amount=100
     * Copy
     * If the attacker wants to transfer money from a victim's account to his own
     * account instead — 5678 — he needs to make the victim trigger the request:
     * 
     * GET http://bank.com/transfer?accountNo=5678&amount=1000
     * Since we use the jwt token, we don't need the csrf token to be enabled so we
     * can disable it
     * 
     */  
    /**
     * Une Session http sauvegarde des informations à propos d'un utilisateur
     * (client login state, for example, plus whatever else the Web application
     * needs to save)
     * Si on laisse le session management actif alors qu'on a désactivé la
     * protection CRSF on s'ouvre à des attaques CSRF
     * d'ou la ligne 60 sessionManagement(session ->
     * session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
     */

    http.cors().and().csrf().disable()
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .exceptionHandling()
        .authenticationEntryPoint(jwtEntryPoint)
        .and()
        .authorizeRequests()
        .antMatchers("/login")
        .permitAll()
        .anyRequest()
        .authenticated();

    http.logout().logoutSuccessUrl("/logoutSuccessfully");
    // where to implement the middleware filter
    http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}