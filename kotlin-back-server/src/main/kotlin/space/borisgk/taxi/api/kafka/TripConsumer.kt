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
import space.borisgk.taxi.api.model.dto.TripDto
import space.borisgk.taxi.api.model.dto.UserDto
import space.borisgk.taxi.api.model.entity.Trip
import space.borisgk.taxi.api.service.TripService
import space.borisgk.taxi.api.tool.Converter

@Component
class TripConsumer {

    private val logger = LoggerFactory.getLogger(TripConsumer::class.java)

    @Autowired
    private val om: ObjectMapper? = null
    @Autowired
    private val tripService: TripService? = null
    @Autowired
    private val kafkaTemplate: KafkaTemplate<String, String>? = null
    @Autowired
    private val mapper: Mapper? = null

    @KafkaListener(topics = ["request.trip.create"])
    @Throws(ServerException::class)
    fun tripCreate(payload: String) {
        try {
            var tripDto: TripDto? = null
            tripDto = om!!.readValue<TripDto>(payload, TripDto::class.java!!)
            val trip = tripService!!.saveTrip(mapper!!.map<Trip>(tripDto, Trip::class.java))
            tripDto = mapper.map<TripDto>(trip, TripDto::class.java)
            kafkaTemplate!!.send("response.trip.create", om.writeValueAsString(tripDto))
        } catch (e: Exception) {
            throw ServerException(e)
        }

    }

    @KafkaListener(topics = ["request.trip.search"])
    @Throws(ServerException::class)
    fun tripSearch(payload: String) {
        try {
            val trips = tripService!!.all
            val tripDtos = mapper!!.map(trips, List::class.java) as List<TripDto>
            kafkaTemplate!!.send("response.trip.search", om!!.writeValueAsString(tripDtos))
        } catch (e: Exception) {
            throw ServerException(e)
        }

    }
}
