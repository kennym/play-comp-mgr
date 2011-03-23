package models;

import java.util.Date;
import javax.persistence.*;

import play.db.jpa.*;
import play.data.validation.*;
import play.data.binding.As;

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

    @Temporal(TemporalType.TIMESTAMP)
    @As("dd/MM/yyyy/hh:mm:ss")
    public Date creationDate = new Date();

    public Trabajo(Concursante concursante, Blob blob) {
        this.concursante = concursante;
        this.blob = blob;

        create();
    }

    public boolean exists() {
        if (this.blob.exists()) {
            return true;
        } else {
            return false;
        }
    }

    public void evaluar() {

    }
}