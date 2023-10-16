package ru.quipy.logic

import ru.quipy.api.*
import ru.quipy.core.annotations.StateTransitionFunc
import ru.quipy.domain.AggregateState
import java.util.*


class UserAggregateState : AggregateState<UUID, UserAggregate> {

    private lateinit var userId: UUID
    private lateinit var userName: String
    private lateinit var userNickName: String

    override fun getId(): UUID = userId

    @StateTransitionFunc
    fun createUser(event: UserCreatedEvent) {
        userId = event.userId
        userName = event.userName
        userNickName = event.nickName
    }
}


