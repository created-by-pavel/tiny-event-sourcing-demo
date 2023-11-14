package ru.quipy.projection.project

import java.util.*

class ProjectViewDto {
    data class ProjectDto(
        val projectId: UUID,
        val projectTitle: String,
        val members: List<UUID>,
        val statuses: List<ProjectViewDomain.Status>,
        val tasks: List<ProjectViewDomain.Task>
    )

    data class TaskDto(
        val taskId: UUID,
        val taskTitle: String,
        val taskDescription: String,
        val taskStatus: StatusDto
    )

    data class StatusDto(
        val statusId: UUID,
        val statusName: String,
        val color: String
    )

    data class MemberProjectsDto(
        val memberId: UUID,
        val projects: List<ProjectViewDomain.Project>
    )
}