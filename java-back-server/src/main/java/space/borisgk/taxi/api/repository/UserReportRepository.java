package space.borisgk.taxi.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import space.borisgk.taxi.api.model.entity.UserReport;

@Repository
public interface UserReportRepository extends JpaRepository<UserReport, Integer> {
    @Query(value = "select count(id) from user_report where user_id = :userId", nativeQuery = true)
    Long countByUser(@Param("userId") Integer userId);
}
