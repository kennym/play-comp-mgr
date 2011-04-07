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

    public boolean canSubmitSolution;

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

    public boolean hasSolved(Problem problem) {
        for (Solution solution: solutions) {
            if (solution.problem == problem) {
                return true;
            }
        }
        return false;
    }

    public void submitSolution(Problem problem, Blob blob) {
        solutions.add(new Solution(this, problem, blob));

        this.save();
    }

    public void canSubmitSolution(boolean yesOrNo) {
        this.canSubmitSolution = yesOrNo;

        this.save();
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

    public List<Solution> getSolutions() {
        return Solution.find("participant", this).fetch();
    }

    public String name_and_surname() {
        return this.name + " " + this.surname;
    }

    public String toString() {
        return "Concursante(" + this.name + " " + this.surname + ")";
    }
}
