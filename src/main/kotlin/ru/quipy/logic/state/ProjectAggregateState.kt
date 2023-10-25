package ru.quipy.logic.state

import ru.quipy.api.*
import ru.quipy.core.annotations.StateTransitionFunc
import ru.quipy.domain.AggregateState
import java.util.*

// Service's business logic
class ProjectAggregateState : AggregateState<UUID, ProjectAggregate> {
    private lateinit var projectId: UUID
    private var createdAt: Long = System.currentTimeMillis()
    private var updatedAt: Long = System.currentTimeMillis()

    lateinit var projectTitle: String
    var taskStatuses = mutableMapOf<UUID, TaskStatusEntity>()
    val members = mutableListOf<UUID>()

    override fun getId() = projectId

    // State transition functions which is represented by the class member function
    @StateTransitionFunc
    fun projectCreatedApply(event: ProjectCreatedEvent) {
        projectId = event.projectId
        projectTitle = event.projectTitle
        updatedAt = createdAt
        taskStatuses[event.defaultTaskStatusEntity.id] = event.defaultTaskStatusEntity
    }

    @StateTransitionFunc
    fun projectInfoUpdatedApply(event: ProjectInfoUpdatedEvent) {
        projectTitle = event.newTitle
        updatedAt = event.createdAt
    }

    @StateTransitionFunc
    fun userLeftProjectApply(event: UserLeftProjectEvent) {
        members.remove(event.userId)
        updatedAt = event.createdAt
    }

    @StateTransitionFunc
    fun userJoinedToProjectApply(event: UserJoinedToProjectEvent) {
        members.add(event.userId)
        updatedAt = event.createdAt
    }

    @StateTransitionFunc
    fun taskStatusCreatedApply(event: TaskStatusCreatedEvent) {
        taskStatuses[event.taskStatusId] = TaskStatusEntity(event.taskStatusId, event.taskStatusName, event.color)
        updatedAt = event.createdAt
    }

    @StateTransitionFunc
    fun taskStatusDeletedApply(event: TaskStatusDeletedEvent) {
        taskStatuses.remove(event.taskStatusId)
        updatedAt = event.createdAt
    }
}

data class TaskStatusEntity(
    val id: UUID,
    val statusName: String,
    val color: String
) {
    companion object {
        const val DEFAULT_TASK_STATUS_NAME = "CREATED"
        const val DEFAULT_TASK_STATUS_COLOR = "GREEN"
    }
}
