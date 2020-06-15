package com.wozu.blog.controller

import com.wozu.blog.models.Users
import com.wozu.blog.repository.UsersRepository
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UsersController(val repository: UsersRepository) {
    @GetMapping("/api/users")
    fun users(): String {
        return "hereAreUsers";
    }

    @PostMapping("/api/users")
    fun newUser(@RequestBody newUser: Users, errors: Errors): Any {

        //val currentUser = userService.currentUser()

        val user = Users(email = newUser.email, password = newUser.password)

        repository.save(user)
        return "success"
    }
}