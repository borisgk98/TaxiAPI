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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Service
public class TripService extends AbstractCrudService<Trip> {
    private UserService userCrudService;
    // 0.5 km
    private final Double distanceDelta = 500.0;

    public TripService(JpaRepository<Trip, Integer> repository, EntityManager em, CriteriaBuilder cb, UserService userCrudService) {
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
        if (tripUsers.size() == trip.getNumberOfSeats()) {
            trip.setStatus(TripStatus.COLLECTED);
        }
        else {
            trip.setStatus(TripStatus.ACTIVE);
        }
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
        else {
            trip.setStatus(TripStatus.ACTIVE);
        }
        trip.setUsers(tripUsers);
        return update(trip);
    }

    @Transactional
    public List<Trip> search(TripSearchRequest searchRequest) {
        Long userId = Long.parseLong(searchRequest.getUserId());
        if (searchRequest.getLatTo() == null || searchRequest.getLongTo() == null) {
            Query query1 = em.createNativeQuery("" +
                    "with s as (\n" +
                    "    select t.*\n" +
                    "    from taxi_user\n" +
                    "             join trip_users tu on taxi_user.id = tu.user_id\n" +
                    "             join trip t on tu.trip_id = t.id\n" +
                    "    where taxi_user.id in (\n" +
                    "        select friend_id\n" +
                    "        from taxi_user_friends\n" +
                    "        where user_id = :id\n" +
                    "    )\n" +
                    "    union distinct\n" +
                    "    select t.*\n" +
                    "    from taxi_user\n" +
                    "             join trip_users tu on taxi_user.id = tu.user_id\n" +
                    "             join trip t on tu.trip_id = t.id\n" +
                    "    where taxi_user.id = :id\n" +
                    ")\n" +
                    "select * from s\n" +
                    "where s.status in :statuses and s.date >= now()\n" +
                    "order by distance_delta(\n" +
                    "              lat_from,\n" +
                    "              long_from,\n" +
                    "              cast(:latFrom as double precision),\n" +
                    "              cast(:longFrom as double precision)\n" +
                    "          );", Trip.class);
            query1.setParameter("statuses", List.of(TripStatus.ACTIVE.ordinal()));
            query1.setParameter("latFrom", searchRequest.getLatFrom());
            query1.setParameter("longFrom", searchRequest.getLongFrom());
            query1.setParameter("id", userId);
            List<Trip> friendsTrips = query1.getResultList();
            for (Trip trip : friendsTrips) {
                trip.setHasFriends(true);
                userCrudService.fillUsersForTrip(trip, userId);
            }

            Query query2 = em.createNativeQuery("" +
                    "with s as (\n" +
                    "    select t.*\n" +
                    "    from taxi_user\n" +
                    "             join trip_users tu on taxi_user.id = tu.user_id\n" +
                    "             join trip t on tu.trip_id = t.id\n" +
                    "    where taxi_user.id in (\n" +
                    "        select friend_id\n" +
                    "        from taxi_user_friends\n" +
                    "        where user_id = :id\n" +
                    "    )\n" +
                    "    union distinct\n" +
                    "    select t.*\n" +
                    "    from taxi_user\n" +
                    "             join trip_users tu on taxi_user.id = tu.user_id\n" +
                    "             join trip t on tu.trip_id = t.id\n" +
                    "    where taxi_user.id = :id\n" +
                    ")\n" +
                    "select * from trip\n" +
                    "where status in :statuses\n and trip.id not in (select id from s) and trip.date >= now()\n" +
                    "order by distance_delta(\n" +
                    "              lat_from,\n" +
                    "              long_from,\n" +
                    "              cast(:latFrom as double precision),\n" +
                    "              cast(:longFrom as double precision)\n" +
                    "          );", Trip.class);
            query2.setParameter("statuses", List.of(TripStatus.ACTIVE.ordinal()));
            query2.setParameter("latFrom", searchRequest.getLatFrom());
            query2.setParameter("longFrom", searchRequest.getLongFrom());
            query2.setParameter("id", userId);
            List<Trip> anotherTrips = query2.getResultList();
            anotherTrips.forEach(trip -> trip.setHasFriends(false));
            List<Trip> all = new ArrayList<>();
            all.addAll(friendsTrips);
            all.addAll(anotherTrips);
            return all;
        } else {
            Query query1 = em.createNativeQuery("" +
                    "with s as (\n" +
                    "    select t.*\n" +
                    "    from taxi_user\n" +
                    "             join trip_users tu on taxi_user.id = tu.user_id\n" +
                    "             join trip t on tu.trip_id = t.id\n" +
                    "    where taxi_user.id in (\n" +
                    "        select friend_id\n" +
                    "        from taxi_user_friends\n" +
                    "        where user_id = :id\n" +
                    "    )\n" +
                    "    union\n" +
                    "    select t.*\n" +
                    "    from taxi_user\n" +
                    "             join trip_users tu on taxi_user.id = tu.user_id\n" +
                    "             join trip t on tu.trip_id = t.id\n" +
                    "    where t.status in :statuses and taxi_user.id = :id\n" +
                    ")\n" +
                    "select * from s\n" +
                    "where distance_delta(\n" +
                    "              lat_from,\n" +
                    "              long_from,\n" +
                    "              cast(:latFrom as double precision),\n" +
                    "              cast(:longFrom as double precision)\n" +
                    "          ) < :distanceDelta\n" +
                    "  and distance_delta(\n" +
                    "        lat_to,\n" +
                    "        long_to,\n" +
                    "        cast(:latTo as double precision),\n" +
                    "        cast(:longTo as double precision)\n" +
                    "    ) < :distanceDelta\n and s.date >= now()\n" +
                    "order by compute_trip_rate(\n" +
                    "                 lat_from,\n" +
                    "                 long_from,\n" +
                    "                 lat_to,\n" +
                    "                 long_to,\n" +
                    "                 cast(:latFrom as double precision),\n" +
                    "                 cast(:longFrom as double precision),\n" +
                    "                 cast(:latTo as double precision),\n" +
                    "                 cast(:longTo as double precision),\n" +
                    "                 cast(:tripTime as timestamp),\n" +
                    "                 date\n" +
                    "             );", Trip.class);
            query1.setParameter("statuses", List.of(TripStatus.ACTIVE.ordinal()));
            query1.setParameter("latFrom", searchRequest.getLatFrom());
            query1.setParameter("latTo", searchRequest.getLatTo());
            query1.setParameter("longTo", searchRequest.getLongTo());
            query1.setParameter("longFrom", searchRequest.getLongFrom());
            query1.setParameter("tripTime", searchRequest.getDate());
            query1.setParameter("distanceDelta", distanceDelta);
            query1.setParameter("id", userId);
            List<Trip> friendsTrips = query1.getResultList();
            for (Trip trip : friendsTrips) {
                trip.setHasFriends(true);
                userCrudService.fillUsersForTrip(trip, userId);
            }

            Query query2 = em.createNativeQuery("" +
                    "with s as (\n" +
                    "    select t.*\n" +
                    "    from taxi_user\n" +
                    "             join trip_users tu on taxi_user.id = tu.user_id\n" +
                    "             join trip t on tu.trip_id = t.id\n" +
                    "    where taxi_user.id in (\n" +
                    "        select friend_id\n" +
                    "        from taxi_user_friends\n" +
                    "        where user_id = :id\n" +
                    "    )\n" +
                    "    union\n" +
                    "    select t.*\n" +
                    "    from taxi_user\n" +
                    "             join trip_users tu on taxi_user.id = tu.user_id\n" +
                    "             join trip t on tu.trip_id = t.id\n" +
                    "    where t.status in :statuses and taxi_user.id = :id\n" +
                    ")\n" +
                    "select * from trip\n" +
                    "where trip.status in (:statuses) and trip.id not in (select id from s) and distance_delta(\n" +
                    "              lat_from,\n" +
                    "              long_from,\n" +
                    "              cast(:latFrom as double precision),\n" +
                    "              cast(:longFrom as double precision)\n" +
                    "          ) < :distanceDelta\n " +
                    "and trip.date >= now()\n" +
                    "  and distance_delta(\n" +
                    "              lat_to,\n" +
                    "              long_to,\n" +
                    "              cast(:latTo as double precision),\n" +
                    "              cast(:longTo as double precision)\n" +
                    "          ) < :distanceDelta\n" +
                    "order by compute_trip_rate(\n" +
                    "                 lat_from,\n" +
                    "                 long_from,\n" +
                    "                 lat_to,\n" +
                    "                 long_to,\n" +
                    "                 cast(:latFrom as double precision),\n" +
                    "                 cast(:longFrom as double precision),\n" +
                    "                 cast(:latTo as double precision),\n" +
                    "                 cast(:longTo as double precision),\n" +
                    "                 cast(:tripTime as timestamp),\n" +
                    "                 date\n" +
                    "             );", Trip.class);
            query2.setParameter("statuses", List.of(TripStatus.ACTIVE.ordinal()));
            query2.setParameter("latFrom", searchRequest.getLatFrom());
            query2.setParameter("latTo", searchRequest.getLatTo());
            query2.setParameter("longTo", searchRequest.getLongTo());
            query2.setParameter("longFrom", searchRequest.getLongFrom());
            query2.setParameter("distanceDelta", distanceDelta);
            query2.setParameter("id", userId);
            query2.setParameter("tripTime", searchRequest.getDate());
            List<Trip> anotherTrips = query2.getResultList();
            anotherTrips.forEach(trip -> trip.setHasFriends(false));
            List<Trip> all = new ArrayList<>();
            all.addAll(friendsTrips);
            all.addAll(anotherTrips);
            return all;
        }
    }

    public List<Trip> findAll() {
        return repository.findAll();
    }
}
