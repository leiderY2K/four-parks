package com.project.layer.Persistence.Repository;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Optional;

import com.project.layer.Persistence.Entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.layer.Controllers.Requests.DateHourCountRequest;
import com.project.layer.Persistence.Entity.Reservation;

@Repository
public interface IReservationRepository extends JpaRepository<Reservation, Integer> {

        @Query(value = "SELECT * FROM RESERVATION r " +
                        "WHERE " +
                        "r.STARTTIMERES = :hour " +
                        "AND r.DATERES = :actualDate " +
                        "AND r.FK_IDRESSTATUS = :pendingStatus", nativeQuery = true)
        List<Reservation> findByStartTime(
                        @Param("hour") Time hour,
                        @Param("actualDate") Date actualDate,
                        @Param("pendingStatus") String pendingStatus);

        @Query(value = "SELECT * FROM RESERVATION r " +
                        "WHERE " +
                        "r.ENDTIMERES = :hour " +
                        "AND r.DATERES = :actualDate " +
                        "AND r.FK_IDRESSTATUS = :pendingStatus", nativeQuery = true)
        List<Reservation> findByEndTime(
                        @Param("hour") Time hour,
                        @Param("actualDate") Date actualDate,
                        @Param("pendingStatus") String pendingStatus);

        @Query(value = "SELECT * FROM RESERVATION r, USER c " +
                        "WHERE " +
                        "r.FK_CLIENT_IDUSER = c.IDUSER " +
                        "AND r.FK_CLIENT_IDDOCTYPE = c.FK_IDDOCTYPE " +
                        "AND c.IDUSER = :clientId " +
                        "AND c.FK_IDDOCTYPE = :docType " +
                        "AND (:status IS NULL OR r.FK_IDRESSTATUS = :status)", nativeQuery = true)
        List<Reservation> findAllByClientId(
                        @Param("clientId") String clientId,
                        @Param("docType") String doctype,
                        @Param("status") String status);

        @Query(value = "SELECT r.FK_IDPARKINGSPACE FROM RESERVATION r " +
                        "WHERE " +
                        "r.FK_IDCITY = :cityId " +
                        "AND r.FK_IDPARKING = :parkingId " +
                        "AND r.FK_IDVEHICLETYPE = :vehicleType " +
                        "AND r.DATERES = :dateRes " +
                        "AND (r.STARTTIMERES <= :endTimeRes AND r.ENDTIMERES >= :startTimeRes)", nativeQuery = true)
        List<Integer> findBusyParkingSpaces(
                        @Param("cityId") String cityId,
                        @Param("parkingId") int parkingId,
                        @Param("vehicleType") String vehicleType,
                        @Param("dateRes") java.util.Date date,
                        @Param("startTimeRes") Time startTimeRes,
                        @Param("endTimeRes") Time endTimeRes);

        @Query(value = "SELECT COUNT(DISTINCT r.FK_IDPARKINGSPACE) " +
                        "FROM RESERVATION r " +
                        "LEFT JOIN PARKINGSPACE ps ON r.FK_IDPARKINGSPACE = ps.IDPARKINGSPACE " +
                        "WHERE r.FK_IDCITY = :cityId " +
                        "AND r.FK_IDPARKING = :parkingId " +
                        "AND (:vehicleType IS NULL OR r.FK_IDVEHICLETYPE = :vehicleType) " +
                        "AND (:dateRes IS NULL OR r.DATERES = :dateRes) " +
                        "AND (r.STARTTIMERES <= :endTimeRes AND r.ENDTIMERES >= :startTimeRes)", nativeQuery = true)
        Integer findCountOfBusyParkingSpaces(
                        @Param("cityId") String cityId,
                        @Param("parkingId") int parkingId,
                        @Param("vehicleType") String vehicleType,
                        @Param("dateRes") Date dateRes,
                        @Param("startTimeRes") Time startTimeRes,
                        @Param("endTimeRes") Time endTimeRes);

        @Query(value = "SELECT COUNT(*) " +
                        "FROM RESERVATION r " +
                        "WHERE r.DATERES = :valueOf AND " +
                        "r.STARTTIMERES <= :valueOf2 AND r.ENDTIMERES > :valueOf2 AND "+
                        "r.FK_IDPARKING = :idParking", nativeQuery = true)
        Integer getDateHourCount(Date valueOf, Time valueOf2, int idParking);


    @Query(
            value ="SELECT * FROM CARD u WHERE u.FK_CLIENT_IDUSER LIKE %:userId%",
            nativeQuery = true
    )
    List<Card> findByUserId();
}