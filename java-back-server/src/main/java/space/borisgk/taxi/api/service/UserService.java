package space.borisgk.taxi.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import space.borisgk.taxi.api.model.entity.AuthServiceData;
import space.borisgk.taxi.api.model.entity.User;
import space.borisgk.taxi.api.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        return userRepository.saveAndFlush(user);
    }

    public Optional<User> getUser(Integer userId) {
        return Optional.ofNullable(userRepository.getOne(userId));
    }
    public Optional<User> getUserByAuthServiceData(AuthServiceData data) {
        return Optional.ofNullable(userRepository.getUserByAuthServiceData(data.getAuthServiceUserId(), data.getId()));
    }
}
