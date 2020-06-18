package com.wozu.blog.repository

import com.wozu.blog.models.Article
import com.wozu.blog.models.Comment
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository: CrudRepository<Comment, Long> {
    fun findByArticle(article: Article): List<Comment>
    fun findByArticleOrderByCreatedAtDesc(article: Article): List<Comment>
}