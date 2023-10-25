package ru.quipy.controller

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.quipy.api.*
import ru.quipy.logic.*
import ru.quipy.logic.service.ProjectService
import ru.quipy.logic.state.ProjectAggregateState
import ru.quipy.logic.state.TaskAggregateState
import java.util.*

@RestController
@RequestMapping("/projects")
class ProjectController(
    private val projectService: ProjectService
) {

    @PostMapping("/{projectTitle}")
    fun createProject(@PathVariable projectTitle: String): ProjectCreatedEvent {
        return projectService.createProject(projectTitle)
    }

    @GetMapping("/{projectId}")
    fun getProject(@PathVariable projectId: UUID): ProjectAggregateState? {
        return projectService.getProject(projectId)
    }

    @PatchMapping("/{projectId}")
    fun updateProjectInfo(@PathVariable projectId: UUID, @RequestParam projectTitle: String): ProjectInfoUpdatedEvent? {
        return projectService.updateProjectInfo(projectId, projectTitle)
    }

    @DeleteMapping("/{projectId}/members/{userId}")
    fun removeMember(@PathVariable projectId: UUID, @PathVariable userId: UUID): UserLeftProjectEvent {
        return projectService.removeMember(projectId, userId)
    }

    @PostMapping("/{projectId}/members/{userId}")
    fun addMember(@PathVariable projectId: UUID, @PathVariable userId: UUID): UserJoinedToProjectEvent? {
        return projectService.addMember(projectId, userId)
    }

    @PostMapping("/{projectId}/taskStatus")
    fun createTaskStatus(@PathVariable projectId: UUID, @RequestBody dto: TaskStatusDto): TaskStatusCreatedEvent {
        return projectService.createTaskStatus(projectId, dto)
    }

    @DeleteMapping("/{projectId}/taskStatus")
    fun deleteTaskStatus(@PathVariable projectId: UUID, @RequestParam taskStatusId: UUID): TaskStatusDeletedEvent {
        return projectService.deleteTaskStatus(projectId, taskStatusId)
    }

    @PostMapping("/{projectId}/tasks")
    fun createTask(
        @PathVariable projectId: UUID,
        @RequestParam title: String,
        @RequestParam description: String,
    ): TaskCreatedEvent {
        return projectService.createTask(projectId, title, description)
    }

    @GetMapping("/tasks/{taskId}")
    fun getTask(@PathVariable taskId: UUID): TaskAggregateState {
        return projectService.getTask(taskId)
    }

    @PostMapping("/{projectId}/tasks/{taskId}/executor")
    fun addExecutor(
        @PathVariable projectId: UUID,
        @PathVariable taskId: UUID,
        @RequestParam userId: UUID
    ): TaskAssignedEvent {
        return projectService.assignTask(projectId, taskId, userId)
    }

    @DeleteMapping("/{projectId}/tasks/{taskId}/executor")
    fun removeExecutor(
        @PathVariable projectId: UUID,
        @PathVariable taskId: UUID,
        @RequestParam userId: UUID
    ): TaskUnassignedEvent {
        return projectService.unassignTask(projectId, taskId, userId)
    }

    @PutMapping("/tasks/{taskId}/info")
    fun updateTask(
        @PathVariable taskId: UUID,
        @RequestParam newTitle: String,
        @RequestParam newDescription: String,
    ): TaskInfoUpdatedEvent {
        return projectService.updateTask(taskId, newTitle, newDescription)
    }

    @PutMapping("/{projectId}/tasks/{taskId}/taskStatus")
    fun updateTaskStatus(
        @PathVariable projectId: UUID, @PathVariable taskId: UUID,
        @RequestParam newTaskStatusId: UUID,
    ): TaskStatusUpdatedEvent {
        return projectService.updateTaskStatusId(projectId, taskId, newTaskStatusId)
    }
}

data class TaskStatusDto(
    val name: String,
    val color: String
)