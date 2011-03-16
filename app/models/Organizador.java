package models;

import play.data.validation.*;

import javax.persistence.*;

/**
 * Class Name
 *
 * Class description - Explain why you need it and what it does.
 *
 * @author Kenny Meyer <knny.myer@gmail.com>
 */
@Entity
public class Organizador extends Usuario {

    @Required
    public String nombre;

    @Required
    public String apellido;

    @Required
    @ManyToOne
    public Concurso concurso;

    public Organizador(Concurso concurso,
                       String nombre,
                       String apellido,
                       String login,
                       String password) {
        this.concurso = concurso;
        this.nombre = nombre;
        this.apellido = apellido;

        this.login = login;
        this.password = password;
        this.rol = ApplicationRole.getByName("organizador");

        create();
    }

    @Override
    public String toString() {
        return "Organizador(" + this.nombre + " " + this.apellido + ")";
    }

    public String nombre_y_apellido() {
        return this.nombre + " " + this.apellido;
    }
}