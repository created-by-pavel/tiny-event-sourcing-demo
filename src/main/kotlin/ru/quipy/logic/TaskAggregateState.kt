package ru.quipy.logic

import ru.quipy.api.*
import ru.quipy.core.annotations.StateTransitionFunc
import ru.quipy.domain.AggregateState
import java.util.*

// Service's business logic
class TaskAggregateState : AggregateState<UUID, TaskAggregate> {
    private lateinit var taskId: UUID
    var createdAt: Long = System.currentTimeMillis()
    var updatedAt: Long = System.currentTimeMillis()

    lateinit var title: String
    lateinit var description: String
    lateinit var projectId: UUID
    lateinit var status: Status
    var executors = mutableSetOf<UUID>()

    override fun getId() = taskId

    @StateTransitionFunc
    fun taskCreatedApply(event: TaskCreatedEvent) {
        taskId = event.taskId
        title = event.title
        description = event.description
        projectId = event.projectId
        createdAt = event.createdAt
        updatedAt = createdAt
        status = Status(Status.DEFAULT_TASK_STATUS_ID, Status.DEFAULT_TASK_STATUS_NAME, Status.DEFAULT_TASK_STATUS_COLOR)
    }

    @StateTransitionFunc
    fun updateTaskApply(event: UpdateTasksInfoEvent) {
        title = event.title
        description = event.description
        updatedAt = event.createdAt
    }

    @StateTransitionFunc
    fun addTaskExecutorApply(event: AddTaskExecutorEvent) {
        executors.add(event.executor)
        updatedAt = event.createdAt
    }

    @StateTransitionFunc
    fun delTaskExecutorApply(event: DelTaskExecutorEvent) {
        executors.remove(event.executor)
        updatedAt = event.createdAt
    }

    @StateTransitionFunc
    fun changeTaskStatusApply(event: ChangeTaskStatusEvent) {
        status = event.status
        updatedAt = event.createdAt
    }
}

data class Status(
    val id: Int,
    val statusType: StatusType,
    val color: StatusColor
) {
    companion object {
        val DEFAULT_TASK_STATUS_ID = StatusType.CREATED.ordinal
        val DEFAULT_TASK_STATUS_NAME = StatusType.CREATED
        val DEFAULT_TASK_STATUS_COLOR = StatusColor.WHITE
    }
}

enum class StatusType { CREATED, IN_PROGRESS, COMPLETED, DELETED }
enum class StatusColor { WHITE, BLUE, GREEN, RED }
