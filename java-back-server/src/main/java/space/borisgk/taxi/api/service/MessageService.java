package space.borisgk.taxi.api.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import space.borisgk.taxi.api.model.entity.Message;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;

@Service
public class MessageService extends AbstractCrudService<Message> {
    public MessageService(JpaRepository<Message, Integer> repository, EntityManager em, CriteriaBuilder cb) {
        super(repository, em, cb);
    }
}
