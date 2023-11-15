package ru.quipy.projection.user

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import ru.quipy.domain.Unique
import ru.quipy.logic.state.UserInfo
import java.util.UUID

class UserViewDomain {
    @Document("user-view-users")
    data class User(
        @Id override val id: UUID,
        val createdAt: Long = System.currentTimeMillis(),
        val nickName: String,
        val userInfo: UserInfo,
    ) : Unique<UUID>
}