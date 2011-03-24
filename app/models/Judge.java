package models;

import java.util.*;
import javax.persistence.*;

import play.db.jpa.*;
import play.data.validation.*;

/**
 * Class Name
 *
 * Class description - Explain why you need it and what it does.
 *
 * @author Kenny Meyer <knny.myer@gmail.com>
 */
@Entity
public class Judge extends User {

    @Required
    public String name;
    @Required
    public String surname;
    
    @Required
    @ManyToOne
    public Competition competition;

    public Judge(Competition competition,
                  String name,
                  String surname,
                  String login,
                  String password) {
        this.competition = competition;
        this.name = name;
        this.surname = surname;

        this.login = login;
        this.password = password;
        this.role = ApplicationRole.getByName("judge");

        create();
    }

    public String toString() {
        return "Judge(" + this.name + " " + this.surname + ")";
    }

    public String name_and_surname() {
        return this.name + " " + this.surname;
    }
}