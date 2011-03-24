package models;

import javax.persistence.*;

import play.db.jpa.*;
import play.data.validation.*;

/**
 * Evaluation
 *
 * A Jury member creates an evaluation of a work, and modifies the indicators.
 * The indicators serve to point the actual work.
 * 
 * Indicators:
 * <ul>
 *  <li>compiles</li>
 *  <li>returnsCorrectResult</li>
 * </ul>
 *
 * @author Kenny Meyer <knny.myer@gmail.com>
 */
@Entity
public class Evaluation extends Model {
    public Trabajo work;
    public Jurado jury;

    // Indicators
    public boolean compiles;
    public boolean returnsCorrectResult;

    // Methods, and all that stuff
    public Evaluation (Trabajo work, Jurado jury) {
        this.work = work;
        this.jury = jury;

        create();
    }
}