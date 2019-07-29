//package space.borisgk.itis.sem4.hw9.daos;
//
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//@Component
//public class EntityCBDAO extends CriteriaBuilderDAO<Entity> {
//    @Override
//    public Entity get(Integer id) {
//        Entity e = em.find(Entity.class, id);
//        return e;
//    }
//
//    @Override
//    public Integer create(Entity o) {
//        em.getTransaction().begin();
//        em.persist(o);
//        em.getTransaction().commit();
//        return em.find(Entity.class, o.getId()).getId();
//    }
//
//    @Override
//    public Integer update(Entity o) {
//        em.getTransaction().begin();
////        var q = em.createQuery("update Entity e set e.firstName = :firstName, e.lastName = :lastName where e.id = :id");
//        var criteriaUpdate = cb.createCriteriaUpdate(Entity.class);
//        var root = criteriaUpdate.from(Entity.class);
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
//        var criteriaDelete = cb.createCriteriaDelete(Entity.class);
//        var root = criteriaDelete.from(Entity.class);
//        criteriaDelete.where(cb.equal(root.get("id"), id));
//        var q = em.createQuery(criteriaDelete);
//        q.executeUpdate();
//        q.executeUpdate();
//        em.getTransaction().commit();
//    }
//
//    @Override
//    public List<Entity> findAll() {
//        em.getTransaction().begin();
//        var criteria = cb.createQuery(Entity.class);
//        var root = criteria.from(Entity.class);
//        criteria.select(root);
//        var q = em.createQuery(criteria);
//        var l = (List<Entity>) q.getResultList();
//        return l;
//    }
//
//    @Override
//    public List<Entity> findAll(Integer start, Integer limit) {
//        em.getTransaction().begin();
//        var criteria = cb.createQuery(Entity.class);
//        var root = criteria.from(Entity.class);
//        criteria.select(root);
//        var q = em.createQuery(criteria);
//        q.setMaxResults(limit);
//        q.setFirstResult(start);
//        var l = (List<Entity>) q.getResultList();
//        return l;
//    }
//}
