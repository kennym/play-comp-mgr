package models;

import java.util.Date;
import javax.persistence.*;

import play.db.jpa.*;
import play.data.validation.*;
import play.data.binding.As;

/**
 * Representa el trabajo del participante
 *
 * @author Kenny Meyer <knny.myer@gmail.com>
 */
@Entity
public class Work extends Model {
    @Required
    public Participant participant;

    /**
     * El trabajo en forma de un objeto binario.
     */
    public Blob blob;

    public long points;

    @Temporal(TemporalType.TIMESTAMP)
    @As("dd/MM/yyyy/hh:mm:ss")
    public Date creationDate = new Date();

    public Work(Participant participant, Blob blob) {
        this.participant = participant;
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