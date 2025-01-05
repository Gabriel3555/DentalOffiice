package gdbv.clinica.models;

import java.time.LocalDate;
import lombok.*;
import jakarta.persistence.*;

@Getter
@Setter
@MappedSuperclass
public abstract class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone_number")
    private String phoneNumber;
    private String email;
    private String address;

    @Column(name = "born_date")
    private LocalDate bornDate;

    @OneToOne(optional = true)
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private User user;

    protected Person(){
        bornDate = LocalDate.now();
    }
}