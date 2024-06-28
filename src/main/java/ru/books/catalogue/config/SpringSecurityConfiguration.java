package ru.books.catalogue.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.books.catalogue.model.UserAuthority;

@Slf4j
@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(expressionInterceptUrlRegistry ->
                        expressionInterceptUrlRegistry
                                //Все
                                .requestMatchers("/registration", "/login").permitAll()

                                //0
                                .requestMatchers(HttpMethod.GET, "/books/**").hasAnyAuthority(UserAuthority.PLACE_ACCES.getAuthority(), UserAuthority.MANAGE_ACCES.getAuthority())

                                .requestMatchers(HttpMethod.GET, "/reviews/**").hasAnyAuthority(UserAuthority.PLACE_ACCES.getAuthority(), UserAuthority.MANAGE_ACCES.getAuthority())
                                .requestMatchers(HttpMethod.POST, "/reviews").hasAnyAuthority(UserAuthority.PLACE_ACCES.getAuthority(), UserAuthority.MANAGE_ACCES.getAuthority())

                                .requestMatchers(HttpMethod.POST, "/marks").hasAnyAuthority(UserAuthority.PLACE_ACCES.getAuthority(), UserAuthority.MANAGE_ACCES.getAuthority())

                                //1
                                .requestMatchers(HttpMethod.POST, "/books").hasAuthority(UserAuthority.MANAGE_ACCES.getAuthority())
                                .requestMatchers(HttpMethod.PUT, "/books/{id}").hasAuthority(UserAuthority.MANAGE_ACCES.getAuthority())
                                .requestMatchers(HttpMethod.DELETE, "/books/{id}").hasAuthority(UserAuthority.MANAGE_ACCES.getAuthority())

                                .requestMatchers(HttpMethod.PUT, "/reviews/{id}").hasAuthority(UserAuthority.MANAGE_ACCES.getAuthority())
                                .requestMatchers(HttpMethod.DELETE, "/reviews/{id}").hasAuthority(UserAuthority.MANAGE_ACCES.getAuthority())

                                .requestMatchers(HttpMethod.GET, "/marks/**").hasAuthority(UserAuthority.MANAGE_ACCES.getAuthority())
                                .requestMatchers(HttpMethod.PUT, "/marks/{id}").hasAuthority(UserAuthority.MANAGE_ACCES.getAuthority())
                                .requestMatchers(HttpMethod.DELETE, "/marks/{id}").hasAuthority(UserAuthority.MANAGE_ACCES.getAuthority())

                                .requestMatchers(HttpMethod.GET, "/authors/**").hasAuthority(UserAuthority.MANAGE_ACCES.getAuthority())
                                .requestMatchers(HttpMethod.POST, "/authors").hasAuthority(UserAuthority.MANAGE_ACCES.getAuthority())
                                .requestMatchers(HttpMethod.PUT, "/authors/{id}").hasAuthority(UserAuthority.MANAGE_ACCES.getAuthority())
                                .requestMatchers(HttpMethod.DELETE, "/authors/{id}").hasAuthority(UserAuthority.MANAGE_ACCES.getAuthority())

                                .anyRequest().hasAuthority(UserAuthority.FULL.getAuthority()))
                .formLogin(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
