package gdbv.clinica.models;

import jakarta.persistence.*;
@Entity
@Table(name = "secretaries", schema = "clinics")
public class Secretary extends Person{
    private String sector;
}
