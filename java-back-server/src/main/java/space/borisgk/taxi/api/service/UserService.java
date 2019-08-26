package space.borisgk.taxi.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.borisgk.taxi.api.model.entity.AuthServiceData;
import space.borisgk.taxi.api.model.entity.User;
import space.borisgk.taxi.api.repository.UserRepository;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    private UserRepository userRepository;
    private EntityManager em;

    @Autowired
    public UserService(UserRepository userRepository, EntityManagerFactory emf) {
        this.userRepository = userRepository;
        this.em = emf.createEntityManager();
    }

    public User saveUser(User user) {
        return userRepository.saveAndFlush(user);
    }

    public Optional<User> getUser(Integer userId) {
        return Optional.ofNullable(userRepository.getOne(userId));
    }

    public Optional<User> getUserByAuthServiceData(Set<AuthServiceData> authServiceData) {
        StringBuilder queryString = new StringBuilder("select * from taxi_user u " +
                "join taxi_user_auth_service_data ud on u.id = ud.user_id " +
                "join auth_service_data d on ud.auth_services_data_id = d.id where ");
        for (AuthServiceData data : authServiceData) {
            queryString.append(String.format("(d.auth_service = %s and d.social_id = '%s') or ",
                    data.getAuthService().ordinal(), data.getSocialId()));
        }
        queryString.delete(queryString.length() - 3, queryString.length());
        try {
            return Optional.of((User) em.createNativeQuery(queryString.toString(), User.class).getSingleResult());
        }
        catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
