package gdbv.clinica.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "responsibles", schema = "clinica")
public class Responsible extends Person {
    private String relation;

}
