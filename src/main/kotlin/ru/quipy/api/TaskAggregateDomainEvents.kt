package ru.quipy.api

import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import ru.quipy.logic.Status
import java.util.*

const val TASK_CREATED_EVENT = "TASK_CREATED_EVENT"
const val UPDATE_TASKS_INFO_EVENT = "UPDATE_TASKS_INFO_EVENT"
const val ADD_TASKS_EXECUTOR_EVENT = "ADD_TASKS_EXECUTOR_EVENT"
const val DEL_TASK_EXECUTOR_EVENT = "DEL_TASK_EXECUTOR_EVENT"
const val CHANGE_TASK_STATUS_EVENT = "CHANGE_TASK_STATUS_EVENT"

// API

@DomainEvent(name = TASK_CREATED_EVENT)
class TaskCreatedEvent(
    val taskId: UUID,
    val title: String,
    val description: String,
    val projectId: UUID,
    createdAt: Long = System.currentTimeMillis(),
) : Event<TaskAggregate>(
    name = TASK_CREATED_EVENT,
    createdAt = createdAt
)

@DomainEvent(name = UPDATE_TASKS_INFO_EVENT)
class UpdateTasksInfoEvent(
    val taskId: UUID,
    val title: String,
    val description: String,
    createdAt: Long = System.currentTimeMillis(),
) : Event<TaskAggregate>(
    name = UPDATE_TASKS_INFO_EVENT,
    createdAt = createdAt
)

@DomainEvent(name = ADD_TASKS_EXECUTOR_EVENT)
class AddTaskExecutorEvent(
    val taskId: UUID,
    val executor: UUID,
    createdAt: Long = System.currentTimeMillis(),
) : Event<TaskAggregate>(
    name = ADD_TASKS_EXECUTOR_EVENT,
    createdAt = createdAt
)

@DomainEvent(name = DEL_TASK_EXECUTOR_EVENT)
class DelTaskExecutorEvent(
    val taskId: UUID,
    val executor: UUID,
    createdAt: Long = System.currentTimeMillis(),
) : Event<TaskAggregate>(
    name = DEL_TASK_EXECUTOR_EVENT,
    createdAt = createdAt
)

@DomainEvent(name = CHANGE_TASK_STATUS_EVENT)
class ChangeTaskStatusEvent(
    val taskId: UUID,
    val status: Status,
    createdAt: Long = System.currentTimeMillis(),
) : Event<TaskAggregate>(
    name = CHANGE_TASK_STATUS_EVENT,
    createdAt = createdAt
)