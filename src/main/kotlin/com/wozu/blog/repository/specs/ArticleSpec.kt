package com.wozu.blog.repository.specs

import com.wozu.blog.models.Article
import com.wozu.blog.models.Users
import org.springframework.data.jpa.domain.Specification
import javax.persistence.criteria.Predicate

object ArticleSpec {
    fun lastArticles(author: Users?): Specification<Article> {
        return Specification { root, query, cb ->
            val predicates = mutableListOf<Predicate>()

            author?.let {
                val user = root.get<String>("author")
                predicates.add(cb.equal(user, author))
            }

            cb.and(*predicates.toTypedArray())
        }
    }
}