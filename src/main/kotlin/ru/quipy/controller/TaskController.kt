package ru.quipy.controller

import org.springframework.web.bind.annotation.*
import ru.quipy.api.*
import ru.quipy.core.EventSourcingService
import ru.quipy.logic.*
import java.util.*

@RestController
@RequestMapping("/tasks")
class TaskController(
    val taskEsService: EventSourcingService<UUID, TaskAggregate, TaskAggregateState>
) {

    @PostMapping("/{projectId}")
    fun createTask(
        @PathVariable projectId: UUID,
        @RequestParam title: String,
        @RequestParam description: String
    ): TaskCreatedEvent {
        return taskEsService.create { it.create(title, description, projectId) }
    }

    @GetMapping("/{taskId}")
    fun getTask(@PathVariable taskId: UUID): TaskAggregateState? {
        return taskEsService.getState(taskId)
    }

    @PutMapping("/{taskId}/info")
    fun updateTask(
        @PathVariable taskId: UUID,
        @RequestParam title: String,
        @RequestParam description: String
    ): UpdateTasksInfoEvent {
        return taskEsService.update(taskId) { it.updateInfo(title, description) }
    }

    @PostMapping("/{taskId}/executor")
    fun addExecutor(@PathVariable taskId: UUID, @RequestParam executor: UUID): AddTaskExecutorEvent {
        return taskEsService.update(taskId) { it.addExecutor(executor) }
    }

    @DeleteMapping("/{taskId}/executor")
    fun delExecutor(@PathVariable taskId: UUID, @RequestParam executor: UUID): DelTaskExecutorEvent {
        return taskEsService.update(taskId) { it.delExecutor(executor) }
    }

    @PutMapping("/{taskId}/status")
    fun changeStatus(@PathVariable taskId: UUID, @RequestBody status: Status): ChangeTaskStatusEvent {
        val statusType = StatusType.values().getOrNull(status.id)
        if (statusType != null && statusType == status.statusType) {
            return taskEsService.update(taskId) { it.changeStatus(status) }
        } else {
            throw IllegalArgumentException("Invalid status ID or statusType: ${status.id}, ${status.statusType}")
        }
    }
}