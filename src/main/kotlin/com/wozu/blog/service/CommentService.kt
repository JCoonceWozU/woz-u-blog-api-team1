package com.wozu.blog.service

import com.wozu.blog.repository.CommentRepository
import org.springframework.stereotype.Service

@Service
class CommentService(val commentRepository: CommentRepository) {
}