package com.example.S20230403.configration.yheConfiguration;

import com.example.S20230403.auth.PrincipalOauth2UserService;
import com.example.S20230403.handler.yheHandler.*;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final PrincipalOauth2UserService principalOauth2UserService;
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;
    private final CustomAccessDeniedHandler webAccessDeniedHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.authorizeRequests()
                .antMatchers("/hello/**").hasAnyAuthority("USER", "SELLER")
                .antMatchers("/payment/**").hasAnyAuthority("USER", "SELLER")
                .antMatchers("/additional-info").hasAnyAuthority("TEMPORARY")
                .antMatchers("/admin").hasAnyAuthority("ADMIN")
                .antMatchers("/commonUser/**").hasAnyAuthority("USER", "ADMIN")
                .antMatchers("/seller/**").hasAnyAuthority("SELLER")
                .antMatchers("/cgAjaxInsertZzim").hasAnyAuthority("USER")// ajax에서 거르긴 하지만 찬규 이중 보안이라는 의미로 !
                .anyRequest().permitAll()
                .and()

                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(webAccessDeniedHandler)
                .and()

                .formLogin()
                .usernameParameter("user_id")
                .passwordParameter("password")
                .loginPage("/login")
                .failureHandler(customAuthenticationFailureHandler)
                .loginProcessingUrl("/login_proc")
                .defaultSuccessUrl("/")
                .successHandler(customAuthenticationSuccessHandler)
                .and()

                .oauth2Login()
                .loginPage("/login")
                .userInfoEndpoint()
                .userService(principalOauth2UserService)
                .and()
                .successHandler(customAuthenticationSuccessHandler)
                .and()

                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(logoutSuccessHandler())
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID");

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new CustomLogoutSuccessHandler();
    }

}
