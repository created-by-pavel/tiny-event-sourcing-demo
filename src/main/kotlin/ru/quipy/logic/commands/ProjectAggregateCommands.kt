package ru.quipy.logic.commands

import ru.quipy.api.*
import ru.quipy.exception.TaskManagerException
import ru.quipy.logic.state.ProjectAggregateState
import ru.quipy.logic.state.TaskStatusEntity
import ru.quipy.logic.state.TaskStatusEntity.Companion.DEFAULT_TASK_STATUS_COLOR
import ru.quipy.logic.state.TaskStatusEntity.Companion.DEFAULT_TASK_STATUS_NAME
import java.util.*


// Commands : takes something -> returns event
// Here the commands are represented by extension functions, but also can be the class member functions

fun ProjectAggregateState.create(id: UUID, projectTitle: String): ProjectCreatedEvent {
    val defaultTaskStatusEntity = TaskStatusEntity(UUID.randomUUID(), DEFAULT_TASK_STATUS_NAME, DEFAULT_TASK_STATUS_COLOR)
    return ProjectCreatedEvent(
        projectId = id,
        projectTitle = projectTitle,
        defaultTaskStatusEntity = defaultTaskStatusEntity
    )
}

fun ProjectAggregateState.updateProjectInfo(newTitle: String): ProjectInfoUpdatedEvent {
    return ProjectInfoUpdatedEvent(projectId = this.getId(), newTitle = newTitle)
}

fun ProjectAggregateState.leaveProject(userId: UUID): UserLeftProjectEvent {
    return UserLeftProjectEvent(projectId = this.getId(), userId = userId)
}

fun ProjectAggregateState.joinUserToProject(userId: UUID): UserJoinedToProjectEvent {
    if (this.members.contains(userId)) throw TaskManagerException("User has already been joined to project");
    return UserJoinedToProjectEvent(projectId = this.getId(), userId = userId)
}

fun ProjectAggregateState.createTaskStatus(taskStatusName: String, color: String): TaskStatusCreatedEvent {
    return TaskStatusCreatedEvent(projectId = this.getId(), taskStatusId = UUID.randomUUID(), taskStatusName = taskStatusName, color = color)
}

fun ProjectAggregateState.deleteTaskStatus(taskStatusId: UUID): TaskStatusDeletedEvent {
    return TaskStatusDeletedEvent(this.getId(), taskStatusId = taskStatusId)
}