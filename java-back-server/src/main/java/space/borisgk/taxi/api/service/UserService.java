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
    private ICrudService<AuthServiceData, Integer> authServiceDataService;

    public UserService(JpaRepository<User, Integer> repository, EntityManager em, CriteriaBuilder cb, ICrudService<AuthServiceData, Integer> authServiceDataService) {
        super(repository, em, cb);
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
    public void updateFriends(Integer userId, AuthServiceData authServiceData, Set<String> newFriendsSocialIds) throws ModelNotFound {
        if (!existById(userId)){
            throw new ModelNotFound();
        }

        // Update AuthServiceData
        em.createNativeQuery("update auth_service_data set friends_hash = ?2 where id in (\n" +
                "    select d.id from auth_service_data d\n" +
                "    join taxi_user_auth_service_data on auth_service_data.id = taxi_user_auth_service_data.auth_service_data_id\n" +
                "    join taxi_user tu on taxi_user_auth_service_data.user_id = tu.id\n" +
                "    where tu.id = ?1\n" +
                ")")
                .setParameter(1, userId)
                .setParameter(2, authServiceData.getFriendsHash())
                .executeUpdate();

        // Update friends for user
        em.createNativeQuery("insert into taxi_user_friends\n" +
                "select ?1 as user_id, u.id as friends_id from taxi_user u\n" +
                "    join taxi_user_auth_service_data ud on u.id = ud.user_id\n" +
                "    join auth_service_data d on ud.auth_service_data_id = d.id\n" +
                "where d.auth_service = ?2 and d.social_id in ?3")
                .setParameter(1, userId)
                .setParameter(2, authServiceData.getAuthService().ordinal())
                .setParameter(3, newFriendsSocialIds)
                .executeUpdate();
    }
}
