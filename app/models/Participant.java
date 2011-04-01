package models;

import javax.persistence.*;

import play.db.jpa.*;
import play.data.validation.*;

/**;
 *
 * Class Name
 *
 * Class description - Explain why you need it and what it does.
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

    public Work work;

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

    public Work createWork(Blob solution) {
        Work work = new Work(this, solution);

        this.refresh();

        return work;
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
        long points = 0;

//        try {
//            points = this.work.points;
//        } catch (NullPointerException e) {
//            points = 0;
//        }

        return points;
    }

    public String toString() {
        return "Concursante(" + this.name + " " + this.surname + ")";
    }
}