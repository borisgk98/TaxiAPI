package space.borisgk.taxi.api.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import space.borisgk.taxi.api.converter.Converter;
import space.borisgk.taxi.api.exception.ServerException;
import space.borisgk.taxi.api.model.AuthService;
import space.borisgk.taxi.api.model.dto.UserDto;
import space.borisgk.taxi.api.model.entity.AuthServiceData;
import space.borisgk.taxi.api.model.entity.User;
import space.borisgk.taxi.api.service.UserService;

import java.util.Optional;

@Component
public class UserConsumer {

    private Logger logger = LoggerFactory.getLogger(UserConsumer.class);

    @Autowired
    private ObjectMapper om;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private UserService userService;
    @Autowired
    private Mapper mapper;
    @Autowired
    private Converter<User, UserDto> userUserDtoConverter;
    @Autowired
    private Converter<UserDto, User> userDtoUserConverter;

    // TODO обработка отсутсвтующего socialIds
    @KafkaListener(topics = "request.user.data", groupId = "server-java")
    public void userData(String payload) throws ServerException {
        try {
            UserDto userDto = null;
            userDto = om.readValue(payload, UserDto.class);
            User user = userDtoUserConverter.map(userDto);
            Optional<User> userOptional = userService.getUserByAuthServiceData(user.getAuthServicesData());
            if (userOptional.isPresent()) {
                user = userOptional.get();
            }
            else {
                user = userService.saveUser(user);
            }
            String res = user.getId().toString();
            kafkaTemplate.send("response.user.data", res);
        }
        catch (Exception e) {
            throw new ServerException(e);
        }
    }

    @KafkaListener(topics = "request.user.get", groupId = "server-java")
    public void userGet(String payload) throws ServerException  {
        logger.info("Receive payload:");
        logger.info(payload);
    }
}
