package space.borisgk.taxi.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import space.borisgk.taxi.api.model.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("select User from User u join u.authServiceDatas asd" +
            " where asd.id = :authServiceDataId and asd.authServiceUserId = :authServiceUserId")
    User getUserByAuthServiceData(String authServiceUserId, Integer authServiceDataId);
}
