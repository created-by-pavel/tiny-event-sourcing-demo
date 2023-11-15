package ru.quipy.projection.project

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import ru.quipy.domain.Unique
import java.util.*

class ProjectViewDomain {
    @Document(collection = "project-view-projects")
    data class Project(
        @Id
        override val id: UUID,
        var createdAt: Long = System.currentTimeMillis(),
        var projectTitle: String,
        val statuses: MutableSet<UUID> = mutableSetOf(),
        val members: MutableSet<UUID> = mutableSetOf()
    ) : Unique<UUID>

    @Document(collection = "project-view-statuses")
    data class Status(
        @Id
        override val id: UUID,
        var projectId: UUID,
        var statusName: String,
        var color: String
    ) : Unique<UUID>

    @Document(collection = "project-view-tasks")
    data class Task(
        @Id
        override var id: UUID,
        var createdAt: Long = System.currentTimeMillis(),
        var projectId: UUID,
        var taskTitle: String,
        var taskDescription: String,
        var taskStatusId: UUID,
        val executors: MutableSet<UUID> = mutableSetOf()
    ) : Unique<UUID>
}