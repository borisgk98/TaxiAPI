package space.borisgk.taxi.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import space.borisgk.taxi.api.model.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User getUserByVkId(Integer vkId);
}
