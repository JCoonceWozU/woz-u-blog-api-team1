package com.wozu.blog.models

import org.jetbrains.annotations.NotNull
import java.time.OffsetDateTime
import javax.persistence.*

@Entity
// @Table(name = "article")
data class Article(var title: String,
                   var body: String,
                   var slug: String = "",
        //@ManyToMany
        //var tagList: MutableList<Tag> = mutableListOf(),
                   var createdAt: OffsetDateTime = OffsetDateTime.now(),
                   var updatedAt: OffsetDateTime = OffsetDateTime.now(),
                   var favorited: Long = 0,
                   var isPaid: Boolean = false,
                   var isPublished: Boolean = false,
                   var wordCount: Long = 0,
                   var readTime: Long = 0,
                   @ManyToOne
                   @JoinColumn(name = "user_id")
                   var user: Users,
                   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
                   var id: Long = 0) {
    //create algorithm for read time
    fun calcReadTime() = null
}