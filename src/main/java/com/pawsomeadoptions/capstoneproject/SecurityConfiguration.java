package com.pawsomeadoptions.capstoneproject;

import com.pawsomeadoptions.capstoneproject.service.UserDetailsLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private UserDetailsLoader usersLoader;

    public SecurityConfiguration(UserDetailsLoader usersLoader) {
        this.usersLoader = usersLoader;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                /* Login configuration */
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/profile") // user's home page, it can be any URL
                .permitAll() // Anyone can go to the login page
                /* Logout configuration */
                .and()
                .logout()
                .logoutSuccessUrl("/visitorpost") // Set the logout success URL
                /* Pages that can be viewed without having to log in */
                .and()
                .authorizeHttpRequests()
                .requestMatchers(
                        "/userpost",
                        "/userpost/{postID}",
                        "/profile","/userpost/{id}/edit", "/posts/submitEdit", "/userpost/{id}/delete"
                ) // only authenticated users can create/edit ads
                .authenticated()
                /* Pages that require authentication */
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/home", "/deleteUser", "/visitorpost", "/visitorpost/{postID}", "/sign-up", "/adopt", "/adopt/{petID}","/reset-password", "/about", "/invalidUsernameOrPassword", "/js/**", "/logout", "/css/**", "/img/**", "/static/**") // anyone can see home, the ads pages, and sign up
                .permitAll();

        return http.build();
    }


}