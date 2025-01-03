package gdbv.clinica.models;

import jakarta.persistence.*;

@Entity
@Table(name = "users", schema = "clinica")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String user_name;
    private String password;
    private String rol;
}
