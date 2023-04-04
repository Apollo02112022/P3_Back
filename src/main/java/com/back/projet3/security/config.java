package com.back.projet3.security;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.context.annotation.Configuration; 

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
@EnableWebSecurity
public class config {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    
        http.authorizeHttpRequests((authz) -> {
            try {
                authz
                    .antMatchers("/barters").permitAll()
                    .antMatchers("/").hasRole("ADMIN") /*L'adminisatrateur bénéficie de tous les droits d'accès.*/
                	.antMatchers("/users/{id}/profil", "/offer-a-barter", "/barters/{id}", "/proposal_deal",
                     "/notifications", "/notifications/{id}").hasRole("USER") /*L'utilisateur pourra accèder à tous les liens répertoriés ici.*/
                    .anyRequest().authenticated()
                    .and()
                    .formLogin()
                    .and()
                    .httpBasic();
            } catch (Exception e) {
    
                e.printStackTrace();
            }
        });
  
        return http.build();

	};

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
    PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();   

    UserDetails user = User
        .withUsername("user")
        .password(encoder.encode("user"))
        .roles("USER")
        .build();

    UserDetails admin = User
        .withUsername("admin")
        .password(encoder.encode("admin"))
        .roles("ADMIN")
        .build();

	return new InMemoryUserDetailsManager(user, admin);

    }

} 
