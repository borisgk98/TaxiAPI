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
    private lateinit var om: ObjectMapper
    @Autowired
    private lateinit var messageService: MessageService
    @Autowired
    private lateinit var kafkaTemplate: KafkaTemplate<String, String>
    @Autowired
    private lateinit var mapper: Mapper

    @KafkaListener(topics = ["request.message.create"])
    @Throws(ServerException::class)
    fun messageCreate(payload: String) {
        try {
            var messageDto = om.readValue(payload, MessageDto::class.java)
            var message = mapper.map(messageDto, Message::class.java)
            message.user = User(id = messageDto.userId)
            message.trip = Trip(id = messageDto.tripId)
            message = messageService.saveMessage(message)
            messageDto = mapper.map(message, MessageDto::class.java)

            // TODO высылать только сокетам, которые слушают чат
            kafkaTemplate.send("response.message.create", om.writeValueAsString(messageDto))
        } catch (e: Exception) {
            throw ServerException(e)
        }

    }

    @KafkaListener(topics = ["request.message.getByTripId"])
    @Throws(ServerException::class)
    fun messageGetByTripId(payload: String) {
        try {
            val tripId = Integer.parseInt(payload)
            val messages = messageService.getAllByTripId(tripId)
            val messageDtos = mapper.map(messages, List::class.java) as List<MessageDto>
            for (i in messageDtos.indices) {
                messageDtos[i].userId = messages.get(i).user!!.id
                messageDtos[i].tripId = messages.get(i).trip!!.id
            }

            // TODO высылать только сокетам, которые слушают чат
            kafkaTemplate.send("response.message.getByTripId", om.writeValueAsString(messageDtos))
        } catch (e: Exception) {
            throw ServerException(e)
        }

    }
}
