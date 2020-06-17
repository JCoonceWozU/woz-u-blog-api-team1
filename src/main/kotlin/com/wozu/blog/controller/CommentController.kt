package com.wozu.blog.controller

import com.wozu.blog.models.Article
import com.wozu.blog.models.Comment
import com.wozu.blog.repository.ArticleRepository
import com.wozu.blog.repository.CommentRepository
import com.wozu.blog.service.ArticleService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class CommentController(val repository: CommentRepository, val articles: ArticleRepository) {
    @CrossOrigin()
    @GetMapping("/api/comments/{article_id}")
    fun getComments(@PathVariable(value = "article_id") articleId: Long): MutableList<Comment> {
        return articles.findById(articleId).get().comments
    }
    @CrossOrigin()
    @PostMapping("/api/comments/{article_id}")
    fun postComment(@PathVariable(value = "article_id") articleId: Long, @RequestBody comment: Comment): ResponseEntity<Comment?>? {
        println(comment)
        var article: Article = articles.findById(articleId).orElse(null)
        // Saving to DB using an instance of the repo interface.
        val createdComment: Comment = Comment(body = comment.body, article = article, commenter = null)
        repository.save(createdComment)
        // RespEntity crafts response to include correct status codes.
        return ResponseEntity.ok<Comment>(createdComment)
    }

    @CrossOrigin()
    @DeleteMapping("/api/comments/{id}")
    fun deleteComment(@PathVariable(value = "id") id: Long): ResponseEntity<Comment?>? {
        val foundComment: Comment = repository.findById(id).orElse(null)
        repository.delete(foundComment)
        return ResponseEntity.ok<Comment?>(foundComment)
    }

    @CrossOrigin()
    @PutMapping("api/comments/{id}")
    fun putComment(@RequestBody comment: Comment,
                   @PathVariable(value = "id") id: Long): ResponseEntity<Comment?>? {
        // Saving to DB using an instance of the repo interface.
        var updatedComment: Comment
        return run {
            updatedComment = repository.save(comment)
            ResponseEntity.ok<Comment?>(updatedComment)
        }
    }
}