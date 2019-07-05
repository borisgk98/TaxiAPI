package space.borisgk.taxi.api.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service
import space.borisgk.taxi.api.model.entity.User
import space.borisgk.taxi.api.repository.UserRepository

@Service
class UserService {
    @Autowired
    private val userRepository: UserRepository? = null

    fun saveUser(user: User): User {
        return userRepository!!.saveAndFlush(user)
    }

    fun getUser(userId: Int?): User {
        return userRepository!!.getOne(userId!!)
    }

    fun getUserByVkId(vkId: Int?): User {
        return userRepository!!.getUserByVkId(vkId)
    }
}
