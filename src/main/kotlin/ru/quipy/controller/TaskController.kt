package ru.quipy.controller

import org.springframework.web.bind.annotation.*
import ru.quipy.api.*
import ru.quipy.logic.service.ProjectService
import ru.quipy.logic.state.TaskAggregateState
import java.util.*

@RestController
@RequestMapping("/tasks")
class TaskController(
    private val projectService: ProjectService
) {
//    @PostMapping("/{projectId}")
//    fun createTask(
//        @PathVariable projectId: UUID,
//        @RequestParam title: String,
//        @RequestParam description: String,
//    ): TaskCreatedEvent {
//        return projectService.createTask(projectId, title, description)
//    }
//
//    @GetMapping("/{taskId}")
//    fun getTask(@PathVariable taskId: UUID): TaskAggregateState {
//        return projectService.getTask(taskId)
//    }
//
//    @PostMapping("executor/{projectId}&&{taskId}")
//    fun addExecutor(
//        @PathVariable projectId: UUID,
//        @PathVariable taskId: UUID,
//        @RequestParam userId: UUID
//    ): TaskAssignedEvent {
//        return projectService.assignTask(projectId, taskId, userId)
//    }
//
//    @DeleteMapping("executor/{projectId}&&{taskId}")
//    fun removeExecutor(
//        @PathVariable projectId: UUID,
//        @PathVariable taskId: UUID,
//        @RequestParam userId: UUID
//    ): TaskUnassignedEvent {
//        return projectService.unassignTask(projectId, taskId, userId)
//    }
//
//    @PutMapping("/info/{taskId}")
//    fun updateTask(
//        @PathVariable taskId: UUID,
//        @RequestParam newTitle: String,
//        @RequestParam newDescription: String,
//    ): TaskInfoUpdatedEvent {
//        return projectService.updateTask(taskId, newTitle, newDescription)
//    }
//
//    @PutMapping("taskStatus/{projectId}&&{taskId}")
//    fun updateTaskStatus(
//        @PathVariable projectId: UUID, @PathVariable taskId: UUID,
//        @RequestParam newTaskStatusId: UUID,
//    ): TaskStatusUpdatedEvent {
//        return projectService.updateTaskStatusId(projectId, taskId, newTaskStatusId)
//    }
}