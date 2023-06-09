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
        .antMatchers("/", "/barters", "/offer-a-barter", "/login", "/signup","/barters/{id}/image","/streamMessages","/custumLogout")
        .permitAll()
        .antMatchers("/users").hasRole("ADMIN")
        .anyRequest().authenticated(); 
        
    // where to implement the middleware filter
    http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  
  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
