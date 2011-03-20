package models;

import play.data.validation.*;
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
public class Equipo extends Model {

    public String nombre;

    @OneToMany
    public List<Concursante> concursantes;

    @Required
    @ManyToOne
    public Concurso concurso;

    public Equipo(Concurso concurso, String nombre) {
        this.concurso = concurso;
        this.nombre = nombre;
        create();
    }

    @Override
    public String toString() {
        return this.nombre;
    }
}