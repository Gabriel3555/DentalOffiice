package gdbv.clinica.models;


import jakarta.persistence.*;

import java.time.LocalTime;

@Entity
@Table(name = "schedules", schema = "clinica")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalTime startTime;
    private LocalTime endTime;


}
