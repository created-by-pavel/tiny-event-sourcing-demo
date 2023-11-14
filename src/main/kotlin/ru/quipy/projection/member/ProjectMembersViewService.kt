package ru.quipy.projection.member

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.quipy.api.ProjectAggregate
import ru.quipy.api.UserAggregate
import ru.quipy.api.UserCreatedEvent
import ru.quipy.api.UserJoinedToProjectEvent
import ru.quipy.projection.member.repository.ProjectRepository
import ru.quipy.projection.member.repository.UserRepository
import ru.quipy.streams.annotation.AggregateSubscriber
import ru.quipy.streams.annotation.SubscribeEvent
import java.util.*

@Service
@AggregateSubscriber(aggregateClass = ProjectAggregate::class, subscriberName = "members-subs-stream")
class ProjectMembersViewService (val projectRepository: ProjectRepository) {
    val logger: Logger = LoggerFactory.getLogger(ProjectMembersViewService::class.java)

    @SubscribeEvent
    fun memberAddedSubscriber(event: UserJoinedToProjectEvent) {
        logger.info("User joined: {}", event.userId)
        val project = projectRepository.findById(event.projectId).orElse(null)
        if (project != null) {
            project.members.add(event.userId)
            projectRepository.save(project)
        }
    }

    fun findProjectMembersByProjectId(projectId: UUID): ProjectMembersViewDomain.Project? {
        return projectRepository.findById(projectId).orElse(null)
    }
}

@Service
@AggregateSubscriber(aggregateClass = UserAggregate::class, subscriberName = "members-subs-stream")
class UserService (val userRepository: UserRepository) {
    val logger: Logger = LoggerFactory.getLogger(UserService::class.java)

    @SubscribeEvent
    fun userAddedSubscriber(event: UserCreatedEvent) {
        logger.info("User created: {}", event.userId)
        userRepository.save(ProjectMembersViewDomain.User(event.userId, event.nickName))
    }

    fun findUsersByIds(userIds: Set<UUID>): List<ProjectMembersViewDomain.User> {
        return userRepository.findAllById(userIds).toList()
    }
}