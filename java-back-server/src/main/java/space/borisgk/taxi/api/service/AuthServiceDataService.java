package space.borisgk.taxi.api.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import space.borisgk.taxi.api.model.entity.AuthServiceData;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;

@Service
public class AuthServiceDataService extends AbstractCrudService<AuthServiceData> implements ICrudService<AuthServiceData, Integer> {
    public AuthServiceDataService(JpaRepository<AuthServiceData, Integer> repository, EntityManager em, CriteriaBuilder cb) {
        super(repository, em, cb);
    }
}
