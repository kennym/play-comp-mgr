package models;

import javax.persistence.*;

import play.db.jpa.*;
import play.data.validation.*;

/**
 * Problem
 *
 * Represents a problem which has to be resolved.
 * 
 * @author Kenny Meyer <knny.myer@gmail.com>
 */
@Entity
public class Problem extends Model {
    @ManyToOne
    public Competition competition;

    @Required
    public String title;

    public Solution solution;

    @Required
    @Lob
    public String description;

    public Problem (Competition competition,
                    String title,
                    String description) {
        this.competition = competition;
        this.title = title;
        this.description = description;

        create();
    }

    public Solution getSolution() {
        return Solution.find("byProblem", this).first();
    }

    public boolean isSolved() {
        Solution solution = Solution.find("problem", this).first();
        try {
            if (solution.exists()) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public String toString() {
        return this.title;
    }
}