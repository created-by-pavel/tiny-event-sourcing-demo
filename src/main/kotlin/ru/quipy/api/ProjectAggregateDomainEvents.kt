package ru.quipy.api

import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import java.util.*

const val PROJECT_CREATED_EVENT = "PROJECT_CREATED_EVENT"
const val UPDATE_PROJECT_INFO_EVENT = "UPDATE_PROJECT_INFO_EVENT"
const val ADD_USER_EVENT = "ADD_USER_EVENT"
const val ADD_TASK_EVENT = "ADD_TASK_EVENT"
const val TASK_DELETED_EVENT = "TASK_DELETED_EVENT"

// API
@DomainEvent(name = PROJECT_CREATED_EVENT)
class ProjectCreatedEvent(
    val projectId: UUID,
    val title: String,
    createdAt: Long = System.currentTimeMillis(),
) : Event<ProjectAggregate>(
    name = PROJECT_CREATED_EVENT,
    createdAt = createdAt,
)

@DomainEvent(name = UPDATE_PROJECT_INFO_EVENT)
class UpdateProjectInfoEvent(
    val projectId: UUID,
    val title: String,
    createdAt: Long = System.currentTimeMillis(),
) : Event<ProjectAggregate>(
    name = UPDATE_PROJECT_INFO_EVENT,
    createdAt = createdAt,
)

@DomainEvent(name = ADD_USER_EVENT)
class UserAddedEvent(
    val projectId: UUID,
    val userId: UUID,
    createdAt: Long = System.currentTimeMillis(),
) : Event<ProjectAggregate>(
    name = ADD_USER_EVENT,
    createdAt = createdAt,
)

@DomainEvent(name = ADD_TASK_EVENT)
class TaskAddedEvent(
    val projectId: UUID,
    val taskId: UUID,
    createdAt: Long = System.currentTimeMillis(),
) : Event<ProjectAggregate>(
    name = ADD_TASK_EVENT,
    createdAt = createdAt
)

@DomainEvent(name = TASK_DELETED_EVENT)
class TaskDeletedEvent(
    val projectId: UUID,
    val taskId: UUID,
    createdAt: Long = System.currentTimeMillis(),
) : Event<ProjectAggregate>(
    name = TASK_DELETED_EVENT,
    createdAt = createdAt
)