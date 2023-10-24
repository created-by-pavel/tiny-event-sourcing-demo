package ru.quipy.api

import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import ru.quipy.logic.state.TaskStatusEntity
import java.util.*

private const val PROJECT_CREATED_EVENT = "PROJECT_CREATED_EVENT"
private const val PROJECT_INFO_UPDATED_EVENT = "PROJECT_INFO_UPDATED_EVENT"
private const val USER_JOINED_TO_PROJECT_EVENT = "USER_JOINED_TO_PROJECT_EVENT"
private const val USER_LEFT_PROJECT_EVENT = "USER_LEFT_FROM_PROJECT_EVENT"
private const val TASK_STATUS_CREATED_EVENT = "TASK_STATUS_CREATED_EVENT"
private const val TASK_STATUS_DELETED_EVENT = "TASK_STATUS_DELETED_EVENT"

// API
@DomainEvent(name = PROJECT_CREATED_EVENT)
class ProjectCreatedEvent(
    val projectId: UUID,
    val projectTitle: String,
    val defaultTaskStatusEntity: TaskStatusEntity
) : Event<ProjectAggregate>(
    name = PROJECT_CREATED_EVENT,
    createdAt = System.currentTimeMillis(),
)

@DomainEvent(name = PROJECT_INFO_UPDATED_EVENT)
class ProjectInfoUpdatedEvent(
    val projectId: UUID,
    val newTitle: String,
) : Event<ProjectAggregate>(
    name = PROJECT_INFO_UPDATED_EVENT,
    createdAt = System.currentTimeMillis()
)

@DomainEvent(name = USER_LEFT_PROJECT_EVENT)
class UserLeftProjectEvent(
    val projectId: UUID,
    val userId: UUID,
) : Event<ProjectAggregate>(
    name = USER_LEFT_PROJECT_EVENT,
    createdAt = System.currentTimeMillis()
)

@DomainEvent(name = USER_JOINED_TO_PROJECT_EVENT)
class UserJoinedToProjectEvent(
    val projectId: UUID,
    val userId: UUID,
) : Event<ProjectAggregate>(
    name = USER_JOINED_TO_PROJECT_EVENT,
    createdAt = System.currentTimeMillis()
)

@DomainEvent(name = TASK_STATUS_CREATED_EVENT)
class TaskStatusCreatedEvent(
    val projectId: UUID,
    val taskStatusId: UUID,
    val taskStatusName: String,
    val color: String,
) : Event<ProjectAggregate>(
    name = TASK_STATUS_CREATED_EVENT,
    createdAt = System.currentTimeMillis()
)

@DomainEvent(name = TASK_STATUS_DELETED_EVENT)
class TaskStatusDeletedEvent(
    val projectId: UUID,
    val taskStatusId: UUID
) : Event<ProjectAggregate>(
    name = TASK_STATUS_DELETED_EVENT,
    createdAt = System.currentTimeMillis()
)