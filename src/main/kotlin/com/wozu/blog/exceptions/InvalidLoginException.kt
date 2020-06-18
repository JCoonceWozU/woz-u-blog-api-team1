package com.wozu.blog.exceptions

class InvalidLoginException(val field: String, val error: String) : RuntimeException()