package gdbv.clinica.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "user_owners", schema = "clinica")
public abstract class UserOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String address;
    private LocalDate bornDate;

    protected UserOwner() {
        bornDate = LocalDate.now();
    }

    @OneToOne(mappedBy = "owner", cascade = CascadeType.ALL)
    private User user;
}
