package com.wozu.blog

import com.wozu.blog.repository.ArticleRepository
import com.wozu.blog.repository.UsersRepository
import com.wozu.blog.utilities.Deserializer
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener

@SpringBootApplication

class BlogApplication(private val articles: ArticleRepository,
    private val users: UsersRepository) {


    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(BlogApplication::class.java, *args)
        }
    }

    @EventListener
    fun populateOnLoad(event: ApplicationReadyEvent) {
        val populater = Deserializer()
        populater.execute(articles, "articleseed.json")
        populater.execute(users, "userseed.json")
    }
}