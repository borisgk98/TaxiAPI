package space.borisgk.taxi.api.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import space.borisgk.taxi.api.model.entity.User

@Repository
interface UserRepository : JpaRepository<User, Int> {
    fun getUserByVkId(vkId: Int?): User
}
