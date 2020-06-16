package com.wozu.blog.controller

import com.wozu.blog.models.Users
import com.wozu.blog.repository.UsersRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class UsersController(val repository: UsersRepository) {
    @CrossOrigin()
    @GetMapping("/api/users")
    fun getUsers() : MutableList <Users> {
        return repository.findAll()
    }

    @CrossOrigin()
    @GetMapping("/api/users/{id}")
    fun getUsers(@PathVariable(value = "id") id: Long): Users? {
        return repository.findById(id).orElse(null)

    }

    @CrossOrigin()
    @PostMapping("/api/users")
    fun postUsers(@RequestBody users: Users): ResponseEntity<Users>? {
        // Saving to DB using an instance of the repo interface.
        val createdUsers: Users = repository.save(users)

        // RespEntity crafts response to include correct status codes.
        return ResponseEntity.ok<Users>(createdUsers)
    }

    @CrossOrigin()
    @DeleteMapping("/api/users/{id}")
    fun deleteUsers(@PathVariable(value = "id") id: Long): ResponseEntity<Users?>? {
        val foundUsers: Users = repository.findById(id).orElse(null)
        repository.delete(foundUsers)
        return ResponseEntity.ok().build<Users?>()
    }

    @CrossOrigin()
    @PutMapping("api/users/")
    fun putUsers(@RequestBody users: Users): ResponseEntity<Users?>? {
        // Saving to DB using an instance of the repo interface.
        var updatedUsers: Users
        return run {
            updatedUsers = repository.save(users)
            ResponseEntity.ok<Users?>(updatedUsers)
        }
    }

    @CrossOrigin()
    @PutMapping("api/users/{id}")
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