package space.borisgk.taxi.api.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import space.borisgk.taxi.api.exception.ServerException;
import space.borisgk.taxi.api.model.dto.MessageDto;
import space.borisgk.taxi.api.model.entity.Message;
import space.borisgk.taxi.api.model.entity.Trip;
import space.borisgk.taxi.api.model.entity.User;
import space.borisgk.taxi.api.service.MessageService;
import space.borisgk.taxi.api.service.TripService;

import java.util.List;

@Component
public class MessageConsumer {

    private Logger logger = LoggerFactory.getLogger(TripConsumer.class);

    @Autowired
    private ObjectMapper om;
    @Autowired
    private MessageService messageService;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private Mapper mapper;

    @KafkaListener(topics = "request.message.create", groupId = "server-java")
    public void messageCreate(String payload) throws ServerException {
        try {
            // TODO move to aspect
            logger.info("Receive payload:");
            logger.info(payload);

            MessageDto messageDto = om.readValue(payload, MessageDto.class);
            Message message = mapper.map(messageDto, Message.class);
            message.setUser(User.builder().id(messageDto.getUserId()).build());
            message.setTrip(Trip.builder().id(messageDto.getTripId()).build());
            message = messageService.saveMessage(message);
            messageDto = mapper.map(message, MessageDto.class);

            // TODO высылать только сокетам, которые слушают чат
            kafkaTemplate.send("response.message.create", om.writeValueAsString(messageDto));
        }
        catch (Exception e) {
            throw new ServerException(e);
        }
    }

    @KafkaListener(topics = "request.message.getByTripId", groupId = "server-java")
    public void messageGetByTripId(String payload) throws ServerException {
        try {
            // TODO move to aspect
            logger.info("Receive payload:");
            logger.info(payload);

            Integer tripId = Integer.parseInt(payload);
            List<Message> messages = messageService.getAllByTripId(tripId);
            List<MessageDto> messageDtos = mapper.map(messages, List.class);
            for (int i = 0; i < messageDtos.size(); i++) {
                messageDtos.get(i).setUserId(messages.get(i).getUser().getId());
                messageDtos.get(i).setTripId(messages.get(i).getTrip().getId());
            }

            // TODO высылать только сокетам, которые слушают чат
            kafkaTemplate.send("response.message.getByTripId", om.writeValueAsString(messageDtos));
        }
        catch (Exception e) {
            throw new ServerException(e);
        }
    }
}
