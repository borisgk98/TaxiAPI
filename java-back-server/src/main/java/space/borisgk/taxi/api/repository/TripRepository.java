package space.borisgk.taxi.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import space.borisgk.taxi.api.model.TripStatus;
import space.borisgk.taxi.api.model.entity.Trip;

import java.util.Date;
import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Integer> {
//    @Query("select t from Trip t where t.getLatTo < toLat")
//    List<Trip> search(Date date, Double latFrom, Double latTo, Double longTo, Double toLong, Double deltaTime);

    List<Trip> findByStatus(TripStatus status);
}
