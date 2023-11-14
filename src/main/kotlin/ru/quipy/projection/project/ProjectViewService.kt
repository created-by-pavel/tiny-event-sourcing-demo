package ru.quipy.projection.project

import org.springframework.stereotype.Service
import ru.quipy.api.*
import ru.quipy.projection.project.repository.ProjectRepository
import ru.quipy.projection.project.repository.StatusRepository
import ru.quipy.projection.project.repository.TaskRepository
import ru.quipy.streams.annotation.AggregateSubscriber
import ru.quipy.streams.annotation.SubscribeEvent
import java.util.UUID

@Service
@AggregateSubscriber(
    aggregateClass = ProjectAggregate::class, subscriberName = "project-view-subs-stream"
)
class ProjectService(val projectRepository: ProjectRepository) {

    @SubscribeEvent
    fun projectCreatedSubscriber(event: ProjectCreatedEvent) {
        projectRepository.save(
            ProjectViewDomain.Project(
                event.projectId,
                event.createdAt,
                event.projectTitle,
            )
        )
    }

    @SubscribeEvent
    fun projectStatusAddedSubscriber(event: TaskStatusCreatedEvent) {
        projectRepository.findById(event.projectId).ifPresent {
            it.statuses.add(event.taskStatusId)
            projectRepository.save(it)
        }
    }

    @SubscribeEvent
    fun projectMemberAddedSubscriber(event: UserJoinedToProjectEvent) {
        projectRepository.findById(event.projectId).ifPresent {
            it.members.add(event.userId)
            projectRepository.save(it)
        }
    }

    @SubscribeEvent
    fun projectMemberRemovedSubscriber(event: UserLeftProjectEvent) {
        projectRepository.findById(event.projectId).ifPresent {
            it.members.remove(event.userId)
            projectRepository.save(it)
        }
    }

    @SubscribeEvent
    fun projectStatusRemovedSubscriber(event: TaskStatusDeletedEvent) {
        projectRepository.findById(event.projectId).ifPresent {
            it.statuses.remove(event.taskStatusId)
            projectRepository.save(it)
        }
    }

    @SubscribeEvent
    fun projectInfoUpdatedSubscriber(event: ProjectInfoUpdatedEvent) {
        projectRepository.findById(event.projectId).ifPresent {
            it.projectTitle = event.newTitle
            projectRepository.save(it)
        }
    }

    fun findProjectById(id: UUID): ProjectViewDomain.Project? {
        return projectRepository.findById(id).orElse(null)
    }

    fun findProjectsByUserId(userId: UUID): List<ProjectViewDomain.Project> {
        return projectRepository.findAllByMembersIsContaining(userId)
    }
}

@Service
@AggregateSubscriber(
    aggregateClass = TaskAggregate::class, subscriberName = "project-view-subs-stream"
)
class TaskService(val taskRepository: TaskRepository) {

    @SubscribeEvent
    fun taskCreatedSubscriber(event: TaskCreatedEvent) {
        taskRepository.save(
            ProjectViewDomain.Task(
                event.taskId,
                event.createdAt,
                event.projectId,
                event.taskTitle,
                event.taskDescription,
                event.taskStatusId,
            )
        )
    }

    @SubscribeEvent
    fun taskNameUpdatedSubscriber(event: TaskInfoUpdatedEvent) {
        taskRepository.findById(event.taskId).ifPresent {
            it.taskTitle = event.newTitle
            it.taskDescription = event.newDescription
            taskRepository.save(it)
        }
    }

    @SubscribeEvent
    fun taskExecutorAddedSubscriber(event: TaskAssignedEvent) {
        taskRepository.findById(event.taskId).ifPresent {
            it.executors.add(event.userId)
            taskRepository.save(it)
        }
    }

    @SubscribeEvent
    fun taskStatusChangedSubscriber(event: TaskStatusUpdatedEvent) {
        taskRepository.findById(event.taskId).ifPresent {
            it.taskStatusId = event.newTaskStatusId
            taskRepository.save(it)
        }
    }

   fun findTaskById(id: UUID): ProjectViewDomain.Task? {
        return taskRepository.findById(id).orElse(null)
    }

    fun findTasksByProjectId(projectId: UUID): List<ProjectViewDomain.Task> {
        return taskRepository.findAllByProjectId(projectId)
    }
}

@Service
@AggregateSubscriber(
    aggregateClass = ProjectAggregate::class, subscriberName = "project-status-view-subs-stream"
)
class StatusService(val statusRepository: StatusRepository) {

    @SubscribeEvent
    fun statusCreatedSubscriber(event: TaskStatusCreatedEvent) {
        statusRepository.save(
            ProjectViewDomain.Status(
                event.taskStatusId,
                event.projectId,
                event.taskStatusName,
                event.color,
            )
        )
    }

    @SubscribeEvent
    fun statusRemovedSubscriber(event: TaskStatusDeletedEvent) {
        statusRepository.deleteById(event.taskStatusId)
    }

    fun findStatusById(id: UUID): ProjectViewDomain.Status? {
        return statusRepository.findById(id).orElse(null)
    }

    fun findStatusesByProjectId(projectId: UUID): List<ProjectViewDomain.Status> {
        return statusRepository.findAllByProjectId(projectId)
    }
}

