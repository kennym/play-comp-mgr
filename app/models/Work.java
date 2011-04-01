package models;

import java.util.Date;
import javax.persistence.*;

import play.db.jpa.*;
import play.data.validation.*;
import play.data.binding.As;

/**
 * Object which can be evaluated by a Judge and submitted by
 * a Participant.
 *
 * @author Kenny Meyer <knny.myer@gmail.com>
 */
@Entity
public class Work extends Model {
    @Required
    @ManyToOne
    public Participant participant;

    /**
     * El trabajo en forma de un objeto binario.
     */
    public Blob file;

    public long points;

    public Work (Participant participant, Blob file) {
        this.participant = participant;
        this.file = file;

        create();
    }

    public void evaluate() {

    }
}