package ru.quipy.api

import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import java.util.UUID

private const val TASK_CREATED_EVENT = "TASK_CREATED_EVENT"
private const val TASK_ASSIGNED_EVENT = "TASK_ASSIGNED_EVENT"
private const val TASK_UNASSIGNED_EVENT = "TASK_UNASSIGNED_EVENT"
private const val TASK_INFO_UPDATED_EVENT = "TASK_INFO_UPDATED_EVENT"
private const val TASK_STATUS_UPDATED_EVENT = "TASK_STATUS_UPDATED_EVENT"

@DomainEvent(name = TASK_CREATED_EVENT)
class TaskCreatedEvent(
    val taskId: UUID,
    val projectId: UUID,
    val taskStatusId: UUID,
    val taskTitle: String,
    val taskDescription: String
) : Event<TaskAggregate>(
    name = TASK_CREATED_EVENT,
    createdAt = System.currentTimeMillis(),
)

@DomainEvent(name = TASK_ASSIGNED_EVENT)
class TaskAssignedEvent(
    val taskId: UUID,
    val userId: UUID,
) : Event<TaskAggregate>(
    name = TASK_ASSIGNED_EVENT,
    createdAt = System.currentTimeMillis()
)

@DomainEvent(name = TASK_UNASSIGNED_EVENT)
class TaskUnassignedEvent(
    val taskId: UUID,
    val userId: UUID,
) : Event<TaskAggregate>(
    name = TASK_UNASSIGNED_EVENT,
    createdAt = System.currentTimeMillis()
)

@DomainEvent(name = TASK_INFO_UPDATED_EVENT)
class TaskInfoUpdatedEvent(
    val taskId: UUID,
    val newTitle: String,
    val newDescription: String
) : Event<TaskAggregate>(
    name = TASK_INFO_UPDATED_EVENT,
    createdAt = System.currentTimeMillis()
)

@DomainEvent(name = TASK_STATUS_UPDATED_EVENT)
class TaskStatusUpdatedEvent(
    val taskId: UUID,
    val newTaskStatusId: UUID
) : Event<TaskAggregate>(
    name = TASK_STATUS_UPDATED_EVENT,
    createdAt = System.currentTimeMillis()
)