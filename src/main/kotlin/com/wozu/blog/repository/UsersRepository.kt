package com.wozu.blog.repository

import com.wozu.blog.models.Users
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UsersRepository : CrudRepository<Users, Long> {
    fun existsByEmail(email: String): Boolean
    fun findByEmail(email: String): Users?
    fun findByToken(token: String): Users?
    fun findByEmailAndPassword(email: String, password: String): Users?
}