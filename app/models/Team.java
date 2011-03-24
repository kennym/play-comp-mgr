package models;

import play.data.validation.*;
import java.util.*;

import javax.persistence.*;

import play.db.jpa.*;

/**
 * Class Name
 *
 * Class description - Explain why you need it and what it does.
 *
 * @author Kenny Meyer <knny.myer@gmail.com>
 */
@Entity
public class Team extends Model {

    public String name;

    @OneToMany(cascade=CascadeType.PERSIST)
    public List<Participant> participants;

    @Required
    @ManyToOne
    public Competition competition;

    public Team(Competition competition, String name) {
        this.competition = competition;
        this.name = name;

        create();
    }

    @Override
    public String toString() {
        return this.name;
    }
}