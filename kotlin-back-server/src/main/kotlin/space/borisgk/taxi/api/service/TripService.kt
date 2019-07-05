package space.borisgk.taxi.api.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import space.borisgk.taxi.api.model.entity.Trip
import space.borisgk.taxi.api.repository.TripRepository

import javax.persistence.EntityManager
import javax.persistence.criteria.CriteriaBuilder

@Service
class TripService {

    @Autowired
    private val tripRepository: TripRepository? = null

    val all: List<Trip>
        get() = tripRepository!!.findAll()

    fun saveTrip(trip: Trip): Trip {
        return tripRepository!!.saveAndFlush(trip)
    }
}
