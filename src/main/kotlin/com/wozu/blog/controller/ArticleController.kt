package com.wozu.blog.controller

import com.wozu.blog.models.Article
import com.wozu.blog.repository.ArticleRepository
import com.wozu.blog.repository.UsersRepository
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ArticleController(val repository: ArticleRepository) {

    @GetMapping("/api/articles")
    fun articles(): String {
        return "hereAreArticles";
    }

    @PostMapping("/api/articles")
    fun newArticle(@RequestBody newArticle: Article, errors: Errors): Any {
        val article = Article(title = newArticle.title, body = newArticle.body, author = newArticle.author)

        repository.save(article)
        return "success"
    }
}