package space.borisgk.taxi.api.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import space.borisgk.taxi.api.exception.ServerException;
import space.borisgk.taxi.api.model.TripStatus;
import space.borisgk.taxi.api.model.dto.SocketDataWrapper;
import space.borisgk.taxi.api.model.dto.TripDto;
import space.borisgk.taxi.api.model.dto.TripAndUserRequest;
import space.borisgk.taxi.api.model.entity.Trip;
import space.borisgk.taxi.api.model.mapping.StaxMapper;
import space.borisgk.taxi.api.service.TripService;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TripConsumer {

    private Logger logger = LoggerFactory.getLogger(TripConsumer.class);

    private final StaxMapper mapper;
    private final ObjectMapper om;
    private final TripService tripService;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = "request.trip.create", groupId = "server-java")
    public void tripCreate(String payload) throws ServerException {
        try {
            TripDto tripDto = null;
            tripDto = om.readValue(payload, TripDto.class);
            Trip trip = tripService.create(mapper.dto2e(tripDto));
            tripDto = mapper.e2dto(trip);
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
            List<TripDto> tripDtos = trips.stream().map(mapper::e2dto).collect(Collectors.toList());
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
            TripDto tripDto = mapper.e2dto(trip);
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
            TripDto tripDto = mapper.e2dto(trip);
            String result = om.writeValueAsString(SocketDataWrapper.builder().payload("ok").socket(socketDataWrapper.getSocket()).build());
            kafkaTemplate.send("response.trip.leave", result);
        }
        catch (Exception e) {
            throw new ServerException(e);
        }
    }
}
