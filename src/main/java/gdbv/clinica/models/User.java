package gdbv.clinica.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "users", schema = "clinica")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name")
    private String userName;

    private String password;
    private String role;

    @OneToOne(optional = true) // Relación opcional con Doctor
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    private Doctor doctor;

    @OneToOne(optional = true) // Relación opcional con Secretary
    @JoinColumn(name = "secretary_id", referencedColumnName = "id")
    private Secretary secretary;

    // Método para validar que solo uno de los dos atributos esté asignado
    @PrePersist
    @PreUpdate
    private void validateReferences() {
        if (doctor != null && secretary != null) {
            throw new IllegalStateException("A user cannot reference both a doctor and a secretary at the same time.");
        }
    }
}
