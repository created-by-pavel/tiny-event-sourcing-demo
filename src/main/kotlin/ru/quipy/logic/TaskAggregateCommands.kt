package ru.quipy.logic

import ru.quipy.api.*
import java.util.*


// Commands : takes something -> returns event
// Here the commands are represented by extension functions, but also can be the class member functions

fun TaskAggregateState.create(title: String, description: String, projectId: UUID): TaskCreatedEvent {
    return TaskCreatedEvent(
        taskId = UUID.randomUUID(),
        title = title,
        description = description,
        projectId = projectId,
    )
}

fun TaskAggregateState.updateInfo(title: String, description: String): UpdateTasksInfoEvent {
    return UpdateTasksInfoEvent(taskId = this.getId(), title = title, description = description)
}

fun TaskAggregateState.addExecutor(executor: UUID): AddTaskExecutorEvent {
    return AddTaskExecutorEvent(taskId = this.getId(), executor = executor)
}

fun TaskAggregateState.delExecutor(executor: UUID): DelTaskExecutorEvent {
    return DelTaskExecutorEvent(taskId = this.getId(), executor = executor)
}

fun TaskAggregateState.changeStatus(status: Status): ChangeTaskStatusEvent {
    return ChangeTaskStatusEvent(taskId = this.getId(), status = status)
}

fun TaskAggregateState.deleteTask(): DeleteTaskEvent {
    return DeleteTaskEvent(taskId = this.getId())
}