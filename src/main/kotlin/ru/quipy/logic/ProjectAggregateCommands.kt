package ru.quipy.logic

import ru.quipy.api.*
import java.util.*


// Commands : takes something -> returns event
// Here the commands are represented by extension functions, but also can be the class member functions

fun ProjectAggregateState.create(id: UUID, title: String): ProjectCreatedEvent {
    return ProjectCreatedEvent(
        projectId = id,
        title = title
    )
}

fun ProjectAggregateState.updateInfo(title: String): UpdateProjectInfoEvent {
    return UpdateProjectInfoEvent(projectId = this.getId(), title = title)
}

fun ProjectAggregateState.addMember(userId: UUID): UserAddedEvent {
    return UserAddedEvent(
        projectId = this.getId(),
        userId = userId,
    )
}

fun ProjectAggregateState.addTask(taskId: UUID): TaskAddedEvent {
    return TaskAddedEvent(
        projectId = this.getId(),
        taskId = taskId,
    )
}

fun ProjectAggregateState.delTask(taskId: UUID): TaskDeletedEvent {
    return TaskDeletedEvent(
        projectId = this.getId(),
        taskId = taskId,
    )
}