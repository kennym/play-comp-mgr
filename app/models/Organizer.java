package models;

import play.data.validation.*;

import javax.persistence.*;

/**
 * Class Name
 *
 * Class description - Explain why you need it and what it does.
 *
 * @author Kenny Meyer <knny.myer@gmail.com>
 */
@Entity
public class Organizer extends User {

    @Required
    public String name;

    @Required
    public String surname;

    @Required
    @ManyToOne
    public Competition competition;

    public Organizer(Competition competition,
                     String name,
                     String surname,
                     String login,
                     String password) {
        this.competition = competition;
        this.name = name;
        this.surname = surname;

        this.login = login;
        this.password = password;
        this.role = ApplicationRole.getByName("organizer");

        create();
    }

    @Override
    public String toString() {
        return "Organizer(" + this.name + " " + this.surname + ")";
    }

    public String name_and_surname() {
        return this.name + " " + this.surname;
    }
}