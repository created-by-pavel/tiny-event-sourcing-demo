package ru.quipy.projection.member

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/project-members-view")
class ProjectMembersViewController(
    val service: ProjectMembersViewService,
    val userService: UserService) {

    @GetMapping("/{projectId}")
    fun getProjectMembersByProjectId(@PathVariable projectId: UUID): ProjectMembersViewDto? {
        val project = service.findProjectMembersByProjectId(projectId) ?: return null
        val users = userService.findUsersByIds(project.members)
        return ProjectMembersViewDto(
            projectId = projectId,
            members = users)
    }

}

data class ProjectMembersViewDto(
    val projectId: UUID,
    val members: List<ProjectMembersViewDomain.User>
)