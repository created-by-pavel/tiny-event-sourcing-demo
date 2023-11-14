package ru.quipy.projection.member

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import ru.quipy.domain.Unique
import java.util.*

class ProjectMembersViewDomain {
    @Document(collection = "project-members-view")
    data class Project(
        @Id override val id: UUID,
        val members: MutableSet<UUID> = mutableSetOf()
    ) : Unique<UUID>

    @Document(collection = "project-members-view")
    data class User(
        @Id override val id: UUID,
        val nickName: String
    ) : Unique<UUID>
}