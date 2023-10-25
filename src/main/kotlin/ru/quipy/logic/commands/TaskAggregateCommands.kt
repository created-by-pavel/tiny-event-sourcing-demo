package ru.quipy.logic.commands
import ru.quipy.api.*
import ru.quipy.logic.state.TaskAggregateState
import java.util.*

fun TaskAggregateState.create(projectId: UUID, taskId: UUID, taskStatusId: UUID, taskTitle: String, taskDescription: String) : TaskCreatedEvent {
    return TaskCreatedEvent(projectId, taskId, taskStatusId, taskTitle, taskDescription)
}

fun TaskAggregateState.assignTaskToUser(userId: UUID, taskId: UUID): TaskAssignedEvent {
    return TaskAssignedEvent(userId, taskId)
}

fun TaskAggregateState.unassignUserFromTask(userId: UUID, taskId: UUID): TaskUnassignedEvent {
    return TaskUnassignedEvent(userId, taskId)
}

fun TaskAggregateState.updateTask(taskId: UUID, newTitle: String, newDescription: String): TaskInfoUpdatedEvent {
    return TaskInfoUpdatedEvent(taskId, newTitle, newDescription)
}

fun TaskAggregateState.updateTaskStatus(taskId: UUID, newTaskStatusId: UUID): TaskStatusUpdatedEvent {
    return TaskStatusUpdatedEvent(taskId, newTaskStatusId)
}