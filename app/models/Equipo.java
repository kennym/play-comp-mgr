package models;

import java.util.*;
import java.util.Date;

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
public class Equipo extends Model {

    public String nombre;

    @OneToMany
    public List<Concursante> concursantes;

    @ManyToOne
    public Concurso concurso;

    public Equipo(Concurso concurso, String nombre) {
        this.concurso = concurso;
        this.nombre = nombre;
        create();
    }

    public String toString() {
        return this.nombre;
    }

    public Concursante crearConcursante(String nombre, String apellido) {
        Concursante con = new Concursante(this, nombre, apellido);
        this.refresh();         
        
        return con;
    }
}