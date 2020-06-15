package com.wozu.blog.controller

import com.wozu.blog.models.Users
import com.wozu.blog.repository.UsersRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class UsersController(val repository: UsersRepository) {
    @CrossOrigin()
    @GetMapping("/api/userss")
    fun getUserss() : MutableList <Users> {
        return repository.findAll()
    }

    @GetMapping("/api/userss/{id}")
    fun getUsers(@PathVariable(value = "id") id: Long): ResponseEntity<Users> {
        val queriedUsers = repository.findById(id).orElse(null)
                ?: return ResponseEntity.notFound().header("Users",
                        "Nothing found with that id").build()
        return ResponseEntity.ok(queriedUsers)

    }

    @PostMapping("/api/userss")
    fun postUsers(@RequestBody users: Users): ResponseEntity<Users>? {
        // Saving to DB using an instance of the repo interface.
        val createdUsers: Users = repository.save(users)

        // RespEntity crafts response to include correct status codes.
        return ResponseEntity.ok<Users>(createdUsers)
    }

    @DeleteMapping("/api/userss/{id}")
    fun deleteUsers(@PathVariable(value = "id") id: Long): ResponseEntity<Users?>? {
        val foundUsers: Users = repository.findById(id).orElse(null)
        repository.delete(foundUsers)
        return ResponseEntity.ok().build<Users?>()
    }

    @PutMapping("api/userss/")
    fun putUsers(@RequestBody users: Users): ResponseEntity<Users?>? {
        // Saving to DB using an instance of the repo interface.
        var updatedUsers: Users
        return run {
            updatedUsers = repository.save(users)
            ResponseEntity.ok<Users?>(updatedUsers)
        }
    }

    @PutMapping("api/userss/{id}")
    fun putUsers(@RequestBody users: Users,
                   @PathVariable(value = "id") id: Long): ResponseEntity<Users?>? {
        // Saving to DB using an instance of the repo interface.
        var updatedUsers: Users
        return run {
            updatedUsers = repository.save(users)
            ResponseEntity.ok<Users?>(updatedUsers)
        }
    }
}