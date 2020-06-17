package com.wozu.blog.models

import java.time.OffsetDateTime
import javax.persistence.*

@Entity
data class Article(var slug: String = "",
                   var title: String = "",
                   var description: String = "",
                   var body: String = "",
                   var createdAt: OffsetDateTime = OffsetDateTime.now(),
                   var updatedAt: OffsetDateTime = OffsetDateTime.now(),
                   // @ManyToMany
                   // var favorited: MutableList<Users> = mutableListOf(),
                   @ManyToOne
                   var author: Users = Users(),
                   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
                   var id: Long = 0) {
    //create algorithm for read time
    // fun calcReadTime() = null
}