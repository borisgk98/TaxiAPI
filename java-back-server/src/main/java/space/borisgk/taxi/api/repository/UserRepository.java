package space.borisgk.taxi.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import space.borisgk.taxi.api.model.entity.AuthServiceData;
import space.borisgk.taxi.api.model.entity.User;

import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
//    @Query("")
//    User getUserByAuthServiceData(Set<AuthServiceData> authServicesData);
}
