package ru.quipy.logic.commands

import ru.quipy.api.UserCreatedEvent
import ru.quipy.logic.state.UserAggregateState
import ru.quipy.logic.state.UserInfo
import java.util.*

fun UserAggregateState.createUser(
    id: UUID,
    nickName: String,
    userInfo: UserInfo
): UserCreatedEvent {
    return UserCreatedEvent(id, nickName, userInfo)
}