package com.study.service.security;

import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //@formatter:off
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        final CharacterEncodingFilter filter = new CharacterEncodingFilter();

        http
                .authorizeRequests()
                    .antMatchers().permitAll() // TODO: 2020/08/30 테스트를 위해 전체 해제
                .and()
                    .oauth2Login()
                    .defaultSuccessUrl("/")
                    .failureUrl("/")
                .and()
                    .headers().frameOptions().disable()
                .and()
                    .exceptionHandling()
                    .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/"))
                .and()
                    .formLogin()
                    .successForwardUrl("/")
                .and()
                    .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/")
                    .deleteCookies("JSESSIONID")
                    .invalidateHttpSession(true)
                .and()
                    .addFilterBefore(filter, CsrfFilter.class)
                    .csrf().disable();
    }
    //@formatter:on

    @Bean
    private ClientRegistration getRegistration(final OAuth2ClientProperties clientProperties, final String client) {
        final OAuth2ClientProperties.Registration registration = clientProperties.getRegistration().get("github");

        return CommonOAuth2Provider.GITHUB.getBuilder(client)
                .clientId(registration.getClientId())
                .clientSecret(registration.getClientSecret())
                .scope("email")
                .build();
    }
}
