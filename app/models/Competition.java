package models;

import java.util.Date;
import java.util.List;
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

    @OneToMany(cascade=CascadeType.ALL)
    public List<Participant> participants;
    @OneToMany(cascade=CascadeType.ALL)
    public List<Organizer> organizers;
    @OneToMany(cascade=CascadeType.ALL)
    public List<Team> teams;
    @OneToMany(cascade=CascadeType.ALL)
    public List<Judge> judges;

    /**
     * A competition can have many problems
     */
    @OneToMany(cascade=CascadeType.ALL)
    public List<Problem> problems;

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

        assert this.problems != null;

        save();
    }

    public void stop() {
        this.endTime = new Date();

        save();
    }

    public void reset() {
        this.startTime = null;
        this.endTime = null;
        this.duration = null;

        this.problems.clear();

        save();
    }

    /**
     * Check that competition is running.
     * 
     * A competition validates as started when the duration, start time and 
     * end time were defined.
     * 
     * @return boolean
     */
    public boolean isRunning() {
        if (this.duration != null &&
            this.startTime != null &&
            this.endTime == null) {
            return true;
        }
        return false;
    }

    public void blockSubmissions() {
        for(Participant participant: this.participants) {
            participant.canSubmit(false);
        }

        save();
    }

    public long getRemainingSeconds() {
        DateTime startTime = new DateTime(this.startTime);
        DateTime endTime = startTime.plusHours(this.duration.getHourOfDay());
        endTime = startTime.plusSeconds(this.duration.getSecondOfDay());

        Duration restTime = new Duration(new DateTime(), endTime);

        return restTime.getStandardSeconds();
    }

    public Problem getProblem(int index) {
        return this.problems.get(index);
    }

    public List<Problem> getProblems() {
        return Problem.find("byCompetition", this).fetch();
    }

    /**
     * Crear y devolver un Organizador() relacionado al concurso actual.
     *
     * @param name El nombre del organizador
     * @param surname El apellido del organizador
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
     * @param name El nombre del equipo
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
     * @param name El nombre del Jurado
     * @param surname El apellido del Jurado
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
     * @param team El nombre del Participante
     * @param name El nombre del Participante
     * @param surname El apellido del Participante
     * @param login El nombre del usuario del Participante
     * @param password La contraseña del usuario del Participante
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

    /**
     * Create and return a problem
     *
     * @param title of the problem
     * @param description of the problem
     * @return Problem
     */
    public Problem createProblem(String title,
                                 String description) {
        Problem problem = new Problem(this, title, description);

        this.refresh();

        return problem;
    }

    public String toString() {
        return this.title;
    }
}