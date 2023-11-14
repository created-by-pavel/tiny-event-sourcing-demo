package ru.quipy.projection.user

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.quipy.api.UserAggregate
import ru.quipy.api.UserCreatedEvent
import ru.quipy.streams.annotation.AggregateSubscriber
import ru.quipy.streams.annotation.SubscribeEvent
import java.util.*

@Service
@AggregateSubscriber(
    aggregateClass = UserAggregate::class, subscriberName = "user-subs-stream"
)
class UserViewService (val userRepository: UserRepository) {
    val logger: Logger = LoggerFactory.getLogger(UserViewService::class.java)

    @SubscribeEvent
    fun userCreatedSubscriber(event: UserCreatedEvent) {
        logger.info("User created: {}", event.userInfo.name)
        userRepository.save(
            UserViewDomain.User(
                event.userId,
                event.createdAt,
                event.nickName,
                event.userInfo,
            )
        )
    }

    fun findUserById(id: UUID): UserViewDomain.User? {
        return userRepository.findById(id).orElse(null)
    }
}