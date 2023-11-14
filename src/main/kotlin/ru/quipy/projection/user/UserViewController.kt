package ru.quipy.projection.user

import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/user-view")
class UserViewController(
    private val userViewService: UserViewService
) {

    @GetMapping("/{userId}")
    fun getUser(@PathVariable userId: UUID): UserViewDomain.User? = userViewService.findUserById(userId)
}