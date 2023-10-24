package ru.quipy.logic.state

import ru.quipy.api.*
import ru.quipy.core.annotations.StateTransitionFunc
import ru.quipy.domain.AggregateState
import java.util.*

class TaskAggregateState : AggregateState<UUID, TaskAggregate> {
    private lateinit var taskId: UUID
    private var createdAt: Long = System.currentTimeMillis()
    private var updatedAt: Long = System.currentTimeMillis()

    lateinit var projectId: UUID
    lateinit var taskTitle: String
    lateinit var taskDescription: String
    lateinit var taskStatusId: UUID
    private val executors = mutableSetOf<UUID>()

    override fun getId(): UUID  = taskId

    @StateTransitionFunc
    fun taskCreatedApply(event: TaskCreatedEvent) {
        taskId = event.taskId
        createdAt = event.createdAt
        updatedAt = event.createdAt
        projectId = event.projectId
        taskTitle = event.taskTitle
        taskDescription = event.taskDescription
        taskStatusId = event.taskStatusId
    }

    @StateTransitionFunc
    fun taskAssignedApply(event: TaskAssignedEvent) {
        executors.add(event.userId)
        updatedAt = event.createdAt
    }

    @StateTransitionFunc
    fun taskUnassignedApply(event: TaskUnassignedEvent) {
        executors.remove(event.userId)
        updatedAt = event.createdAt
    }

    @StateTransitionFunc
    fun taskNameUpdatedApply(event: TaskInfoUpdatedEvent) {
        taskTitle = event.newTitle
        taskDescription = event.newDescription
        updatedAt = event.createdAt
    }

    @StateTransitionFunc
    fun taskStatusUpdatedApply(event: TaskStatusUpdatedEvent) {
        taskStatusId = event.newTaskStatusId
        updatedAt = event.createdAt
    }
}