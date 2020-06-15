package com.wozu.blog.repository

import com.wozu.blog.models.Users
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UsersRepository: JpaRepository<Users, Long> {
    fun findFirstById(id: Long): Users?
}