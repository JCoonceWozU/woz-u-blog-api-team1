package com.wozu.blog.controller

import com.wozu.blog.repository.ArticleRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ArticleController(/*val repository: ArticleRepository*/) {
    
    @GetMapping("/api/articles")
    fun articles(): String {
        return "hereAreArticles";
    }
}