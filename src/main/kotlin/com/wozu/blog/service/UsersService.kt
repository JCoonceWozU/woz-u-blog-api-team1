package com.wozu.blog.service

import com.wozu.blog.repository.UsersRepository
import org.springframework.stereotype.Service

@Service
class UsersService(val usersRepository: UsersRepository) {
}