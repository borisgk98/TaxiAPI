package space.borisgk.taxi.api.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import space.borisgk.taxi.api.exception.ServerException;
import space.borisgk.taxi.api.model.dto.AuthServiceDataDTO;
import space.borisgk.taxi.api.model.dto.SocketDataWrapper;
import space.borisgk.taxi.api.model.dto.TripDto;
import space.borisgk.taxi.api.model.dto.UserDto;
import space.borisgk.taxi.api.model.dto.UserGerTripsRequest;
import space.borisgk.taxi.api.model.dto.UserUpdateFriendsRequest;
import space.borisgk.taxi.api.model.entity.AuthService;
import space.borisgk.taxi.api.model.entity.AuthServiceData;
import space.borisgk.taxi.api.model.entity.User;
import space.borisgk.taxi.api.model.mapping.StaxMapper;
import space.borisgk.taxi.api.service.UserService;

import java.net.Socket;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserConsumer {

    private Logger logger = LoggerFactory.getLogger(UserConsumer.class);

    private final ObjectMapper om;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final UserService userService;
    private final StaxMapper mapper;


    // TODO обработка отсутсвтующего socialIds
    // TODO обработка, если socialId == null
    // TODO валидация запроса
    // TODO маппинг юзера в json если отсутсвует hibernate session (возникает ожибка из за lazy initialization)
    @KafkaListener(topics = "request.user.data", groupId = "server-java")
    public void userData(String payload) throws ServerException {
        try {
            SocketDataWrapper socketDataWrapper = om.readValue(payload, SocketDataWrapper.class);
            UserDto userDto = null;
            userDto = om.readValue(socketDataWrapper.getPayload(), UserDto.class);
            User user = mapper.dto2e(userDto);
            Optional<User> userOptional = userService.getUserByAuthServiceData(user.getAuthServicesData());
            if (userOptional.isPresent()) {
                user = userOptional.get();
            }
            else {
                user = userService.create(user);
            }
            String result = om.writeValueAsString(SocketDataWrapper.builder().payload( om.writeValueAsString(mapper.e2dto(user))).socket(socketDataWrapper.getSocket()).build());
            kafkaTemplate.send("response.user.data", result);
        }
        catch (Exception e) {
            throw new ServerException(e);
        }
    }

    @KafkaListener(topics = "request.user.get", groupId = "server-java")
    public void userGet(String payload) throws ServerException  {
        try {
            SocketDataWrapper socketDataWrapper = om.readValue(payload, SocketDataWrapper.class);
            Integer id = Integer.parseInt(socketDataWrapper.getPayload());
            UserDto userDto = mapper.e2dto(userService.read(id));
            String result = om.writeValueAsString(SocketDataWrapper.builder().payload(om.writeValueAsString(userDto)).socket(socketDataWrapper.getSocket()).build());
            kafkaTemplate.send("response.user.get", result);
        }
        catch (Exception e) {
            throw new ServerException(e);
        }
    }

    // TODO вынести groupId и topics в кофигурацию или final static поля
    @KafkaListener(topics = "request.user.update.friends", groupId = "server-java")
    public void updateFriends(String payload) throws ServerException {
        try {
            SocketDataWrapper socketDataWrapper = om.readValue(payload, SocketDataWrapper.class);
            UserUpdateFriendsRequest userUpdateFriendsRequest = om.readValue(socketDataWrapper.getPayload(), UserUpdateFriendsRequest.class);
            userService.updateFriends(
                    Integer.parseInt(userUpdateFriendsRequest.getUserId()),
                    AuthServiceData.builder()
                            .authService(AuthService.fromName(userUpdateFriendsRequest.getAuthService()))
                            .friendsHash(userUpdateFriendsRequest.getAuthServiceData().getFriendsHash())
                            .socialId(userUpdateFriendsRequest.getAuthServiceData().getSocialId())
                            .build(),
                    userUpdateFriendsRequest.getNewFriendsSocialIds(),
                    userUpdateFriendsRequest.getDeletedFriendsSocialIds()
            );
            String result = om.writeValueAsString(SocketDataWrapper.builder().payload("ok").socket(socketDataWrapper.getSocket()).build());
            kafkaTemplate.send("response.user.update.friends", result);
        }
        catch (Exception e) {
            throw new ServerException(e);
        }
    }

    @KafkaListener(topics = "request.user.get.trips", groupId = "server-java")
    public void getTrips(String payload) throws ServerException {
        try {
            SocketDataWrapper socketDataWrapper = om.readValue(payload, SocketDataWrapper.class);
            UserGerTripsRequest userGerTripsRequest = om.readValue(socketDataWrapper.getPayload(), UserGerTripsRequest.class);
            List<TripDto> tripDtos = userService.getTrips(
                    Long.parseLong(userGerTripsRequest.getUserId()),
                    userGerTripsRequest.getTripStatus()
            ).stream().map(mapper::e2dto).collect(Collectors.toList());
            String result = om.writeValueAsString(SocketDataWrapper.builder().payload(om.writeValueAsString(tripDtos)).socket(socketDataWrapper.getSocket()).build());
            kafkaTemplate.send("response.user.get.trips", result);
        }
        catch (Exception e) {
            throw new ServerException(e);
        }
    }
}
