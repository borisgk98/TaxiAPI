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
import space.borisgk.taxi.api.model.dto.MessageDto
import space.borisgk.taxi.api.model.entity.Message
import space.borisgk.taxi.api.model.entity.Trip
import space.borisgk.taxi.api.model.entity.User
import space.borisgk.taxi.api.service.MessageService
import space.borisgk.taxi.api.service.TripService

@Component
class MessageConsumer {

    private val logger = LoggerFactory.getLogger(TripConsumer::class.java!!)

    @Autowired
    private val om: ObjectMapper? = null
    @Autowired
    private val messageService: MessageService? = null
    @Autowired
    private val kafkaTemplate: KafkaTemplate<String, String>? = null
    @Autowired
    private val mapper: Mapper? = null

    @KafkaListener(topics = ["request.message.create"])
    @Throws(ServerException::class)
    fun messageCreate(payload: String) {
        try {
            // TODO move to aspect
            logger.info("Receive payload:")
            logger.info(payload)

            var messageDto = om!!.readValue<MessageDto>(payload, MessageDto::class.java!!)
            var message = mapper!!.map<Message>(messageDto, Message::class.java)
            message.user = User(id = messageDto.userId)
            message.trip = Trip(id = messageDto.tripId)
            message = messageService!!.saveMessage(message)
            messageDto = mapper.map<MessageDto>(message, MessageDto::class.java)

            // TODO высылать только сокетам, которые слушают чат
            kafkaTemplate!!.send("response.message.create", om.writeValueAsString(messageDto))
        } catch (e: Exception) {
            throw ServerException(e)
        }

    }

    @KafkaListener(topics = ["request.message.getByTripId"])
    @Throws(ServerException::class)
    fun messageGetByTripId(payload: String) {
        try {
            // TODO move to aspect
            logger.info("Receive payload:")
            logger.info(payload)

            val tripId = Integer.parseInt(payload)
            val messages = messageService!!.getAllByTripId(tripId)
            val messageDtos = mapper!!.map(messages, List::class.java) as List<MessageDto>
            for (i in messageDtos.indices) {
                messageDtos[i].userId = messages.get(i).user!!.id
                messageDtos[i].tripId = messages.get(i).trip!!.id
            }

            // TODO высылать только сокетам, которые слушают чат
            kafkaTemplate!!.send("response.message.getByTripId", om!!.writeValueAsString(messageDtos))
        } catch (e: Exception) {
            throw ServerException(e)
        }

    }
}
