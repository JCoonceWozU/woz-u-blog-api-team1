package com.wozu.blog.repository

import com.wozu.blog.models.Article
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ArticleRepository : JpaRepository<Article, Long> {
    fun findArticleById(id: Long): Article?
}