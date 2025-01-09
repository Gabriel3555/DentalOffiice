package gdbv.clinica.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
@Entity
@Table(name = "turns", schema = "clinica")
public class Turn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalTime app_time;
    private LocalDate app_date;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    public Turn(){
        app_time = LocalTime.now();
        app_date = LocalDate.now();
    }

    public Turn(Long id, LocalDate app_date, LocalTime app_time, Long patientId, Long doctorId, String patientName, String patientLastName) {
        this.id = id;
        this.app_date = app_date;
        this.app_time = app_time;
        this.patient = new Patient(); // Create a minimal Patient object
        this.patient.setId(patientId);
        this.patient.setName(patientName);
        this.patient.setLastName(patientLastName);
        this.doctor = new Doctor(); // Create a minimal Doctor object
        this.doctor.setId(doctorId);
    }
}
