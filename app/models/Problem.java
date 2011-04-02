package models;

import java.util.List;
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
    @OneToMany(cascade=CascadeType.PERSIST)
    public List<Competition> competitions;

    @Required
    public String title;

    @Required
    @Lob
    public String description;

    public Problem (String title,
                    String description) {
        this.title = title;
        this.description = description;

        create();
    }

    public void setCompetition(Competition competition) {
        this.competitions.add(competition);
    }

    public String toString() {
        return this.title;
    }
}