package models;

import java.util.*;
import javax.persistence.*;

import play.db.jpa.*;           

/**
 * Class Name
 *
 * Class description - Explain why you need it and what it does.
 *
 * @author Kenny Meyer <knny.myer@gmail.com>
 */
@Entity
public class Organizador extends Model {

    public String nombre;

    @ManyToOne
    public Concurso concurso;

    public Organizador(Concurso concurso, String nombre) {
        this.concurso = concurso;
        this.nombre = nombre;
        create();
    }

    public String toString() {
        return this.nombre;
    }
}