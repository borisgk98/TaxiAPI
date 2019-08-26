package space.borisgk.taxi.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.borisgk.taxi.api.model.AuthService;
import space.borisgk.taxi.api.model.entity.AuthServiceData;
import space.borisgk.taxi.api.model.entity.User;
import space.borisgk.taxi.api.repository.UserRepository;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    private UserRepository userRepository;
    private EntityManager em;
    private CriteriaBuilder cb;

    @Autowired
    public UserService(UserRepository userRepository, EntityManager em, CriteriaBuilder cb) {
        this.userRepository = userRepository;
        this.em = em;
        this.cb = cb;
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

    // TODO обработка отсутвующего id у authServiceData
    @Transactional
    public void updateFriends(Long userId, AuthServiceData authServiceData, Set<String> newFriendsSocialIds) {

        // Update AuthServiceData
        CriteriaUpdate<AuthServiceData> updateAuthServiceData = cb.createCriteriaUpdate(AuthServiceData.class);
        Root<AuthServiceData> authServiceDataRoot = updateAuthServiceData.from(AuthServiceData.class);
        updateAuthServiceData.set("friendsHash", authServiceData.getFriendsHash());
        updateAuthServiceData.where(cb.equal(authServiceDataRoot.get("id"), authServiceData.getId()));
        em.createQuery(updateAuthServiceData).executeUpdate();

        // Get all users by socialIds
        CriteriaQuery<User> userSelect = cb.createQuery(User.class);
        Root<User> userRoot = userSelect.from(User.class);
        CriteriaQuery<AuthServiceData> selectAuthServiceData = cb.createQuery(AuthServiceData.class);
        Root<AuthServiceData> selectServiceDataRoot = selectAuthServiceData.from(AuthServiceData.class);
        selectAuthServiceData
                .select(selectServiceDataRoot)
                .where(cb.and(
                        authServiceDataRoot.get("socialId").in(newFriendsSocialIds),
                        cb.equal(authServiceDataRoot.get("authService"), authServiceData.getAuthService())
                        ));
        Set<AuthServiceData> authServiceDataSet = em.createQuery(selectAuthServiceData).getResultList().stream().collect(Collectors.toSet());
        userSelect
                .select(userRoot)
                .where(cb.equal(userRoot.get("authServicesData"), authServiceDataSet));
        Join<User, AuthServiceData> join = userRoot.join("authServicesData");
        join.
        Set<User> newFriends = new HashSet<>(em.createQuery(userSelect).getResultList());

        //Update user
        CriteriaUpdate<User> userCriteriaUpdate = cb.createCriteriaUpdate(User.class);
        Root<User> userRoot1 = userCriteriaUpdate.from(User.class);
        userCriteriaUpdate.set("friends", newFriends);
        userCriteriaUpdate.where(cb.equal(userRoot1.get("id"), userId));
        em.createQuery(userCriteriaUpdate).executeUpdate();
    }
}
