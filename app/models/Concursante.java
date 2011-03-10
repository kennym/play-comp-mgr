package models;

import java.util.*;
import javax.persistence.*;

import play.db.jpa.*;
import play.data.validation.*;

import models.deadbolt.Role;

import models.*;

/**
 * Class Name
 *
 * Class description - Explain why you need it and what it does.
 *
 * @author Kenny Meyer <knny.myer@gmail.com>
 */
@Entity
public class Concursante extends Model {

    @Required
    public String nombre;
    @Required
    public String apellido;

    @ManyToOne
    public Equipo equipo;

    public Concursante(Equipo equipo, String nombre, String apellido) {
        this.equipo = equipo;
        this.nombre = nombre;
        this.apellido = apellido;
        create();
    }

    public String toString() {
        return this.nombre + " " + this.apellido;
    }
}