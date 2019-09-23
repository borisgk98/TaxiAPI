package space.borisgk.taxi.api.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.borisgk.taxi.api.exception.ModelNotFound;
import space.borisgk.taxi.api.model.TripStatus;
import space.borisgk.taxi.api.model.entity.Trip;
import space.borisgk.taxi.api.model.entity.User;
import space.borisgk.taxi.api.repository.TripRepository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Set;

@Service
public class TripService extends AbstractCrudService<Trip> {
    private ICrudService<User, Integer> userCrudService;

    public TripService(JpaRepository<Trip, Integer> repository, EntityManager em, CriteriaBuilder cb, ICrudService<User, Integer> userCrudService) {
        super(repository, em, cb);
        this.userCrudService = userCrudService;
    }

    public List<Trip> getByStatus(TripStatus status) {
        return ((TripRepository) repository).findByStatus(status);
    }

    // TODO проверять ограничения на количество пользователей в поездке, проверка на отсутствие по id
    @Transactional
    public Trip addUserToTrip(Integer tripId, Integer userId) throws ModelNotFound {
        Trip trip = read(tripId);
        User user = userCrudService.read(userId);
        Set<User> tripUsers = trip.getUsers();
        tripUsers.add(user);
        trip.setUsers(tripUsers);
        return update(trip);
    }

    // TODO проверять ограничения на количество пользователей в поездке, проверка на отсутствие по id
    @Transactional
    public Trip removeUserFromTrip(Integer tripId, Integer userId) throws ModelNotFound {
        Trip trip = read(tripId);
        User user = userCrudService.read(userId);
        Set<User> tripUsers = trip.getUsers();
        tripUsers.remove(user);
        if (tripUsers.size() == 0) {
            trip.setStatus(TripStatus.DELETED);
        }
        trip.setUsers(tripUsers);
        return update(trip);
    }

//    public List<Trip> search(Long userId, Double latFrom, Double latTo, Double longFrom, Double longTo) {
//        em.createNativeQuery("").;
//    }
}
