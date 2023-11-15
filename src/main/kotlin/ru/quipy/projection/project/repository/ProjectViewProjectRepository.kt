package ru.quipy.projection.project.repository

import org.springframework.data.mongodb.repository.MongoRepository
import ru.quipy.projection.project.ProjectViewDomain
import java.util.UUID

interface ProjectViewProjectRepository : MongoRepository<ProjectViewDomain.Project, UUID> {
    fun findAllByMembersIsContaining(userId: UUID): List<ProjectViewDomain.Project>
}