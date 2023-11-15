package ru.quipy.projection.project.repository

import org.springframework.data.mongodb.repository.MongoRepository
import ru.quipy.projection.project.ProjectViewDomain
import java.util.*

interface ProjectViewStatusRepository : MongoRepository<ProjectViewDomain.Status, UUID> {
    fun findAllByProjectId(projectId: UUID): List<ProjectViewDomain.Status>
}