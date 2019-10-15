package space.borisgk.taxi.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import space.borisgk.taxi.api.model.entity.UserReport;

@Repository
public interface UserReportRepository extends JpaRepository<UserReport, Integer> {
}
