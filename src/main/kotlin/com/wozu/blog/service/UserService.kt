package com.wozu.blog.service

import com.wozu.blog.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(val userRepository: UserRepository) {
}