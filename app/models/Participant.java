package models;

import java.util.List;
import javax.persistence.*;

import play.db.jpa.*;
import play.data.validation.*;


/**
 * Participant
 *
 * Represents the participant model.
 *
 * @author Kenny Meyer <knny.myer@gmail.com>
 */
@Entity
public class Participant extends User {

    @Required
    public String name;
    @Required
    public String surname;

    @Required
    @ManyToOne
    public Competition competition;
    @Required
    @ManyToOne
    public Team team;

    public boolean canSubmitWork;

    /**
     * One solution for each problem.
     */
    @OneToMany
    public List<Solution> solutions;

    public Participant(Competition competition,
                       Team team,
                       String name,
                       String surname,
                       String login,
                       String password) {
        this.competition = competition;
        this.team = team;
        this.name = name;
        this.surname = surname;

        this.login = login;
        this.password = password;
        this.role = ApplicationRole.getByName("participant");

        create();
    }

    public void submitSolution(Problem problem, Blob blob) {
        solutions.add(new Solution(this, problem, blob));

        this.save();
    }

    public void canSubmit(boolean yesOrNo) {
        this.canSubmitWork = yesOrNo;

        this.save();
    }

    public boolean isBlocked() {
        return this.canSubmitWork;
    }

    /**
     * Calculate the points received from the Jury for the work.
     *
     * @return long
     */
    public long getPoints() {
        // TODO
        long points = 0;

        return points;
    }

    public String toString() {
        return "Concursante(" + this.name + " " + this.surname + ")";
    }
}
