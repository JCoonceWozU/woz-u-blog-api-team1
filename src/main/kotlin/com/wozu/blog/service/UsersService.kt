package com.wozu.blog.service

import com.wozu.blog.exceptions.InvalidLoginException
import com.wozu.blog.models.Users
import com.wozu.blog.models.tokenizer.Login
import com.wozu.blog.repository.UsersRepository
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.mindrot.jbcrypt.BCrypt
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*

@Service
class UsersService(val usersRepository: UsersRepository,
                   @Value("\${jwt.secret}") val jwtSecret: String,
                   @Value("\${jwt.issuer}") val jwtIssuer: String) {

    val currentUser = ThreadLocal<Users>()

    //@Cacheable(cacheNames=arrayOf("usersByToken"), key="#token")
    fun findByToken(token: String) = usersRepository.findByToken(token)

    fun clearCurrentUser() = currentUser.remove()

    fun setCurrentUser(user: Users): Users {
        currentUser.set(user)
        return user
    }

    fun currentUser() = currentUser.get()

    fun newToken(user: Users): String {
        return Jwts.builder()
                .setIssuedAt(Date())
                .setSubject(user.email)
                .setIssuer(jwtIssuer)
                .setExpiration(Date(System.currentTimeMillis() + 10 * 24 * 60 * 60 * 1000)) // 10 days
                .signWith(SignatureAlgorithm.HS256, jwtSecret).compact()
    }

    fun validToken(token: String, user: Users): Boolean {
        val claims = Jwts.parser().setSigningKey(jwtSecret)
                .parseClaimsJws(token).body
        return claims.subject == user.email && claims.issuer == jwtIssuer
                && Date().before(claims.expiration)
    }

    //@CachePut(cacheNames=arrayOf("usersByToken"), key="#user.token")
    fun updateToken(user: Users): Users {
        user.token = newToken(user)
        return usersRepository.save(user)
    }

    fun login(login: Login): Users? {
        usersRepository.findByEmail(login.email!!)?.let {
            if (BCrypt.checkpw(login.password!!, it.password)) {
                return updateToken(it)
            }
            throw InvalidLoginException("password", "invalid password")
        }
        throw InvalidLoginException("email", "unknown email")
    }

}
