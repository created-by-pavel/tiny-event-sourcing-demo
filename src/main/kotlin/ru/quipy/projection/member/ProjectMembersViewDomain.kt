package ru.quipy.projection.member

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import ru.quipy.domain.Unique
import java.util.*

class ProjectMembersViewDomain {
    @Document("members-view-projects")
    data class Project(
        @Id override val id: UUID,
        val members: MutableSet<UUID> = mutableSetOf()
    ) : Unique<UUID>

    @Document("members-view-users")
    data class User(
        @Id override val id: UUID,
        val nickName: String
    ) : Unique<UUID>
}