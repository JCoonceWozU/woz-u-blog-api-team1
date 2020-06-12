package com.wozu.blog.utilities

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.wozu.blog.models.Article
import com.wozu.blog.repository.ArticleRepository
import org.springframework.data.jpa.repository.JpaRepository
import java.io.File

class Deserializer {
    private val path = "${System.getProperty("user.dir")}//src//main//resources//"
    // This is the skeleton for any Deserializer needed for a model
    /*
    fun execute(repo: JpaRepository<Any, Long>, fileName: String) {
        println(System.getProperty("user.dir"))
        val filePath = path + fileName
        val mapper = ObjectMapper()
        val inputFile = File(filePath)
        val records: List<*> = mapper.readValue(inputFile)
        for (record in records) {
            println(record)
            repo.save(record as Any)
        }
    }
    */

    fun execute(repo: ArticleRepository, fileName: String) {
        println(System.getProperty("user.dir"))
        val filePath = path + fileName
        val mapper = ObjectMapper()
        val inputFile = File(filePath)
        val records: List<Article> = mapper.readValue(inputFile)
        for (record in records) {
            println(record)
            repo.save(record)
        }
    }
}