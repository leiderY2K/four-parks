package com.project.layer.Persistence.Entity;

import java.sql.Time;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SCHEDULE")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Schedule {
    
    @Id
    @Column(name = "IDSCHEDULE", nullable = false)
    private int idSchedule;

    @Column(name = "STARTTIME", nullable = false)
    private Time startTime;

    @Column(name = "ENDTIME", nullable = false)
    private Time endTime;

    @Column(name = "SCHEDULETYPE", nullable = false)
    private String scheduleType;

}
