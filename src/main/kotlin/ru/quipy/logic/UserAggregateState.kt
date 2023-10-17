package ru.quipy.logic

import ru.quipy.api.*
import ru.quipy.core.annotations.StateTransitionFunc
import ru.quipy.domain.AggregateState
import java.util.*


class UserAggregateState : AggregateState<UUID, UserAggregate> {

    private lateinit var userId: UUID
    private lateinit var userInfo: UserInfo
    private lateinit var userNickName: String

    override fun getId(): UUID = userId

    @StateTransitionFunc
    fun createUser(event: UserCreatedEvent) {
        userId = event.userId
        userInfo = event.userInfo
        userNickName = event.nickName
    }
}

data class UserInfo (
    val name: String,
    val secondName: String
)


