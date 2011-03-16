package models;

import javax.persistence.*;

import play.db.jpa.*;
import play.data.validation.*;

/**;
 *
 * Class Name
 *
 * Class description - Explain why you need it and what it does.
 *
 * @author Kenny Meyer <knny.myer@gmail.com>
 */
@Entity
public class Concursante extends Usuario {

    @Required
    public String nombre;
    @Required
    public String apellido;

    @Required
    @ManyToOne
    public Equipo equipo;

    /**
     * El trabajo en forma de un objeto binario.
     */
    public Blob trabajo;

    public Concursante(Equipo equipo,
                       String nombre,
                       String apellido,
                       String login,
                       String password) {
        this.equipo = equipo;
        this.nombre = nombre;
        this.apellido = apellido;

        this.login = login;
        this.password = password;
        this.rol = ApplicationRole.getByName("concursante");
        create();
    }

    public String toString() {
        return "Concursante(" + this.nombre + " " + this.apellido + ")";
    }
}