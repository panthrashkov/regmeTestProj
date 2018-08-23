package online.regme.fms.loader.dao;

import online.regme.fms.loader.model.Fms;

import java.util.Optional;

public interface FmsDao {
    Optional<Fms> getByCode(String code);
    void persist(Fms fms);
}
