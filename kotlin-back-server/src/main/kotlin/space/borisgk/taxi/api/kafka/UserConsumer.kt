package space.borisgk.taxi.api.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import org.dozer.Mapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import space.borisgk.taxi.api.exception.ServerException
import space.borisgk.taxi.api.model.dto.UserDto
import space.borisgk.taxi.api.model.entity.User
import space.borisgk.taxi.api.service.UserService
import space.borisgk.taxi.api.tool.Converter

@Component
class UserConsumer {

    private val logger = LoggerFactory.getLogger(UserConsumer::class.java)

    @Autowired
    private val om: ObjectMapper? = null
    @Autowired
    private val kafkaTemplate: KafkaTemplate<String, String>? = null
    @Autowired
    private val userService: UserService? = null
    @Autowired
    private val mapper: Mapper? = null

    @KafkaListener(topics = ["request.user.data"])
    @Throws(ServerException::class)
    fun userData(payload: String) {
        try {
            var userDto: UserDto? = null
            userDto = om!!.readValue<UserDto>(payload, UserDto::class.java!!)
            var user: User? = null
            if (userDto!!.vkId != null) {
                user = userService!!.getUserByVkId(userDto!!.vkId)
            }
            if (user == null) {
                user = userService!!.saveUser(mapper!!.map<User>(userDto, User::class.java))
            }
            val res = user!!.id!!.toString()
            kafkaTemplate!!.send("response.user.data", res)
        } catch (e: Exception) {
            throw ServerException(e)
        }

    }

    @KafkaListener(topics = ["request.user.get"])
    @Throws(ServerException::class)
    fun userGet(payload: String) {
        //        UserDto userDto = null;
        //        try {
        //            userDto = om.readValue(payload, UserDto.class);
        //        }
        //        catch (Exception e) {
        //            logger.error("Error while deserialize user object from payload");
        //        }
    }
}
