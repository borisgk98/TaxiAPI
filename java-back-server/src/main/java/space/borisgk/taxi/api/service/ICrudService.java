package space.borisgk.taxi.api.service;

import space.borisgk.taxi.api.exception.ModelNotFound;
import space.borisgk.taxi.api.model.IEntity;

import java.util.List;

public interface ICrudService<T extends IEntity<F>, F> {
    T create(T m);
    T read(F id) throws ModelNotFound;
    T update(T m) throws ModelNotFound;
    void delete(F id) throws ModelNotFound;
    boolean existById(F id);
    boolean exist(T m);
    List<T> getAll();
    List<T> getRange(F offset, F limit);
}