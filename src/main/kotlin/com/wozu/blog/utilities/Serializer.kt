package com.wozu.blog.utilities

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.wozu.blog.models.Article
import com.wozu.blog.repository.ArticleRepository

class Serializer {
    fun execute(repo: ArticleRepository, id: Long): String? {
        val mapper = jacksonObjectMapper()
        val queriedArticle = repo.findById(id).orElse(null)
        return mapper.writeValueAsString(queriedArticle)
    }
}