package com.wozu.blog.models

import java.time.OffsetDateTime
import javax.persistence.*

@Entity
data class Comment(var body: String = "",
                   var createdAt: OffsetDateTime = OffsetDateTime.now(),
                   @ManyToOne
                   @JoinColumn(name = "article_id")
                   var article: Article?,
                   @ManyToOne
                   @JoinColumn(name = "commenter_id")
                   var commenter: Users?,
                   @Id
                   @GeneratedValue(strategy = GenerationType.AUTO)
                   var id: Long = 0) {
}