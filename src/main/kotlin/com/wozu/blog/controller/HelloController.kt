package com.wozu.blog.controller

import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController {
    @CrossOrigin
    @GetMapping("/hello")
    fun helloUser(@AuthenticationPrincipal user: OidcUser): String? {
        return "Hello " + user.attributes["email"]
    }
}