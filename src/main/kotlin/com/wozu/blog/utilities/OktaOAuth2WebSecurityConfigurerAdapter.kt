package com.wozu.blog.utilities

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@Configuration
class OktaOAuth2WebSecurityConfigurerAdapter : WebSecurityConfigurerAdapter() {

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        // all routes protected
        http.authorizeRequests()
                .anyRequest().authenticated() // enable OAuth2/OIDC
                .and()
                .oauth2Login()
    }
}