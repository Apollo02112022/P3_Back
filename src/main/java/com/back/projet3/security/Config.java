package com.back.projet3.security;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.context.annotation.Configuration; 

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

@Configuration
@EnableWebSecurity
public class Config {

    // @Autowired
    // JwtEntryPoint jwtEntryPoint;
  
    // @Autowired
    // JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    http.cors().and().csrf().disable();
        // http.authorizeRequests((authz) -> {
        //     try {
        //         authz
        //             .antMatchers("/barters","barters/*","/offer-a-barter","/").permitAll()
        //         	.antMatchers("/users/{user.id}/profil", "/offer-a-barter*", "/proposal_deal",
        //             "/notifications", "/notifications/{user.id}").hasRole("USER") /*L'utilisateur pourra accèder à tous les liens répertoriés ici.*/
        //             // .antMatchers("/**").hasRole("ADMIN") /*L'adminisatrateur bénéficie de tous les droits d'accès.*/
        //             .anyRequest().authenticated()
        //             .and()
        //             .formLogin()
        //             .and()
        //             .httpBasic();
        //     } catch (Exception e) {
    
        //         e.printStackTrace();
        //     }
        // });
  
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
    @Bean
    public WebMvcConfigurer corsConfigurer() {
    // https://stackoverflow.com/questions/44697883/can-you-completely-disable-cors-support-in-spring
    return new WebMvcConfigurer() {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("http://localhost:4200").allowedMethods("*");
    }
    };
}
    
} 

