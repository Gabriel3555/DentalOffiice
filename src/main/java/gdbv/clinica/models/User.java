package gdbv.clinica.models;

import gdbv.clinica.enums.Role;
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

    private String user_name;
    private String password;
    private Role role;

    @OneToOne
    @JoinColumn(name = "owner", referencedColumnName = "id")
    private UserOwner owner;
}
