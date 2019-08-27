package space.borisgk.taxi.api.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.borisgk.taxi.api.exception.ModelNotFound;
import space.borisgk.taxi.api.model.entity.AuthServiceData;
import space.borisgk.taxi.api.model.entity.User;
import space.borisgk.taxi.api.repository.UserRepository;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService extends AbstractCrudService<User> {
    private ICrudService<User, Integer> userService;
    private ICrudService<AuthServiceData, Integer> authServiceDataService;

    public UserService(JpaRepository<User, Integer> repository, EntityManager em, CriteriaBuilder cb, ICrudService<User, Integer> userService, ICrudService<AuthServiceData, Integer> authServiceDataService) {
        super(repository, em, cb);
        this.userService = userService;
        this.authServiceDataService = authServiceDataService;
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
    public void updateFriends(Long userId, AuthServiceData authServiceData, Set<String> newFriendsSocialIds) throws ModelNotFound {
        authServiceDataService.update(authServiceData);

    }
}
