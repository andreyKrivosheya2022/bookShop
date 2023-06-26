package com.company.pet_project.config;

import com.company.pet_project.service.PersonDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    private PersonDetailsService personDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .authorizeHttpRequests(
                        (authorise) -> authorise
                                .requestMatchers("/css/*", "/shop/auth/**", "/shop/auth/*").permitAll()
                                .requestMatchers("/shop/admin/*", "/shop/*/admin/*").hasRole("ADMIN")
                                .anyRequest().hasAnyRole("USER","ADMIN")
                )
                .httpBasic()
                .and()
                .formLogin()
                .loginPage("/shop/auth/login")
                .loginProcessingUrl("/process_login")
                .defaultSuccessUrl("/shop/products")
                .and()
                .logout()
                .logoutUrl("/shop/auth/logout")
                .logoutSuccessUrl("/shop/auth/login")
                .and()
                .userDetailsService(personDetailsService)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
