package com.wozu.blog.controller

import com.wozu.blog.models.Comment
import com.wozu.blog.repository.CommentRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class CommentController(val repository: CommentRepository) {
    @CrossOrigin()
    @GetMapping("/api/comments")
    fun getComments() : MutableList <Comment> {
        return repository.findAll()
    }

    @GetMapping("/api/comments/{id}")
    fun getComment(@PathVariable(value = "id") id: Long): ResponseEntity<Comment> {
        val queriedComment = repository.findById(id).orElse(null)
                ?: return ResponseEntity.notFound().header("Comment",
                        "Nothing found with that id").build()
        return ResponseEntity.ok(queriedComment)

    }

    @PostMapping("/api/comments")
    fun postComment(@RequestBody comment: Comment): ResponseEntity<Comment>? {
        // Saving to DB using an instance of the repo interface.
        val createdComment: Comment = repository.save(comment)

        // RespEntity crafts response to include correct status codes.
        return ResponseEntity.ok<Comment>(createdComment)
    }

    @DeleteMapping("/api/comments/{id}")
    fun deleteComment(@PathVariable(value = "id") id: Long): ResponseEntity<Comment?>? {
        val foundComment: Comment = repository.findById(id).orElse(null)
        repository.delete(foundComment)
        return ResponseEntity.ok().build<Comment?>()
    }

    @PutMapping("api/comments/")
    fun putComment(@RequestBody comment: Comment): ResponseEntity<Comment?>? {
        // Saving to DB using an instance of the repo interface.
        var updatedComment: Comment
        return run {
            updatedComment = repository.save(comment)
            ResponseEntity.ok<Comment?>(updatedComment)
        }
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