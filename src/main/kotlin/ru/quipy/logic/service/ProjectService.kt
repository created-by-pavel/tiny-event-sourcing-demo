package ru.quipy.logic.service

import org.springframework.stereotype.Service
import ru.quipy.api.*
import ru.quipy.controller.TaskStatusDto
import ru.quipy.core.EventSourcingService
import ru.quipy.exception.TaskManagerException
import ru.quipy.logic.state.TaskStatusEntity.Companion.DEFAULT_TASK_STATUS_NAME
import ru.quipy.logic.commands.*
import ru.quipy.logic.state.ProjectAggregateState
import ru.quipy.logic.state.TaskAggregateState
import ru.quipy.logic.state.UserAggregateState
import java.util.*

@Service
class ProjectService(
    val projectEsService: EventSourcingService<UUID, ProjectAggregate, ProjectAggregateState>,
    val taskEsService: EventSourcingService<UUID, TaskAggregate, TaskAggregateState>,
    val userEsService: EventSourcingService<UUID, UserAggregate, UserAggregateState>
) {

    fun createProject(projectTitle: String): ProjectCreatedEvent {
        return projectEsService.create { it.create(UUID.randomUUID(), projectTitle) }
    }

    fun getProject(projectId: UUID): ProjectAggregateState? {
        return getProjectState(projectId)
    }

    fun createTask(projectId: UUID, taskTitle: String, taskDescription: String): TaskCreatedEvent {
        val projectState = getProjectState(projectId)
        val defaultStatusId = getDefaultStatusId(projectState)
        return taskEsService.create { it.create(projectId, UUID.randomUUID(), defaultStatusId, taskTitle, taskDescription) }
    }

    fun getTask(taskId: UUID): TaskAggregateState {
        return getTaskState(taskId)
    }

    fun updateProjectInfo(projectId: UUID, newTitle: String): ProjectInfoUpdatedEvent? {
        return projectEsService.update(projectId) { it.updateProjectInfo(newTitle) }
    }

    fun removeMember(projectId: UUID, userId: UUID): UserLeftProjectEvent {
        checkUserExists(userId)
        return projectEsService.update(projectId) { it.leaveProject(userId) }
    }

    fun addMember(projectId: UUID, userId: UUID): UserJoinedToProjectEvent? {
        val project = getProjectState(projectId);
        checkUserExists(userId)
        return projectEsService.update(projectId) { it.joinUserToProject(userId) }
    }

    fun createTaskStatus(projectId: UUID, dto: TaskStatusDto): TaskStatusCreatedEvent {
        return projectEsService.update(projectId) { it.createTaskStatus(dto.name, dto.color) }
    }

    fun deleteTaskStatus(projectId: UUID, taskStatusId: UUID): TaskStatusDeletedEvent {
        val projectState = getProjectState(projectId)
        return projectEsService.update(projectId) { it.deleteTaskStatus(taskStatusId) }
    }

    fun assignTask(projectId: UUID, taskId: UUID, userId: UUID): TaskAssignedEvent {
        val taskState = getTaskState(taskId)
        val projectState = getProjectState(projectId)
        if (taskState.projectId != projectId) throw TaskManagerException("Project doesn't have this task")
        if (!projectState.members.contains(userId)) throw TaskManagerException("Project doesn't have this user")
        return taskEsService.update(taskId) { it.assignTaskToUser(userId, taskId) }
    }

    fun unassignTask(projectId: UUID, taskId: UUID, userId: UUID): TaskUnassignedEvent {
        checkUserExists(userId)
        val taskState = getTaskState(taskId)
        val projectState = getProjectState(projectId)
        if (taskState.projectId != projectId) throw TaskManagerException("Project doesn't have this task")
        if (!projectState.members.contains(userId)) throw TaskManagerException("Project doesn't have this user")
        return taskEsService.update(taskId) { it.unassignUserFromTask(userId, taskId) }
    }

    fun updateTask(taskId: UUID, newTitle: String, newDescription: String): TaskInfoUpdatedEvent {
        val taskState = getTaskState(taskId)
        return taskEsService.update(taskId) { it.updateTask(taskId, newTitle, newDescription) }
    }

    fun updateTaskStatusId(projectId: UUID, taskId: UUID, newTaskStatusId: UUID): TaskStatusUpdatedEvent {
        val projectState = getProjectState(projectId)
        val taskState = getTaskState(taskId)
        if (!projectState.taskStatuses.keys.contains(newTaskStatusId)) throw TaskManagerException("Project doesn't have this status")
        if (taskState.projectId != projectId) throw TaskManagerException("Project doesn't have this task")
        return taskEsService.update(taskId) { it.updateTaskStatus(taskId, newTaskStatusId) }
    }

    private fun getProjectState(projectId: UUID): ProjectAggregateState {
        return projectEsService.getState(projectId) ?: throw TaskManagerException("Project not found")
    }

    private fun checkUserExists(userId: UUID): UserAggregateState {
        return userEsService.getState(userId) ?: throw TaskManagerException("User not found")
    }

    private fun getTaskState(taskId: UUID): TaskAggregateState {
        return taskEsService.getState(taskId) ?: throw TaskManagerException("Task not found")
    }

    private fun getDefaultStatusId(projectState: ProjectAggregateState) =
        projectState.taskStatuses.values.find { it.statusName == DEFAULT_TASK_STATUS_NAME }?.id
            ?: throw TaskManagerException("Project doesn't have default status")
}