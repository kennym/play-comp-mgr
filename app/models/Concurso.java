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

    @Temporal(TemporalType.TIMESTAMP)
    public Date initialTime;

    @Temporal(TemporalType.TIMESTAMP)
    public Date endTime;

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

    /**
     * Iniciar el concurso.
     */
    public void iniciar() {

    }

    public void terminar() {

    }

    public void pausar() {

    }
    /**
     * Crear y devolver un Organizador() relacionado al concurso actual.
     *
     * @param nombre El nombre del organizador
     * @param apellido El apellido del organizador
     * @param login El nombre del usuario del organizador
     * @param password La contraseña del organizador
     * @return Organizador
     */
    public Organizador crearOrganizador(String nombre,
                                        String apellido,
                                        String login,
                                        String password) {
        Organizador org = new Organizador(this, nombre, apellido, login, password);
        this.refresh();
        
        return org;
    }

    /**
     * Crear y devolver un Equipo() relacionado con este concurso.
     *
     *
     * @param nombre El nombre del equipo
     * @return Equipo
     */
    public Equipo crearEquipo(String nombre) {
        Equipo equipo = new Equipo(this, nombre);
        this.refresh();

        return equipo;
    }

    /**
     * Crear y devolver un Jurado() relacionado con este concurso.
     *
     * @param nombre El nombre del Jurado
     * @param apellido El apellido del Jurado
     * @param login El nombre del usuario del Jurado
     * @param password La contraseña del usuario del Jurado
     * @return Jurado()
     */
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