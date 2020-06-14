package com.wozu.blog.controller

import com.wozu.blog.models.Article
import com.wozu.blog.models.User
import com.wozu.blog.repository.UserRepository
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(val repository: UserRepository) {
    @GetMapping("/api/users")
    fun users(): String {
        return "hereAreUsers";
    }

    @PostMapping("/api/users")
    fun newUser(@RequestBody newUser: User, errors: Errors): Any {

        //val currentUser = userService.currentUser()

        val user = User(email = newUser.email, password = newUser.password,
                articles = List<Article>(1, Article()))

        repository.save(user)
        return "success"
    }
}