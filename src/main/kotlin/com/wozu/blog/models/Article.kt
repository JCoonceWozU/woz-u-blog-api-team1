package com.wozu.blog.models

import java.time.OffsetDateTime
import javax.persistence.*

@Entity
data class
Article(var title: String = "",
                   var body: String = "",
                   var slug: String = "",
        //@ManyToMany
        //var tagList: MutableList<Tag> = mutableListOf(),
                   var createdAt: OffsetDateTime = OffsetDateTime.now(),
                   var updatedAt: OffsetDateTime = OffsetDateTime.now(),
                   var favorited: Long = 0,
        //@ManyToOne
        //var author: User = User(),
                   var isPaid: Boolean = false,
                   var isPublished: Boolean = false,
                   var wordCount: Long = 0,
                   var readTime: Long = 0,
                   @Id @GeneratedValue(strategy = GenerationType.AUTO)
                   var id: Long = 0) {
    //create algorithm for read time
    fun calcReadTime() = null
}