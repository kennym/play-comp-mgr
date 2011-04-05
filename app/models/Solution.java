package models;

import java.util.Date;
import javax.persistence.*;

import play.db.jpa.*;
import play.data.validation.*;
import play.data.binding.As;

/**
 * A solution to a given Problem()
 *
 * @author Kenny Meyer <knny.myer@gmail.com>
 */
@Entity
public class Solution extends Model {
    /**
     * A solution can have only one participant to point to, because the solution
     * is supposedly unique, but a participant can have various solutions as there
     * can be various problems.
     */
    @Required
    @ManyToOne
    public Participant participant;

    /**
     * A solution must have a OneToOne relationship to a Problem() as it
     * supposedly provides the "solution" to a Problem().
     */
    @ManyToOne
    public Problem problem;

    @Required
    public Blob blob;

    public long points;

    @Temporal(TemporalType.TIMESTAMP)
    @As("dd/MM/yyyy/hh:mm:ss")
    public Date creationDate = new Date();

    public Solution(Participant participant,
                    Problem problem,
                    Blob blob) {
        this.participant = participant;
        this.problem = problem;
        this.blob = blob;

        create();
    }

    public boolean exists() {
        if (this.blob.exists()) {
            return true;
        }
        return false;
    }

    public void evaluate() {

    }
}
