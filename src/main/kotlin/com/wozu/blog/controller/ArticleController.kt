package com.wozu.blog.controller

import com.wozu.blog.models.Article
import com.wozu.blog.repository.ArticleRepository
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.*

@RestController
class ArticleController(val repository: ArticleRepository) {

    @CrossOrigin()
    @GetMapping("/api/articles")
    fun getArticles() : MutableList <Article> {
        return repository.findAll()
    }

    @PostMapping("/api/articles")
    fun newArticle(@RequestBody newArticle: Article, errors: Errors): Any {

        //val currentUser = userService.currentUser()

        val article = Article(title = newArticle.title!!, body = newArticle.body!!)

        repository.save(article)
        return "success"
    }
}