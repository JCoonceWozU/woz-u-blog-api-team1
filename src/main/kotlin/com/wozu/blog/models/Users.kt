package com.wozu.blog.models

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonRootName
import javax.persistence.*

@Entity
@JsonRootName("users")
data class Users(var email: String = "",
                 @JsonIgnore
                 var password: String = "",
                 var token: String = "",
                 var username: String = "",
                 var bio: String = "",
                 var image: String = "",
                 @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
                 var id: Long = 0) {
    override fun toString(): String = "User($email, $username)"

}