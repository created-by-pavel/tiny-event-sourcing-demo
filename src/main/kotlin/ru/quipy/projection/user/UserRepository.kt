package ru.quipy.projection.user

import org.springframework.data.mongodb.repository.MongoRepository
import java.util.UUID

interface UserRepository : MongoRepository<UserViewDomain.User, UUID>