package space.borisgk.taxi.api.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.borisgk.taxi.api.exception.ModelNotFound;
import space.borisgk.taxi.api.model.TripStatus;
import space.borisgk.taxi.api.model.dto.request.TripSearchRequest;
import space.borisgk.taxi.api.model.entity.Trip;
import space.borisgk.taxi.api.model.entity.User;
import space.borisgk.taxi.api.repository.TripRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
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

    public List<Trip> search(TripSearchRequest searchRequest) {
        Query query1 = em.createNativeQuery("" +
                "select t.* from taxi_user\n" +
                "                    join trip_users tu on taxi_user.id = tu.user_id\n" +
                "                    join trip t on tu.trip_id = t.id\n" +
                "where taxi_user.id in (\n" +
                "    select friend_id from taxi_user_friends where user_id = :id\n" +
                "    )\n" +
                "union\n" +
                "select t.* from taxi_user\n" +
                "                    join trip_users tu on taxi_user.id = tu.user_id\n" +
                "                    join trip t on tu.trip_id = t.id\n" +
                "where taxi_user.id = :id\n" +
                "order by compute_trip_rate(\n" +
                "                 lat_from,\n" +
                "                 long_from,\n" +
                "                 lat_to,\n" +
                "                 long_to,\n" +
                "                 cast(:latFrom as double precision),\n" +
                "                 cast(:longFrom as double precision),\n" +
                "                 cast(:latTo as double precision),\n" +
                "                 cast(:longTo as double precision),\n" +
                "                 cast(:time as timestamp),\n" +
                "                 date\n" +
                "             );");
        query1.setParameter("latFrom", searchRequest.getLatFrom());
        query1.setParameter("latTo", searchRequest.getLatTo());
        query1.setParameter("longTo", searchRequest.getLongTo());
        query1.setParameter("longFrom", searchRequest.getLongFrom());
        query1.setParameter("time", searchRequest.getDate());
        query1.setParameter("id", searchRequest.getUserId());
        List<Trip> friendsTrips = query1.getResultList();
        friendsTrips.forEach(trip -> trip.setHasFriends(true));
        Query query2 = em.createNativeQuery("" +
                "select * from trip " +
                "order by compute_trip_rate(\n" +
                "                 lat_from,\n" +
                "                 long_from,\n" +
                "                 lat_to,\n" +
                "                 long_to,\n" +
                "                 cast(:latFrom as double precision),\n" +
                "                 cast(:longFrom as double precision),\n" +
                "                 cast(:latTo as double precision),\n" +
                "                 cast(:longTo as double precision),\n" +
                "                 cast(:time as timestamp),\n" +
                "                 date\n" +
                "             );");
        query2.setParameter("latFrom", searchRequest.getLatFrom());
        query2.setParameter("latTo", searchRequest.getLatTo());
        query2.setParameter("longTo", searchRequest.getLongTo());
        query2.setParameter("longFrom", searchRequest.getLongFrom());
        query2.setParameter("time", searchRequest.getDate());
        List<Trip> anotherTrips = query2.getResultList();
        anotherTrips.forEach(trip -> trip.setHasFriends(false));
        List<Trip> all = new ArrayList<>();
        all.addAll(friendsTrips);
        all.addAll(anotherTrips);
        return all;
    }

    private boolean fillUsersForTrip(Trip trip, Long userId) {
        trip.getUsers()
    }
}
