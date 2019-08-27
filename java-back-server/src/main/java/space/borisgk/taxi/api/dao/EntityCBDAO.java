//package space.borisgk.itis.sem4.hw9.daos;
//
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//@Component
//public class EntityCBDAO extends CriteriaBuilderDAO<IEntity> {
//    @Override
//    public IEntity get(Integer id) {
//        IEntity e = em.find(IEntity.class, id);
//        return e;
//    }
//
//    @Override
//    public Integer create(IEntity o) {
//        em.getTransaction().begin();
//        em.persist(o);
//        em.getTransaction().commit();
//        return em.find(IEntity.class, o.getId()).getId();
//    }
//
//    @Override
//    public Integer update(IEntity o) {
//        em.getTransaction().begin();
////        var q = em.createQuery("update IEntity e set e.firstName = :firstName, e.lastName = :lastName where e.id = :id");
//        var criteriaUpdate = cb.createCriteriaUpdate(IEntity.class);
//        var root = criteriaUpdate.from(IEntity.class);
//        criteriaUpdate
//                .set("firstName", o.getFirstName())
//                .set("lastName", o.getLastName());
//        criteriaUpdate.where(cb.equal(root.get("id"), o.getId()));
//        var q = em.createQuery(criteriaUpdate);
//        q.executeUpdate();
//        em.getTransaction().commit();
//        return o.getId();
//    }
//
//    @Override
//    public void delete(Integer id) {
//        em.getTransaction().begin();
//        var criteriaDelete = cb.createCriteriaDelete(IEntity.class);
//        var root = criteriaDelete.from(IEntity.class);
//        criteriaDelete.where(cb.equal(root.get("id"), id));
//        var q = em.createQuery(criteriaDelete);
//        q.executeUpdate();
//        q.executeUpdate();
//        em.getTransaction().commit();
//    }
//
//    @Override
//    public List<IEntity> findAll() {
//        em.getTransaction().begin();
//        var criteria = cb.createQuery(IEntity.class);
//        var root = criteria.from(IEntity.class);
//        criteria.select(root);
//        var q = em.createQuery(criteria);
//        var l = (List<IEntity>) q.getResultList();
//        return l;
//    }
//
//    @Override
//    public List<IEntity> findAll(Integer start, Integer limit) {
//        em.getTransaction().begin();
//        var criteria = cb.createQuery(IEntity.class);
//        var root = criteria.from(IEntity.class);
//        criteria.select(root);
//        var q = em.createQuery(criteria);
//        q.setMaxResults(limit);
//        q.setFirstResult(start);
//        var l = (List<IEntity>) q.getResultList();
//        return l;
//    }
//}
