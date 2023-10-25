package ru.quipy.controller

import org.springframework.web.bind.annotation.*
import ru.quipy.api.UserCreatedEvent
import ru.quipy.logic.service.UserService
import ru.quipy.logic.state.UserAggregateState
import ru.quipy.logic.state.UserInfo
import java.util.*

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService
) {

    @PostMapping
    fun create(@RequestParam nickName: String, @RequestBody userInfo: UserInfo): UserCreatedEvent {
        return userService.create(nickName, userInfo)
    }

    @GetMapping("/{userId}")
    fun getUser(@PathVariable userId: UUID): UserAggregateState = userService.getUser(userId)
}