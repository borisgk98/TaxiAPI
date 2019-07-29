package space.borisgk.taxi.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import space.borisgk.taxi.api.model.entity.Message;
import space.borisgk.taxi.api.repository.MessageRepository;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public Message saveMessage(Message message) {
        return messageRepository.saveAndFlush(message);
    }

    public List<Message> getAllByTripId(Integer tripId) {
        return messageRepository.getAllByTripId(tripId);
    }
}
