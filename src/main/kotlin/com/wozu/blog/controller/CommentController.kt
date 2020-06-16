package com.wozu.blog.controller

import com.wozu.blog.models.Comment
import com.wozu.blog.repository.ArticleRepository
import com.wozu.blog.repository.CommentRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class CommentController(val repository: CommentRepository, val articles: ArticleRepository) {
    @CrossOrigin()
    @GetMapping("/api/comments/{article_id}")
    fun getComments(@PathVariable(value = "article_id") articleId: Long): MutableList<Comment> {
        return articles.findById(articleId).get().comments
    }

    @PostMapping("/api/comments")
    fun postComment(@RequestBody comment: Comment): ResponseEntity<Comment?>? {
        // Saving to DB using an instance of the repo interface.
        val createdComment: Comment = repository.save(comment)

        // RespEntity crafts response to include correct status codes.
        return ResponseEntity.ok<Comment>(createdComment)
    }

    @DeleteMapping("/api/comments/{id}")
    fun deleteComment(@PathVariable(value = "id") id: Long): ResponseEntity<Comment?>? {
        val foundComment: Comment = repository.findById(id).orElse(null)
        repository.delete(foundComment)
        return ResponseEntity.ok<Comment?>(foundComment)
    }

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