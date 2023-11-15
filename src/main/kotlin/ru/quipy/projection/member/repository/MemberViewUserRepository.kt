package ru.quipy.projection.member.repository

import org.springframework.data.mongodb.repository.MongoRepository
import ru.quipy.projection.member.ProjectMembersViewDomain
import java.util.*

interface MemberViewUserRepository : MongoRepository<ProjectMembersViewDomain.User, UUID>