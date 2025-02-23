package gdbv.clinica.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "doctors", schema = "clinica")
public class Doctor extends Person {

    @OneToOne
    @JoinColumn(name="schedule_id", referencedColumnName = "id")
    private Schedule schedule;

    @OneToMany(mappedBy = "doctor", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Turn> turns = new ArrayList<>();
}
