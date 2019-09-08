package space.borisgk.taxi.api.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import space.borisgk.taxi.api.converter.UserToUserDtoConverter;
import space.borisgk.taxi.api.exception.ServerException;
import space.borisgk.taxi.api.model.TripStatus;
import space.borisgk.taxi.api.model.dto.SocketDataWrapper;
import space.borisgk.taxi.api.model.dto.TripDto;
import space.borisgk.taxi.api.model.dto.TripAndUserRequest;
import space.borisgk.taxi.api.model.entity.Trip;
import space.borisgk.taxi.api.service.TripService;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TripConsumer {

    private Logger logger = LoggerFactory.getLogger(TripConsumer.class);

    @Autowired
    private ObjectMapper om;
    @Autowired
    private TripService tripService;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private Mapper mapper;
    @Autowired
    private UserToUserDtoConverter userToUserDtoConverter;

    @KafkaListener(topics = "request.trip.create", groupId = "server-java")
    public void tripCreate(String payload) throws ServerException {
        try {
            TripDto tripDto = null;
            tripDto = om.readValue(payload, TripDto.class);
            Trip trip = tripService.create(mapper.map(tripDto, Trip.class));
            tripDto = mapper.map(trip, TripDto.class);
            kafkaTemplate.send("response.trip.create", om.writeValueAsString(tripDto));
        }
        catch (Exception e) {
            throw new ServerException(e);
        }
    }

    @KafkaListener(topics = "request.trip.search", groupId = "server-java")
    public void tripSearch(String payload) throws ServerException  {
        try {
            SocketDataWrapper socketDataWrapper = om.readValue(payload, SocketDataWrapper.class);
            List<Trip> trips = tripService.getByStatus(TripStatus.ACTIVE);
            List<TripDto> tripDtos = trips.stream().map(x ->
                TripDto.builder()
                        .addressFrom(x.getAddressFrom())
                        .addressTo(x.getAddressTo())
                        .date(x.getDate())
                        .latFrom(x.getLatFrom())
                        .latTo(x.getLatTo())
                        .longFrom(x.getLongFrom())
                        .longTo(x.getLongTo())
                        .users(x.getUsers().stream().map(y -> userToUserDtoConverter.map(y)).collect(Collectors.toList()))
                        .id(x.getId().toString())
                        .status(x.getStatus())
                        .build()).collect(Collectors.toList());
            String result = om.writeValueAsString(SocketDataWrapper.builder().socket(socketDataWrapper.getSocket()).payload(om.writeValueAsString(tripDtos)).build());
            kafkaTemplate.send("response.trip.search", result);
        }
        catch (Exception e) {
            throw new ServerException(e);
        }
    }

    @KafkaListener(topics = "request.trip.join", groupId = "server-java")
    public void tripJoin(String payload) throws ServerException  {
        try {
            SocketDataWrapper socketDataWrapper = om.readValue(payload, SocketDataWrapper.class);
            TripAndUserRequest tripJoinRequest = om.readValue(socketDataWrapper.getPayload(), TripAndUserRequest.class);
            Trip trip = tripService.addUserToTrip(Integer.parseInt(tripJoinRequest.getTripId()),
                    Integer.parseInt(tripJoinRequest.getUserId()));
            TripDto tripDto = mapper.map(trip, TripDto.class);
            String result = om.writeValueAsString(SocketDataWrapper.builder().payload("ok").socket(socketDataWrapper.getSocket()).build());
            kafkaTemplate.send("response.trip.join", result);
        }
        catch (Exception e) {
            throw new ServerException(e);
        }
    }

    @KafkaListener(topics = "request.trip.leave", groupId = "server-java")
    public void tripUnjoin(String payload) throws ServerException  {
        try {
            SocketDataWrapper socketDataWrapper = om.readValue(payload, SocketDataWrapper.class);
            TripAndUserRequest tripUnjoinRequest = om.readValue(socketDataWrapper.getPayload(), TripAndUserRequest.class);
            Trip trip = tripService.removeUserFromTrip(Integer.parseInt(tripUnjoinRequest.getTripId()),
                    Integer.parseInt(tripUnjoinRequest.getUserId()));
            TripDto tripDto = mapper.map(trip, TripDto.class);
            String result = om.writeValueAsString(SocketDataWrapper.builder().payload("ok").socket(socketDataWrapper.getSocket()).build());
            kafkaTemplate.send("response.trip.leave", result);
        }
        catch (Exception e) {
            throw new ServerException(e);
        }
    }
}
