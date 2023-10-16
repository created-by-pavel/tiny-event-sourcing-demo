package ru.quipy.controller

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.quipy.api.*
import ru.quipy.core.EventSourcingService
import ru.quipy.logic.*
import java.util.*

@RestController
@RequestMapping("/projects")
class ProjectController(
    val projectEsService: EventSourcingService<UUID, ProjectAggregate, ProjectAggregateState>
) {

    @PostMapping("/{projectTitle}")
    fun createProject(@PathVariable projectTitle: String) : ProjectCreatedEvent {
        return projectEsService.create { it.create(UUID.randomUUID(), projectTitle) }
    }

    @PutMapping("/{projectId}/info")
    fun updateTask(@PathVariable projectId: UUID, @RequestParam projectTitle: String) : UpdateProjectInfoEvent {
        return projectEsService.update(projectId) { it.updateInfo(projectTitle) }
    }


    @GetMapping("/{projectId}")
    fun getProject(@PathVariable projectId: UUID) : ProjectAggregateState? {
        return projectEsService.getState(projectId)
    }

    @PostMapping("/{projectId}/members/{userId}")
    fun addMember(@PathVariable projectId: UUID, @PathVariable userId: UUID) : UserAddedEvent {
        return projectEsService.update(projectId) {
            it.addMember(userId)
        }
    }

    @PostMapping("/{projectId}/tasks/{taskId}")
    fun addTask(@PathVariable projectId: UUID, @PathVariable taskId: UUID) : TaskAddedEvent {
        return projectEsService.update(projectId) {
            it.addTask(taskId)
        }
    }

    @DeleteMapping("/{projectId}/tasks/{taskId}")
    fun deleteTask(@PathVariable projectId: UUID, @PathVariable taskId: UUID) : TaskDeletedEvent {
        return projectEsService.update(projectId) {
            it.delTask(taskId)
        }
    }
}