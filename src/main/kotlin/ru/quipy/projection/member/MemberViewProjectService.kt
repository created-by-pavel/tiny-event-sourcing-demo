package ru.quipy.projection.member

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.quipy.api.*
import ru.quipy.projection.member.repository.MemberViewProjectRepository
import ru.quipy.projection.member.repository.MemberViewUserRepository
import ru.quipy.streams.annotation.AggregateSubscriber
import ru.quipy.streams.annotation.SubscribeEvent
import java.util.*

@Service
@AggregateSubscriber(aggregateClass = ProjectAggregate::class, subscriberName = "members-project-subs-stream")
class MemberViewProjectService (val projectRepository: MemberViewProjectRepository) {
    val logger: Logger = LoggerFactory.getLogger(MemberViewProjectService::class.java)

    @SubscribeEvent
    fun projectCreatedSubscriber(event: ProjectCreatedEvent) {
        projectRepository.save(ProjectMembersViewDomain.Project(event.projectId))
    }

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
@AggregateSubscriber(aggregateClass = UserAggregate::class, subscriberName = "members-user-subs-stream")
class MemberViewUserService (val userRepository: MemberViewUserRepository) {
    val logger: Logger = LoggerFactory.getLogger(MemberViewUserService::class.java)

    @SubscribeEvent
    fun userAddedSubscriber(event: UserCreatedEvent) {
        logger.info("User created: {}", event.userId)
        userRepository.save(ProjectMembersViewDomain.User(event.userId, event.nickName))
    }

    fun findUsersByIds(userIds: Set<UUID>): List<ProjectMembersViewDomain.User> {
        return userRepository.findAllById(userIds).toList()
    }
}