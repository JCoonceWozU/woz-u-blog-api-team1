package com.wozu.blog.controller

import com.github.slugify.Slugify
import com.wozu.blog.exceptions.ForbiddenRequestException
import com.wozu.blog.exceptions.InvalidRequest
import com.wozu.blog.exceptions.NotFoundException
import com.wozu.blog.models.Article
import com.wozu.blog.models.Comment
import com.wozu.blog.models.Users
import com.wozu.blog.models.io.NewArticle
import com.wozu.blog.models.io.NewComment
import com.wozu.blog.models.io.UpdateArticle
import com.wozu.blog.repository.ArticleRepository
import com.wozu.blog.repository.CommentRepository
import com.wozu.blog.repository.UsersRepository
import com.wozu.blog.repository.specs.ArticleSpec
import com.wozu.blog.service.UsersService
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.validation.Errors
import org.springframework.validation.FieldError
import org.springframework.web.bind.annotation.*
import java.time.OffsetDateTime
import javax.validation.Valid
import java.util.*
import com.wozu.blog.models.io.Article as ArticleIO
import com.wozu.blog.models.io.Comment as CommentOut

@RestController
class ArticleController(val repository: ArticleRepository,
                        val usersService: UsersService,
                        val userRepository: UsersRepository,
                        val commentRepository: CommentRepository) {

    @GetMapping("/api/articles")
    fun articles(@RequestParam(defaultValue = "20") limit: Int,
                 @RequestParam(defaultValue = "0") offset: Int,
                 @RequestParam(defaultValue = "") author: String): Any {
        val p = PageRequest.of(offset, limit, Sort.Direction.DESC, "createdAt")

        val articles = repository.findAll(ArticleSpec.lastArticles(
                if (author != "") userRepository.findByEmail(author) else null
        ), p).toList()

        return articlesView(articles)
    }

    @GetMapping("/api/articles/{slug}")
    fun article(@PathVariable slug: String): Any {
        repository.findBySlug(slug)?.let {
            return articleView(it)
        }
        throw NotFoundException()
    }

    @PostMapping("/api/articles")
    fun newArticle(@Valid @RequestBody newArticle: NewArticle, errors: Errors): Any {
        InvalidRequest.check(errors)

        var slug = Slugify().slugify(newArticle.title!!)

        if (repository.existsBySlug(slug)) {
            slug += "-" + UUID.randomUUID().toString().substring(0, 8)
        }

        val currentUser = usersService.currentUser()

        val article = Article(slug = slug,
                author = newArticle.author!!, title = newArticle.title!!, description = newArticle.description!!,
                body = newArticle.body!!)

        return articleView(repository.save(article))
    }

    @PutMapping("/api/articles/{slug}")
    fun updateArticle(@PathVariable slug: String, @RequestBody article: UpdateArticle): Any {
        repository.findBySlug(slug)?.let {
            // check for errors
            val errors = org.springframework.validation.BindException(this, "")
            if (article.title == "")
                errors.addError(FieldError("", "title", "can't be empty"))
            if (article.description == "")
                errors.addError(FieldError("", "description", "can't be empty"))
            if (article.body == "")
                errors.addError(FieldError("", "body", "can't be empty"))
            InvalidRequest.check(errors)

            var slug: String = it.slug
            article.title?.let { newTitle ->
                if (newTitle != it.title) {
                    // we don't want conflicting slugs
                    slug = Slugify().slugify(article.title!!)
                    if (repository.existsBySlug(slug)) {
                        slug += "-" + UUID.randomUUID().toString().substring(0, 8)
                    }
                }
            }


            val updated = it.copy(title = article.title ?: it.title,
                    description = article.description ?: it.description,
                    body = article.body ?: it.body,
                    slug = slug,
                    updatedAt = OffsetDateTime.now())

            return articleView(repository.save(updated))
        }

        throw NotFoundException()
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/api/articles/{slug}")
    fun deleteArticle(@PathVariable slug: String) {
        repository.findBySlug(slug)?.let {
            commentRepository.deleteAll(commentRepository.findByArticle(it))
            return repository.delete(it)
        }
        throw NotFoundException()
    }

    @GetMapping("/api/articles/{slug}/comments")
    fun articleComments(@PathVariable slug: String): Any {
        repository.findBySlug(slug)?.let {
            val currentUser = usersService.currentUser()
            return commentsView(commentRepository.findByArticleOrderByCreatedAtDesc(it))
        }
        throw NotFoundException()
    }

    @PostMapping("/api/articles/{slug}/comments")
    fun addComment(@PathVariable slug: String, @Valid @RequestBody comment: NewComment, errors: Errors): Any {
        InvalidRequest.check(errors)

        repository.findBySlug(slug)?.let {
            val currentUser = usersService.currentUser()
            val newComment = Comment(body = comment.body!!, article = it)
            return commentView(commentRepository.save(newComment))
        }
        throw NotFoundException()
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/api/articles/{slug}/comments/{id}")
    fun deleteComment(@PathVariable slug: String, @PathVariable id: Long) {
        repository.findBySlug(slug)?.let {
            val comment = commentRepository.findById(id).orElseThrow { NotFoundException() }
            if (comment.article.id != it.id)
                throw ForbiddenRequestException()
            return commentRepository.delete(comment)
        }
        throw NotFoundException()
    }

    // helpers

    fun articleView(article: Article) = mapOf("article" to ArticleIO.fromModel(article))

    fun articlesView(articles: List<Article>) = mapOf("articles" to articles.map { ArticleIO.fromModel(it) },
            "articlesCount" to articles.size)

    fun commentView(comment: Comment) = mapOf("comment" to CommentOut.fromModel(comment))

    fun commentsView(comments: List<Comment>) = mapOf("comments" to comments.map { CommentOut.fromModel(it) })
}