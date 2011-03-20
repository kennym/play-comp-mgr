package models;

import javax.persistence.*;

import play.db.jpa.*;
import play.data.validation.*;

/**
 * Representa el concurso
 *
 * @author Kenny Meyer <knny.myer@gmail.com>
 */
@Entity
public class Trabajo extends Model {
    @Required
    @OneToOne
    public Concursante concursante;

    /**
     * El trabajo en forma de un objeto binario.
     */
    public Blob blob;

    public boolean exists() {
        if (this.blob.exists()) {
            return true;
        } else {
            return false;
        }
    }
    public Trabajo(Concursante concursante, Blob blob) {
        this.concursante = concursante;
        this.blob = blob;

        create();
    }
}