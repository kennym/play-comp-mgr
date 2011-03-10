package models;

import java.util.*;
import javax.persistence.*;

import play.db.jpa.*;

import models.*;

/**
 * Class Name
 *
 * Class description - Explain why you need it and what it does.
 *
 * @author Kenny Meyer <knny.myer@gmail.com>
 */
@Entity
public class Jurado extends Model {

    public String nombre;
    public String apellido;
    
    @ManyToOne
    public Concurso concurso;

    public Jurado(Concurso concurso, String nombre, String apellido) {
        this.concurso = concurso;
        this.nombre = nombre;
        this.apellido = apellido;
        create();
    }

    public String toString() {
        return "Jurado(" + this.nombre + " " + this.apellido + ")";
    }
}