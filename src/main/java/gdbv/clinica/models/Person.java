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
    private String lastName;
    private String phoneNumber;
    private String email;
    private String address;
    private LocalDate bornDate;

    protected Person(){
        bornDate = LocalDate.now();
    }
}