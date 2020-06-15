package com.wozu.blog.controller

import com.wozu.blog.models.Article
import com.wozu.blog.repository.ArticleRepository
import com.wozu.blog.utilities.Serializer
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
class ArticleController(val repository: ArticleRepository) {
    val serializer = Serializer()

    @CrossOrigin()
    @GetMapping("/api/articles")
    fun getArticles(): MutableList<Article> {
        return repository.findAll()
    }

    @GetMapping("/api/articles/{id}")
    fun getArticle(@PathVariable(value = "id") id: Long): ResponseEntity<String?> {
        val queriedArticle = serializer.execute(repository, id)
                ?: return ResponseEntity.notFound().header("Article",
                        "Nothing found with that id").build()
        return ResponseEntity.ok(queriedArticle)

    }

    @PostMapping("/api/articles")
    fun postArticle(@RequestBody article: Article): ResponseEntity<Article>? {
        // Saving to DB using an instance of the repo interface.
        val createdArticle: Article = repository.save(article)

        // RespEntity crafts response to include correct status codes.
        return ResponseEntity.ok<Article>(createdArticle)
    }

    @DeleteMapping("/api/articles/{id}")
    fun deleteArticle(@PathVariable(value = "id") id: Long): ResponseEntity<Article?>? {
        val foundArticle: Article = repository.findById(id).orElse(null)
        repository.delete(foundArticle)
        return ResponseEntity.ok().build<Article?>()
    }

    @PutMapping("api/articles/")
    fun putArticle(@RequestBody article: Article): ResponseEntity<Article?>? {
        // Saving to DB using an instance of the repo interface.
        var updatedArticle: Article
        return run {
            updatedArticle = repository.save(article)
            ResponseEntity.ok<Article?>(updatedArticle)
        }
    }

    @PutMapping("api/articles/{id}")
    fun putArticle(@RequestBody article: Article,
                   @PathVariable(value = "id") id: Long): ResponseEntity<Article?>? {
        // Saving to DB using an instance of the repo interface.
        var updatedArticle: Article
        return run {
            updatedArticle = repository.save(article)
            ResponseEntity.ok<Article?>(updatedArticle)
        }
    }
}