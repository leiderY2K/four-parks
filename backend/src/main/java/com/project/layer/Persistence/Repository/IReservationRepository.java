package com.project.layer.Persistence.Repository;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.project.layer.Persistence.Entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.layer.Persistence.Entity.Reservation;

@Repository
public interface IReservationRepository extends JpaRepository<Reservation, Integer> {

        @Query(value = "SELECT * FROM RESERVATION r " +
                        "WHERE " +
                        "r.STARTTIMERES = :hour " +
                        "AND r.STARTDATERES = :actualDate " +
                        "AND r.FK_IDRESSTATUS = :pendingStatus", nativeQuery = true)
        List<Reservation> findByStartTime(
                        @Param("hour") LocalTime hour,
                        @Param("actualDate") Date actualDate,
                        @Param("pendingStatus") String pendingStatus);

        @Query(value = "SELECT * FROM RESERVATION r " +
                        "WHERE " +
                        "r.ENDTIMERES = :hour " +
                        "AND r.STARTDATERES = :actualDate " +
                        "AND r.FK_IDRESSTATUS = :pendingStatus", nativeQuery = true)
        List<Reservation> findByEndTime(
                        @Param("hour") LocalTime hour,
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

        @Query(value = "SELECT DISTINCT r.* FROM RESERVATION r, PARKINGSPACE ps " +
                        "WHERE r.FK_IDCITY = :cityId " +
                        "AND r.FK_IDPARKING = :parkingId " +
                        "AND r.FK_IDPARKINGSPACE = ps.IDPARKINGSPACE " +
                        "AND ps.FK_IDVEHICLETYPE = :vehicleType " +
                        "AND r.STARTDATERES = :startDateRes " +
                        "AND r.ENDDATERES = :endDateRes " +
                        "AND (" +
                        "((r.ENDDATERES = r.STARTDATERES) " +
                        "AND (r.STARTTIMERES <= :endTimeRes AND r.ENDTIMERES >= :startTimeRes)) " +
                        "OR ((r.ENDDATERES > r.STARTDATERES) " +
                        "AND (r.STARTTIMERES > :startTimeRes AND r.ENDTIMERES < :endTimeRes))" +
                        ")", nativeQuery = true)
        List<Reservation> findBusyParkingSpaces(
                        @Param("cityId") String cityId,
                        @Param("parkingId") int parkingId,
                        @Param("vehicleType") String vehicleType,
                        @Param("startDateRes") LocalDate startDateRes,
                        @Param("startTimeRes") Time startTimeRes,
                        @Param("endDateRes") LocalDate endDateRes,
                        @Param("endTimeRes") Time endTimeRes);

        @Query(value = "SELECT COUNT(DISTINCT r.FK_IDPARKINGSPACE) " +
                        "FROM RESERVATION r " +
                        "LEFT JOIN PARKINGSPACE ps ON r.FK_IDPARKINGSPACE = ps.IDPARKINGSPACE " +
                        "WHERE r.FK_IDCITY = :cityId " +
                        "AND r.FK_IDPARKING = :parkingId " +
                        "AND (:vehicleType IS NULL OR ps.FK_IDVEHICLETYPE = :vehicleType) " +
                        "AND r.STARTDATERES = :startDateRes " +
                        "AND r.ENDDATERES = :endDateRes " +
                        "AND (" +
                        "((r.ENDDATERES = r.STARTDATERES) " +
                        "AND (r.STARTTIMERES <= :endTimeRes AND r.ENDTIMERES >= :startTimeRes)) " +
                        "OR ((r.ENDDATERES > r.STARTDATERES) " +
                        "AND (r.STARTTIMERES > :startTimeRes AND r.ENDTIMERES < :endTimeRes))" +
                        ")", nativeQuery = true)
        Integer findCountOfBusyParkingSpaces(
                        @Param("cityId") String cityId,
                        @Param("parkingId") int parkingId,
                        @Param("vehicleType") String vehicleType,
                        @Param("startDateRes") Date startDateRes,
                        @Param("startTimeRes") Time startTimeRes,
                        @Param("endDateRes") Date endDateRes,
                        @Param("endTimeRes") Time endTimeRes);

        @Query(value = "SELECT COUNT(*) FROM RESERVATION r WHERE " +
                        "(r.STARTDATERES != r.ENDDATERES AND r.STARTDATERES = :startDateRes AND r.STARTTIMERES <= :startTimeRes AND r.FK_IDPARKING = :idParking) OR "
                        +
                        "(r.STARTDATERES < :startDateRes AND r.ENDDATERES > :startDateRes AND r.FK_IDPARKING = :idParking) OR "
                        +
                        "(r.STARTDATERES = :startDateRes AND r.ENDDATERES = :startDateRes AND (r.STARTTIMERES <= :startTimeRes AND r.ENDTIMERES > :startTimeRes) AND r.FK_IDPARKING = :idParking) OR "
                        +
                        "(r.STARTDATERES != r.ENDDATERES AND r.ENDDATERES = :startDateRes AND r.ENDTIMERES > :startTimeRes AND r.FK_IDPARKING = :idParking) AND r.FK_IDRESSTATUS != 'CAN'", nativeQuery = true)
        int getDateHourCount(
                        @Param("startDateRes") Date startDateRes,
                        @Param("startTimeRes") Time startTimeRes,
                        @Param("idParking") int idParking);

        @Query(value = "SELECT COUNT(*) " +
                        "FROM RESERVATION r " +
                        "WHERE r.STARTDATERES = :startDateRes AND " +
                        "r.STARTTIMERES <= :startTimeRes AND r.ENDTIMERES > :startTimeRes AND " +
                        "r.FK_IDCITY = :city", nativeQuery = true)
        int getDateHourCount(@Param("startDateRes") Date startDateRes,
                        @Param("startTimeRes") Time startTimeRes,
                        @Param("city") String city);

        @Query(value = "SELECT COUNT(*) " +
                        "FROM RESERVATION r " +
                        "WHERE r.STARTDATERES = :startDateRes AND " +
                        "r.STARTTIMERES <= :startTimeRes AND r.ENDTIMERES > :startTimeRes ", nativeQuery = true)
        int getDateHourCount(@Param("startDateRes") Date startDateRes,
                        @Param("startTimeRes") Time startTimeRes);

        @Query(value = "SELECT SUM(TOTALRES) " +
                        "FROM RESERVATION r " +
                        "WHERE r.ENDDATERES = :date AND " +
                        "r.FK_IDPARKING = :idParking AND r.FK_IDRESSTATUS = 'COM'", nativeQuery = true)
        Float getSumTotalRes(Date date, int idParking);

        @Query(value = "SELECT SUM(TOTALRES) " +
                        "FROM RESERVATION r " +
                        "WHERE r.ENDDATERES = :date AND " +
                        "r.FK_IDCITY = :city AND r.FK_IDRESSTATUS = 'COM'", nativeQuery = true)
        Float getSumTotalRes(Date date, String city);

        @Query(value = "SELECT SUM(TOTALRES) " +
                        "FROM RESERVATION r " +
                        "WHERE r.ENDDATERES = :date AND r.FK_IDRESSTATUS = 'COM'", nativeQuery = true)
        Float getSumTotalRes(Date date);

//        @Query(value = "SELECT * FROM CARD u WHERE u.FK_CLIENT_IDUSER LIKE %:userId%", nativeQuery = true)
//        List<Card> findByUserId();

        @Query("SELECT r.totalRes FROM Reservation r WHERE r.client.userId.idUser = :userId AND r.startDateRes = CURRENT_DATE AND r.status = 'PEN' ORDER BY r.startDateRes DESC")
        float findReservationAmountByUserId(@Param("userId") String userId);

        @Query(value = "SELECT COUNT(*) FROM RESERVATION r WHERE " +
                        "(r.STARTDATERES != r.ENDDATERES AND r.STARTDATERES = :date AND r.STARTTIMERES <= :hour AND r.FK_IDPARKING = :idParking) OR "
                        +
                        "(r.STARTDATERES < :date AND r.ENDDATERES > :date AND r.FK_IDPARKING = :idParking) OR " +
                        "(r.STARTDATERES = :date AND r.ENDDATERES = :date AND (r.STARTTIMERES <= :hour AND r.ENDTIMERES > :hour) AND r.FK_IDPARKING = :idParking) OR "
                        +
                        "(r.STARTDATERES != r.ENDDATERES AND r.ENDDATERES = :date AND r.ENDTIMERES > :hour AND r.FK_IDPARKING = :idParking) AND r.FK_IDRESSTATUS != 'CAN'", nativeQuery = true)
        int occupationHour(@Param("date") Date date, @Param("hour") Time hour, @Param("idParking") int idParking);

        @Query(value = "SELECT COUNT(*) FROM RESERVATION r WHERE " +
                        "(r.STARTDATERES != r.ENDDATERES AND r.STARTDATERES = :date AND r.STARTTIMERES <= :hour AND r.FK_IDCITY = :city) OR "
                        +
                        "(r.STARTDATERES < :date AND r.ENDDATERES > :date AND r.FK_IDCITY = :city) OR " +
                        "(r.STARTDATERES = :date AND r.ENDDATERES = :date AND (r.STARTTIMERES <= :hour AND r.ENDTIMERES > :hour) AND r.FK_IDCITY = :city) OR "
                        +
                        "(r.STARTDATERES != r.ENDDATERES AND r.ENDDATERES = :date AND r.ENDTIMERES > :hour AND r.FK_IDCITY = :city)", nativeQuery = true)
        int cityOccupationHour(@Param("date") Date date, @Param("hour") Time hour, @Param("city") String city);

        @Query(value = "SELECT COUNT(*) FROM RESERVATION r WHERE " +
                        "(r.STARTDATERES != r.ENDDATERES AND r.STARTDATERES = :date AND r.STARTTIMERES <= :hour) OR "
                        +
                        "(r.STARTDATERES < :date AND r.ENDDATERES > :date) OR " +
                        "(r.STARTDATERES = :date AND r.ENDDATERES = :date AND (r.STARTTIMERES <= :hour AND r.ENDTIMERES > :hour)) OR "
                        +
                        "(r.STARTDATERES != r.ENDDATERES AND r.ENDDATERES = :date AND r.ENDTIMERES > :hour)", nativeQuery = true)
        int allOccupationHour(@Param("date") Date date, @Param("hour") Time hour);

        /*@Query(value = "SELECT COUNT(*) FROM RESERVATION r " +
                        "INNER JOIN PARKINGSPACE ps ON r.FK_IDPARKINGSPACE = ps.IDPARKINGSPACE " +
                        "WHERE r.FK_IDCITY = :city " +
                        "AND ps.FK_IDCITY = :city " +
                        "AND ps.FK_IDVEHICLETYPE = :vehicleType")
        int countVehiclesRes(@Param("vehicleType") String vehicleType, @Param("city") String city);*/

        @Query(value="SELECT * FROM RESERVATION r WHERE r.FK_IDCITY = :city AND FK_IDRESSTATUS != 'CAN'",nativeQuery=true)
        List<Reservation> allCityRes(@Param("city") String city);

}