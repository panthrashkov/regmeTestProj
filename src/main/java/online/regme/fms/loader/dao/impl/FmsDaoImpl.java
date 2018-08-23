package online.regme.fms.loader.dao.impl;

import online.regme.fms.loader.dao.FmsDao;
import online.regme.fms.loader.model.Fms;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Optional;


@Repository
public class FmsDaoImpl implements FmsDao {

    private final EntityManager entityManager;

    public FmsDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Fms> getByCode(String code) {
        TypedQuery<Fms> query = entityManager.createNamedQuery("findFmsByCode", Fms.class);
        query.setParameter("code", code);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException exception) {
            return Optional.empty();
        }
    }

    @Override
    public void persist(Fms fms) {
        entityManager.persist(fms);
    }
}
