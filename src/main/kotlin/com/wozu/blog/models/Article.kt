package com.wozu.blog.models

import java.time.OffsetDateTime
import javax.persistence.*

@Entity
data class Article(var title: String,
                   var shortBody: String,
                   var body: String = "",
                   var createdAt: OffsetDateTime = OffsetDateTime.now(),
                   var updatedAt: OffsetDateTime = OffsetDateTime.now(),
                   var favorited: Long = 0,
                   var isPaid: Boolean = false,
                   var isPublished: Boolean = false,
                   var wordCount: Long = 0,
                   var readTime: Long = 0,
                   @ManyToOne
                   @JoinColumn(name = "author_id")
                   var author: Users?,
                   @OneToMany(mappedBy = "article")
                   var comments: List<Comment> = ArrayList<Comment>(),
                   @Id @GeneratedValue(strategy = GenerationType.AUTO)
                   var id: Long = 0) {
    //create algorithm for read time
    fun calcReadTime() = null
}