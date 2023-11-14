package ru.quipy.projection.project

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/project-view")
class ProjectViewController(
    private val projectService: ProjectService,
    private val statusService: StatusService,
    private val taskService: TaskService,
) {
    @GetMapping("/{projectId}")
    fun getProjectById(@PathVariable projectId: UUID): ProjectViewDto.ProjectDto? {
        val project = projectService.findProjectById(projectId) ?: return null
        val statuses = statusService.findStatusesByProjectId(projectId)
        val tasks = taskService.findTasksByProjectId(projectId)
        return ProjectViewDto.ProjectDto(
            projectId = project.id,
            projectTitle = project.projectTitle,
            members = project.members.toList(),
            statuses = statuses,
            tasks = tasks)
    }

    @GetMapping
    fun getAllProjectsByMemberId(@RequestParam memberId: UUID): ProjectViewDto.MemberProjectsDto? {
        val projects = projectService.findProjectsByUserId(memberId)
        return ProjectViewDto.MemberProjectsDto(
            memberId = memberId,
            projects = projects
        )
    }

    @GetMapping("/tasks/{taskId}")
    fun getTaskById(@PathVariable taskId: UUID): ProjectViewDto.TaskDto? {
        val task = taskService.findTaskById(taskId) ?: return null
        val status = statusService.findStatusById(task.taskStatusId) ?: return null
        return ProjectViewDto.TaskDto(
            taskId = task.id,
            taskTitle = task.taskTitle,
            taskDescription = task.taskDescription,
            taskStatus = ProjectViewDto.StatusDto(
                statusId = status.id,
                statusName = status.statusName,
                color = status.color
            )
        )
    }

    @GetMapping("/statuses")
    fun getStatusesByProjectId(@RequestParam projectId: UUID): List<ProjectViewDto.StatusDto> {
        val statuses = statusService.findStatusesByProjectId(projectId)
        return statuses.map {
            ProjectViewDto.StatusDto(
                statusId = it.id,
                statusName = it.statusName,
                color = it.color
            )
        }
    }
}

