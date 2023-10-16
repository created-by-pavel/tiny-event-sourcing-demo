package ru.quipy.logic


import ru.quipy.api.UserCreatedEvent
import java.util.*


// Commands : takes something -> returns event
// Here the commands are represented by extension functions, but also can be the class member functions

fun UserAggregateState.create(id: UUID, name: String, nickName: String): UserCreatedEvent {
    return UserCreatedEvent(
        userId = id,
        userName = name,
        nickName = nickName
    )
}