/* package com.wozu.blog

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BlogApplication

fun main(args: Array<String>) {
    runApplication<BlogApplication>(*args)
} */
package com.wozu.blog

import com.wozu.blog.models.Article
import org.springframework.beans.factory.InitializingBean
import org.springframework.context.annotation.Bean
import com.wozu.blog.repository.ArticleRepository
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BlogApplication(private val repository: ArticleRepository) {

    @Bean
    fun sendDatabase(): InitializingBean {
        return InitializingBean { repository.save(Article("Article 1", "Body 1")) }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<BlogApplication>(*args)
        }
    }
}