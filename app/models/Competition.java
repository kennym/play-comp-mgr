package models;

import java.util.*;
import javax.persistence.*;

import org.joda.time.*;
import org.hibernate.annotations.Type;

import play.db.jpa.*;
import play.data.validation.*;
import play.data.binding.As;

/**
 * Representa el concurso
 *
 * @author Kenny Meyer <knny.myer@gmail.com>
 */
@Entity
public class Competition extends Model {

    @Required
    public String title;

    @Lob
    public String description;

    @Temporal(TemporalType.TIMESTAMP)
    @As("dd/MM/yyyy/hh:mm:ss")
    public Date startTime;
    @Temporal(TemporalType.TIMESTAMP)
    @As("dd/MM/yyyy/hh:mm:ss")
    public Date endTime;

    /**
     * Duración del concurso.
     */
    @Column(name = "duracion_datetime", nullable = true)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    public DateTime duration;

    @OneToMany(cascade=CascadeType.PERSIST)
    public List<Participant> participants;
    @OneToMany(cascade=CascadeType.PERSIST)
    public List<Organizer> organizers;
    @OneToMany(cascade=CascadeType.PERSIST)
    public List<Team> teams;
    @OneToMany(cascade=CascadeType.PERSIST)
    public List<Judge> judges;

    @ManyToOne
    public Problem problem;

    public Competition(String title,
                       String description,
                       Date startTime,
                       Date endTime) {
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;

        create();
    }

    public void start(DateTime duration) {
        this.duration = duration;
        this.startTime = new Date();

        assert this.problem != null;

        save();
    }

    public void stop() {
        this.endTime = new Date(new Date().getTime());

        save();
    }

    public void blockSubmissions() {
        for(Participant participant: this.participants) {
            participant.canSubmit(false);
        }
    }

    /**
     * Crear y devolver un Organizador() relacionado al concurso actual.
     *
     * @param nombre El nombre del organizador
     * @param apellido El apellido del organizador
     * @param login El nombre del usuario del organizador
     * @param password La contraseña del organizador
     * @return Organizador
     */
    public Organizer createOrganizer(String name,
                                     String surname,
                                     String login,
                                     String password) {
        Organizer org = new Organizer(this, name, surname, login, password);
        this.refresh();
        
        return org;
    }

    /**
     * Crear y devolver un Equipo() relacionado con este concurso.
     *
     *
     * @param nombre El nombre del equipo
     * @return Equipo
     */
    public Team createTeam(String name) {
        Team team = new Team(this, name);

        this.refresh();

        return team;
    }

    /**
     * Crear y devolver un Jurado() relacionado con este concurso.
     *
     * @param nombre El nombre del Jurado
     * @param apellido El apellido del Jurado
     * @param login El nombre del usuario del Jurado
     * @param password La contraseña del usuario del Jurado
     * @return Jurado()
     */
    public Judge createJudge(String name,
                             String surname,
                             String login,
                             String password) {
        Judge judge = new Judge(this, name, surname, login, password);

        this.refresh();

        return judge;
    }

    /**
     * Create and return a contestant
     *
     * @param nombre El nombre del Jurado
     * @param apellido El apellido del Jurado
     * @param login El nombre del usuario del Jurado
     * @param password La contraseña del usuario del Jurado
     * @return Jurado()
     */
    public Participant createParticipant(Team team,
                                         String name,
                                         String surname,
                                         String login,
                                         String password) {
        Participant participant = new Participant(this, team, name, surname, login, password);

        this.refresh();

        return participant;
    }

    public void setProblem(long id) {
        this.problem = Problem.findById(id);

        save();
    }

    public String toString() {
        return this.title;
    }
}