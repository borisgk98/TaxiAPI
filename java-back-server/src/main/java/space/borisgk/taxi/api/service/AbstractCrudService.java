package space.borisgk.taxi.api.service;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import space.borisgk.taxi.api.exception.ModelNotFound;
import space.borisgk.taxi.api.model.IEntity;
import space.borisgk.taxi.api.model.TripStatus;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Optional;

public abstract class AbstractCrudService<T extends IEntity<Integer>> implements ICrudService<T, Integer> {
    protected JpaRepository<T, Integer> repository;
    protected EntityManager em;
    protected CriteriaBuilder cb;

    public AbstractCrudService(JpaRepository<T, Integer> repository, EntityManager em, CriteriaBuilder cb) {
        this.repository = repository;
        this.em = em;
        this.cb = cb;
    }

    @Override
    public T create(T m) {
        return repository.save(m);
    }

    @Override
    public T read(Integer id) throws ModelNotFound {
        Optional<T> m = repository.findById(id);
        if (!m.isPresent()) {
            throw new ModelNotFound();
        }
        return m.get();
    }

    @Override
    public T update(T m) throws ModelNotFound {
        if (!existById(m.getId())) {
            throw new ModelNotFound();
        }
        return repository.save(m);
    }

    @Override
    public void delete(Integer id) throws ModelNotFound {
        if (!existById(id)) {
            throw new ModelNotFound();
        }
        repository.deleteById(id);
    }

    @Override
    public boolean exist(T m) {
        return repository.exists(Example.of(m));
    }

    @Override
    public boolean existById(Integer id) {
        return repository.existsById(id);
    }

    @Override
    public List<T> getAll() {
        return repository.findAll();
    }

    @Override
    public List<T> getRange(Integer offset, Integer limit) {
        Pageable request = PageRequest.of(offset, limit);
        return repository.findAll(request).getContent();
    }
}