package models;

import java.util.*;
import javax.persistence.*;

import org.joda.time.*;
import org.hibernate.annotations.Type;

import play.db.jpa.*;
import play.data.validation.*;
import play.data.binding.As;

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
    @As("dd/MM/yyyy/hh:mm:ss")
    public Date tiempoInicial;
    @Temporal(TemporalType.TIMESTAMP)
    @As("dd/MM/yyyy/hh:mm:ss")
    public Date tiempoFinal;

    /**
     * Duración del concurso.
     */
    @Column(name = "duracion_datetime", nullable = true)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    public DateTime duracion;

    @OneToMany(cascade=CascadeType.PERSIST)
    public List<Concursante> concursantes;
    @OneToMany(cascade=CascadeType.PERSIST)
    public List<Organizador> organizadores;
    @OneToMany(cascade=CascadeType.PERSIST)
    public List<Equipo> equipos;
    @OneToMany(cascade=CascadeType.PERSIST)
    public List<Jurado> jurados;

    public Concurso(String titulo,
                    String descripcion,
                    Date tiempoInicial,
                    Date tiempoFinal) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.tiempoInicial = tiempoInicial;
        this.tiempoFinal = tiempoFinal;
        create();
    }

    public void iniciar(DateTime duracion) {
        this.duracion = duracion;
        this.tiempoInicial = new Date();

        save();
    }

    public void parar() {
        this.tiempoFinal = new Date(new Date().getTime());

        save();
    }

    public void blockSubmissions() {
        for(Concursante concursante: this.concursantes) {
            concursante.canSubmit(false);
        }
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

    /**
     * Create and return a contestant
     *
     * @param nombre El nombre del Jurado
     * @param apellido El apellido del Jurado
     * @param login El nombre del usuario del Jurado
     * @param password La contraseña del usuario del Jurado
     * @return Jurado()
     */
    public Concursante crearConcursante(Equipo equipo,
                                       String nombre,
                                       String apellido,
                                       String login,
                                       String password) {
        Concursante concursante = new Concursante(this, equipo, nombre, apellido, login, password);

        this.refresh();

        return concursante;
    }

    public String toString() {
        return this.titulo;
    }
}