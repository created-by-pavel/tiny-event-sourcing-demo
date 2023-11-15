package ru.quipy.projection.project.repository

import org.springframework.data.mongodb.repository.MongoRepository
import ru.quipy.projection.project.ProjectViewDomain
import java.util.*

interface ProjectViewTaskRepository : MongoRepository<ProjectViewDomain.Task, UUID> {
    fun findAllByProjectId(projectId: UUID): List<ProjectViewDomain.Task>
}