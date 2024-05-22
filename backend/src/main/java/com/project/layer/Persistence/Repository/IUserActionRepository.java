package com.project.layer.Persistence.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.layer.Persistence.Entity.UserAction;

public interface IUserActionRepository extends JpaRepository<UserAction, Integer> {

    //Trae el listado de las acciones segun un usuario
    @Query(
        value = "SELECT * FROM CUSTOMERACTION "+
        "WHERE FK_IDUSER = :idUser", nativeQuery=true)
    List<UserAction> getActionsByUser(
        @Param("idUser") String idUser
    );

    //Trae el listado de todas las acciones
    @Query(
        value = "SELECT * FROM CUSTOMERACTION", 
        nativeQuery=true)
    List<UserAction> getAllActions();

    //Trae el listado de todas las acciones por fecha 
    




}
