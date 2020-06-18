package com.wozu.blog.service

import com.wozu.blog.exceptions.InvalidLoginException
import com.wozu.blog.models.Users
import com.wozu.blog.models.io.Login
import com.wozu.blog.repository.UsersRepository
import org.mindrot.jbcrypt.BCrypt
import org.springframework.stereotype.Service

@Service
class UsersService(val usersRepository: UsersRepository) {

    val currentUser = ThreadLocal<Users>()

    fun clearCurrentUser() = currentUser.remove()

    fun setCurrentUser(user: Users): Users {
        currentUser.set(user)
        return user
    }

    fun currentUser() = currentUser.get()

    fun updateUser(user: Users): Users {
        return usersRepository.save(user)
    }

    fun login(login: Login): Users? {
        usersRepository.findByEmail(login.email!!)?.let {
            if (BCrypt.checkpw(login.password!!, it.password)) {
                return updateUser(it)
            }
            throw InvalidLoginException("password", "invalid password")
        }
        throw InvalidLoginException("email", "unknown email")
    }

}
