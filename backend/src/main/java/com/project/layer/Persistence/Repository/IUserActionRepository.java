package com.project.layer.Persistence.Repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.layer.Persistence.Entity.UserAction;

@Repository
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

    @Query(
        value = "SELECT * FROM CUSTOMERACTION "+
        "WHERE DATEACTION = :dateAction", 
        nativeQuery=true)
        List<UserAction> getActionsByDate(
            @Param("dateAction") Date dateAction
        );

    //Trae el listado de todas las acciones por ip

    @Query(
        value = "SELECT * FROM CUSTOMERACTION "+
        "WHERE IPUSER = :ipUser", 
        nativeQuery=true)
        List<UserAction> getActionsByDate(
            @Param("ipUser") String ipUser
        );

    //Realiza el filtro para traer las acciones segun IP, IDUSER y DATEUSER
         @Query(value = "SELECT * FROM CUSTOMERACTION "+
         "WHERE (:idUser IS NULL OR FK_IDUSER = :idUser) "+ 
         "AND (:dateAction IS NULL OR DATEACTION = :dateAction) "+
         "AND (:ipUser IS NULL OR IPUSER = :ipUser)", nativeQuery = true)
    List<UserAction> getUserActionsByArgs(
            @Param("idUser") String idUser,
            @Param("dateAction") Date dateAction);
}
