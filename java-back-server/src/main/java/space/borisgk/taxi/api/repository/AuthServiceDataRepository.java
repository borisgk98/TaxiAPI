package space.borisgk.taxi.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import space.borisgk.taxi.api.model.entity.AuthServiceData;
import space.borisgk.taxi.api.model.entity.User;

public interface AuthServiceDataRepository extends JpaRepository<AuthServiceData, Integer> {
//    @Query("select User from User u join AuthServiceData d where (d.authService = 1 and d.authServiceUserId = 153171206)")
//    public User t();
}
