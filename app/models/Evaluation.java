package models;

import javax.persistence.*;

import play.db.jpa.*;
import play.data.validation.*;

/**
 * Evaluation
 *
 * A Judge member creates an evaluation of a work, and modifies the indicators.
 * The indicators serve to point the actual work.
 * 
 * Indicators:
 * 
 * @author Kenny Meyer <knny.myer@gmail.com>
 */
@Entity
public class Evaluation extends Model {
    @ManyToOne
    public Judge judge;
    @ManyToOne
    public Solution solution;

    // Indicators
    public boolean compiles = false;
    public boolean returnsCorrectResult = false;

    // Methods, and all that stuff
    public Evaluation(Judge judge,
                      Solution solution) {
        this.judge = judge;
        this.solution = solution;

        create();
    }

    /**
     * Evaluate the solution of the Judge
     */
    public void setIndicators(boolean compiles,
                              boolean returnsCorrectResult) {
        this.compiles = compiles;
        this.returnsCorrectResult = returnsCorrectResult;

        save();
    }
}