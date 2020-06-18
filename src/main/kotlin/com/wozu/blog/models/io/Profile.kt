package com.wozu.blog.models.io

import com.fasterxml.jackson.annotation.JsonRootName
import com.wozu.blog.models.Users

@JsonRootName("profile")
data class Profile(var email: String,
                   var bio: String,
                   var image: String?) {
    companion object {
        fun fromUser(user: Users, currentUser: Users): Profile {
            return Profile(email = user.email, bio = user.bio,
                    image = user.image)
        }
    }
}