package gdbv.clinica.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@MappedSuperclass
public class UserOwner extends Person {

    @OneToOne(mappedBy = "owner")
    private User user;

}
