//package space.borisgk.itis.sem4.hw9.daos;
//
//import org.springframework.beans.factory.annotation.Autowired;
//
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.criteria.CriteriaBuilder;
//
//public abstract class CriteriaBuilderDAO<T> extends JpaDAO<T> {
//    CriteriaBuilder cb;
//
//    @Override
//    @Autowired
//    protected void setEm(EntityManagerFactory emf) {
//        super.setEm(emf);
//        cb = em.getCriteriaBuilder();
//    }
//}
