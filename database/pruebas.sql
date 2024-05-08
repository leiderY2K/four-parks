SELECT COUNT(*) FROM PARKINGSPACE ps 
                    LEFT JOIN PARKING p ON ps.FK_IDPARKING = p.IDPARKING
                    WHERE p.FK_IDCITY = 'BAQ' AND p.IDPARKING = 2
                    AND ps.FK_IDVEHICLETYPE = 'CAR'