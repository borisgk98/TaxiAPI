package space.borisgk.taxi.api.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import space.borisgk.taxi.api.model.entity.Message
import space.borisgk.taxi.api.repository.MessageRepository

@Service
class MessageService {

    @Autowired
    private val messageRepository: MessageRepository? = null

    fun saveMessage(message: Message): Message {
        return messageRepository!!.saveAndFlush(message)
    }

    fun getAllByTripId(tripId: Int?): List<Message> {
        return messageRepository!!.getAllByTripId(tripId)
    }
}
