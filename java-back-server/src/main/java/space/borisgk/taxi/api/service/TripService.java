package space.borisgk.taxi.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.borisgk.taxi.api.model.entity.Trip;
import space.borisgk.taxi.api.model.entity.User;
import space.borisgk.taxi.api.repository.TripRepository;
import space.borisgk.taxi.api.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Service
public class TripService {

    @Autowired
    private TripRepository tripRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EntityManager em;

    public Trip saveTrip(Trip trip) {

        return tripRepository.saveAndFlush(trip);
    }

    public List<Trip> getAll() {
        return tripRepository.findAll();
    }

    // TODO проверять ограничения на количество пользователей в поездке, проверка на отсутствие по id
    @Transactional
    public Trip addUserToTrip(Integer tripId, Integer userId) {
        Trip trip = tripRepository.getOne(tripId);
        User user = userRepository.getOne(userId);
        List<User> tripUsers = trip.getUsers();
        tripUsers.add(user);
        trip.setUsers(tripUsers);
        return tripRepository.saveAndFlush(trip);
    }
}
