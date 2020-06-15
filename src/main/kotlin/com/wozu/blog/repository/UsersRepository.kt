package com.wozu.blog.repository

import com.wozu.blog.models.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UsersRepository: JpaRepository<User, Long> {
    fun findFirstById(id: Long): User?
}