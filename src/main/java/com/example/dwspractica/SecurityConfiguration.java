package com.example.dwspractica;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;

@Configuration
@Order(2)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    RepositoryUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10, new SecureRandom());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // Public pages
        http.authorizeRequests().antMatchers("/home/").permitAll();
        http.authorizeRequests().antMatchers("/home/login").permitAll();
        http.authorizeRequests().antMatchers("/home/loginerror").permitAll();
        http.authorizeRequests().antMatchers("/home/logout").permitAll();
        http.authorizeRequests().antMatchers("/css/**").permitAll();
        http.authorizeRequests().antMatchers("/allGames").permitAll();
        http.authorizeRequests().antMatchers("/gameDetails/**").permitAll();
        http.authorizeRequests().antMatchers("/game/**/ratings").permitAll();
        http.authorizeRequests().antMatchers("/filteredGames").permitAll();

        // Private pages (all other pages)
        http.authorizeRequests().antMatchers("/addGame").hasRole("ADMIN");
        http.authorizeRequests().antMatchers("/addedGame").hasRole("ADMIN");
        http.authorizeRequests().antMatchers("/deleted/**").hasRole("ADMIN");
        http.authorizeRequests().antMatchers("/update/**").hasRole("ADMIN");
        http.authorizeRequests().antMatchers("/updated/**").hasRole("ADMIN");
        http.authorizeRequests().antMatchers("/game/**/ratings/createRating").hasRole("USER");
        http.authorizeRequests().antMatchers("/game/**/newRating").hasRole("USER");
        http.authorizeRequests().antMatchers("/game/**/deleteRating/**").hasAnyRole("USER", "ADMIN");
        http.authorizeRequests().antMatchers("/addedToCart/**").hasRole("USER");
        http.authorizeRequests().antMatchers("/shoppingCart").hasRole("USER");
        http.authorizeRequests().antMatchers("/RemoveGame/**").hasRole("USER");
        http.authorizeRequests().antMatchers("/RemoveShoppingCart").hasRole("USER");
        http.authorizeRequests().antMatchers("/MadeOrder").hasRole("USER");
        http.authorizeRequests().antMatchers("/showOrders").hasRole("USER");
        http.authorizeRequests().antMatchers("/deleteOrder/**").hasRole("USER");
        http.authorizeRequests().antMatchers("/myProfile").hasAnyRole("USER", "ADMIN");
        http.authorizeRequests().antMatchers("/changePassword").hasAnyRole("USER", "ADMIN");
        http.authorizeRequests().antMatchers("/passwordChanged").hasAnyRole("USER", "ADMIN");
        http.authorizeRequests().antMatchers("/deregister").hasAnyRole("USER", "ADMIN");
        http.authorizeRequests().antMatchers("/manageUsers").hasRole("ADMIN");
        http.authorizeRequests().antMatchers("/deleteUser/**").hasRole("ADMIN");

        // Login form
        http.formLogin().loginPage("/home/login");
        http.formLogin().usernameParameter("username");
        http.formLogin().passwordParameter("password");
        http.formLogin().defaultSuccessUrl("/home/");
        http.formLogin().failureUrl("/home/loginerror");

        // Logout
        http.logout().logoutUrl("/home/logout");
        http.logout().logoutSuccessUrl("/home/logout");

        // Disable CSRF at the moment
        //http.csrf().disable();
    }
}
