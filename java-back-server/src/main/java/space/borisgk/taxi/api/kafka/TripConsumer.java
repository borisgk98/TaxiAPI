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
import space.borisgk.taxi.api.model.dto.TripDto;
import space.borisgk.taxi.api.model.dto.UserDto;
import space.borisgk.taxi.api.model.entity.Trip;
import space.borisgk.taxi.api.service.TripService;
import space.borisgk.taxi.api.tool.Converter;

import java.util.List;

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

    @KafkaListener(topics = "request.trip.create", groupId = "server-java")
    public void tripCreate(String payload) throws ServerException {
        try { logger.info("Receive payload:");
            logger.info(payload);
            TripDto tripDto = null;
            tripDto = om.readValue(payload, TripDto.class);
            Trip trip = tripService.saveTrip(mapper.map(tripDto, Trip.class));
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
            logger.info("Receive payload:");
            logger.info(payload);
            List<Trip> trips = tripService.getAll();
            List<TripDto> tripDtos = mapper.map(trips, List.class);
            kafkaTemplate.send("response.trip.search", om.writeValueAsString(tripDtos));
        }
        catch (Exception e) {
            throw new ServerException(e);
        }
    }
}
