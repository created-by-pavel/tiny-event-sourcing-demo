package ru.quipy.logic

import ru.quipy.api.*
import ru.quipy.core.annotations.StateTransitionFunc
import ru.quipy.domain.AggregateState
import java.util.*

// Service's business logic
class ProjectAggregateState : AggregateState<UUID, ProjectAggregate> {
    private lateinit var projectId: UUID
    var createdAt: Long = System.currentTimeMillis()
    var updatedAt: Long = System.currentTimeMillis()

    lateinit var projectTitle: String
    var tasks = mutableSetOf<UUID>()
    var members = mutableSetOf<UUID>()

    override fun getId() = projectId

    // State transition functions which is represented by the class member function
    @StateTransitionFunc
    fun projectCreatedApply(event: ProjectCreatedEvent) {
        projectId = event.projectId
        projectTitle = event.title
        updatedAt = createdAt
    }

    @StateTransitionFunc
    fun updateProjectApply(event: UpdateProjectInfoEvent) {
        projectTitle = event.title
        updatedAt = event.createdAt
    }

    @StateTransitionFunc
    fun memberAddedApply(event: UserAddedEvent) {
        members.add(event.userId)
        updatedAt = createdAt
    }

    @StateTransitionFunc
    fun taskAddedApply(event: TaskAddedEvent) {
        tasks.add(event.taskId)
        updatedAt = createdAt
    }

    @StateTransitionFunc
    fun taskDeletedApply(event: TaskDeletedEvent) {
        tasks.remove(event.taskId)
        updatedAt = createdAt
    }
}
