package com.wozu.blog.controller

import com.wozu.blog.exceptions.ForbiddenRequestException
import com.wozu.blog.exceptions.InvalidException
import com.wozu.blog.exceptions.InvalidLoginException
import com.wozu.blog.exceptions.InvalidRequest
import com.wozu.blog.models.Users
import com.wozu.blog.models.io.Login
import com.wozu.blog.models.io.Register
import com.wozu.blog.models.io.UpdateUsers
import com.wozu.blog.repository.UsersRepository
import com.wozu.blog.service.UsersService
import org.mindrot.jbcrypt.BCrypt
import org.springframework.validation.BindException
import org.springframework.validation.Errors
import org.springframework.validation.FieldError
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
class UsersController(val repository: UsersRepository,
                      val service: UsersService) {

    @PostMapping("/api/users/login")
    fun login(@Valid @RequestBody login: Login, errors: Errors): Any {
        InvalidRequest.check(errors)

        try {
            service.login(login)?.let {
                return view(service.setCurrentUser(it))
            }
            return ForbiddenRequestException()
        } catch (e: InvalidLoginException) {
            val errors = org.springframework.validation.BindException(this, "")
            errors.addError(FieldError("", e.field, e.error))
            throw InvalidException(errors)
        }
    }

    @PostMapping("/api/users")
    fun register(@Valid @RequestBody register: Register, errors: Errors): Any {
        InvalidRequest.check(errors)

        // check for duplicate user
        val errors = org.springframework.validation.BindException(this, "")
        checkUserAvailability(errors, register.email)
        InvalidRequest.check(errors)

        val user = Users(username = register.email!!,
                email = register.email!!, password = BCrypt.hashpw(register.password, BCrypt.gensalt()))

        return view(repository.save(user))
    }

    @GetMapping("/api/user")
    fun currentUser() = view(service.currentUser())

    @PutMapping("/api/user")
    fun updateUser(@Valid @RequestBody user: UpdateUsers, errors: Errors): Any {
        InvalidRequest.check(errors)

        val currentUser = service.currentUser()

        // check for errors
        val errors = org.springframework.validation.BindException(this, "")
        if (currentUser.email != user.email && user.email != null) {
            if (repository.existsByEmail(user.email!!)) {
                errors.addError(FieldError("", "email", "already taken"))
            }
        }
        if (user.password == "") {
            errors.addError(FieldError("", "password", "can't be empty"))
        }
        InvalidRequest.check(errors)

        // update the user
        val u = currentUser.copy(email = user.email ?: currentUser.email,
                password = BCrypt.hashpw(user.password, BCrypt.gensalt()), image = user.image ?: currentUser.image,
                bio = user.bio ?: currentUser.bio)

        return view(repository.save(u))
    }

    private fun checkUserAvailability(errors: BindException, email: String?) {
        email?.let { email ->
            if (repository.existsByEmail(email)) {
                errors.addError(FieldError("", "email", "already taken"))
            }
        }
    }

    fun view(user: Users) = mapOf("user" to user)
}