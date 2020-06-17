package com.wozu.blog.models.tokenizer

import com.fasterxml.jackson.annotation.JsonRootName
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

@JsonRootName("users")
class UpdateUsers {
    @Size(min = 1, message = "can't be empty")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$",
            message = "must be a valid email")
    var email: String? = null

    var password: String? = null
    var image: String? = ""
    var bio: String? = ""
}