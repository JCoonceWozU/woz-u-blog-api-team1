package com.wozu.blog.service

import com.wozu.blog.models.Article
import com.wozu.blog.repository.ArticleRepository
import org.springframework.stereotype.Service

@Service
class ArticleService(val articleRepository: ArticleRepository) {
}