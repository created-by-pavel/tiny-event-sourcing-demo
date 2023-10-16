package ru.quipy.controller

import org.springframework.web.bind.annotation.*
import ru.quipy.api.UserAggregate
import ru.quipy.api.UserCreatedEvent
import ru.quipy.core.EventSourcingService
import ru.quipy.logic.UserAggregateState
import ru.quipy.logic.create
import java.util.*


@RestController
@RequestMapping("/user")
class UserController(
    val userEsService: EventSourcingService<UUID, UserAggregate, UserAggregateState>
) {

    @PostMapping("/{userName}/{nickName}")
    fun createUser( @PathVariable userName: String , @PathVariable nickName: String): UserCreatedEvent {
        return userEsService.create { it.create(UUID.randomUUID(), userName, nickName) }
    }

    @GetMapping("/{userID}")
    fun getUser(@PathVariable userID: UUID) : UserAggregateState? {
        return userEsService.getState(userID)
    }

}