package space.borisgk.taxi.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import space.borisgk.taxi.api.model.entity.User;
import space.borisgk.taxi.api.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        return userRepository.saveAndFlush(user);
    }

    public User getUser(Integer userId) {
        return userRepository.getOne(userId);
    }
    public User getUserByVkId(Integer vkId) {
        return userRepository.getUserByVkId(vkId);
    }
}
