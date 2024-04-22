package com.project.layer.Persistence.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.layer.Persistence.Entity.Client;

@Repository
public interface IClientRepository {
    
    @Query(
            value = "SELECT * FROM Cliente ORDER BY idcliente DESC LIMIT 1",
            nativeQuery = true
    )
    Client getLastId();

    @Query(
            value = "SELECT * FROM cliente c WHERE c.correo = ?1 AND c.passwordcliente = ?2",
            nativeQuery = true
    )
    Client getClientLogin(@Param("correo") String correo, @Param("password") String password);

    @Query(
            value = "SELECT * FROM cliente c WHERE c.correo = ?1",
            nativeQuery = true
    )
    Client verifyExistenceByMail(@Param("cuenta") String cuenta);

    @Query(
            value = "SELECT * FROM cliente WHERE idcliente = ?1",
            nativeQuery = true
    )
    Client verifyExistenceById(@Param("id") int id);
}
