package models;

import java.util.*;
import javax.persistence.*;

import play.db.jpa.*;
import play.data.validation.*;
import play.data.binding.As;

import models.*;

/**
 * Representa el concurso
 *
 * @author Kenny Meyer <knny.myer@gmail.com>
 */
@Entity
public class Concurso extends Model {

    @Required
    public String titulo;

    @Lob
    public String descripcion;

    @Temporal(TemporalType.TIME)
    @As("hh:mm:ss")
    public Date duracion;

    @OneToMany
    public List<Organizador> organizadores;
    @OneToMany
    public List<Equipo> equipos;
    @OneToMany
    public List<Jurado> jurados;
    
    public Concurso(String titulo, String descripcion, Date duracion) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.duracion = duracion;
        create();
    }

    public Organizador crearOrganizador(String nombre,
                                        String login,
                                        String password) {
        Organizador org = new Organizador(this, nombre, login, password);
        this.refresh();
        
        return org;
    }

    public Equipo crearEquipo(String nombre) {
        Equipo equipo = new Equipo(this, nombre);
        this.refresh();

        return equipo;
    }

    public Jurado crearJurado(String nombre,
                              String apellido,
                              String login,
                              String password) {
        Jurado jurado = new Jurado(this, nombre, apellido, login, password);
        this.refresh();

        return jurado;
    }

    public String toString() {
        return this.titulo;
    }
}