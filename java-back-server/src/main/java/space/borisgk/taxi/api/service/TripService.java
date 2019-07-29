package space.borisgk.taxi.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import space.borisgk.taxi.api.model.entity.Trip;
import space.borisgk.taxi.api.repository.TripRepository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Service
public class TripService {

    @Autowired
    private TripRepository tripRepository;

    public Trip saveTrip(Trip trip) {
        return tripRepository.saveAndFlush(trip);
    }

    public List<Trip> getAll() {
        return tripRepository.findAll();
    }
}
