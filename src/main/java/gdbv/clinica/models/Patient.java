package gdbv.clinica.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "patients", schema = "clinica")
public class Patient extends Person{
    private boolean hasEPS;
    private String RH;
    private boolean hasInsurance;

    @OneToOne
    @JoinColumn(name = "responsible_id", referencedColumnName = "id")
    private Responsible responsible;

    @OneToMany(mappedBy = "patient", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Turn> turns = new ArrayList<>();


}
