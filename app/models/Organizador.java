package models;

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

    public String nombre;

    @ManyToOne
    public Concurso concurso;

    public Organizador(Concurso concurso,
                       String nombre,
                       String login,
                       String password) {
        this.concurso = concurso;
        this.nombre = nombre;

        this.login = login;
        this.password = password;
        this.rol = ApplicationRole.getByName("organizador");

        create();
    }

    @Override
    public String toString() {
        return this.nombre;
    }
}