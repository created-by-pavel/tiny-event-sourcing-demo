package ru.quipy.logic.service

import org.springframework.stereotype.Service
import ru.quipy.api.UserAggregate
import ru.quipy.api.UserCreatedEvent
import ru.quipy.core.EventSourcingService
import ru.quipy.exception.TaskManagerException
import ru.quipy.logic.commands.createUser
import ru.quipy.logic.state.UserAggregateState
import ru.quipy.logic.state.UserInfo
import java.util.*

@Service
class UserService (
    private val userEsService: EventSourcingService<UUID, UserAggregate, UserAggregateState>

){
    fun create(nickName: String, userInfo: UserInfo) : UserCreatedEvent {
        return userEsService.create { it.createUser(UUID.randomUUID(), nickName, userInfo) }
    }

    fun getUser(userId: UUID) : UserAggregateState {
        return userEsService.getState(userId) ?: throw TaskManagerException("User not found")
    }
}