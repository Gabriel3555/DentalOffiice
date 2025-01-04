package gdbv.clinica.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "secretaries", schema = "clinica")
public class Secretary extends UserOwner{
    private String field;

}
