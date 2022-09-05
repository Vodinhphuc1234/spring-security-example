package com.dinhphuc.learningspringsecurity.security;

import com.dinhphuc.learningspringsecurity.exceptionHandler.AuthenticationExceptionEntryPoint;
import com.dinhphuc.learningspringsecurity.filter.JWTFilter;
import com.dinhphuc.learningspringsecurity.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class config extends WebSecurityConfigurerAdapter {
    IUserService userService;
    AuthenticationExceptionEntryPoint authenticationExceptionEntryPoint;
    JWTFilter jwtFilter;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.cors().disable();
        http.authorizeRequests().antMatchers("/api/auth/login", "/api/auth/register").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/auth/posts").hasAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers("/api/auth/roles").hasAuthority("ROLE_ADMIN");
        http.authorizeRequests().anyRequest().authenticated();
        http.exceptionHandling().authenticationEntryPoint(authenticationExceptionEntryPoint);
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
