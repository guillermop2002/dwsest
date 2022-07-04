package com.example.dwspractica;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.SecureRandom;

@Configuration
@Order(1)
public class SecurityRESTConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    RepositoryUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10, new SecureRandom());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.requestMatchers().antMatchers("/api/**");
        // Public pages
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/games").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/game/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/getRatings/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/newUser").permitAll();

        // Private pages (all other pages)
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/addGame").hasRole("ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/delete/**").hasRole("ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.PUT, "/api/update/**").hasRole("ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/deleteRating/**/**").hasAnyRole("ADMIN", "USER");
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/addRating/**").hasRole("USER");
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/shoppingCart").hasRole("USER");
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/addToCart/**").hasRole("USER");
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/remove/**").hasRole("USER");
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/removeAllCart").hasRole("USER");
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/makeOrder").hasRole("USER");
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/myOrders").hasRole("USER");
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/deleteOrder/**").hasRole("USER");
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/showUsers").hasRole("ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/deleteUser/**").hasRole("ADMIN");

        // Login form
        http.formLogin().disable();

        http.httpBasic();

        // Disable CSRF at the moment
        http.csrf().disable();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
