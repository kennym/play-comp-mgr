package controllers;

import java.util.List;

import org.joda.time.*;
import org.joda.time.format.*;

import models.*;

/**
 * Organizadores
 *
 * El controller para Organizer()
 *
 * @author Kenny Meyer <knny.myer@gmail.com>
 */
public class Organizers extends Application {

    public static void index() {
        User user = connected();

        try {
            if (user.role != ApplicationRole.getByName("organizer"))
                forbidden("No eres un organizador.");
        } catch (NullPointerException e) {
            try {
                Secure.login();
            } catch (Throwable ex) {
                ex.printStackTrace();
            }
        }

        Organizer organizer = Organizer.find("byLogin", user.login).first();

        Competition competition = organizer.competition;
        List<Participant> participants = Participant.find(
                "byCompetitionLike", competition).fetch();
        List<Problem> problems = Problem.all().fetch();

        render(organizer, competition, participants, problems);
    }

    public static void startCompetition(Long id, String duration, Long problem) {
        validation.required(id);
        validation.required(duration);
        validation.required(problem);

        Competition competition = Competition.findById(id);

        // Parse datetime
        DateTimeFormatter dtf = DateTimeFormat.forPattern("HH:mm:ss");
        DateTime durationDateTime = dtf.parseDateTime(duration);

        // Set problem
        competition.setProblem(problem);

        competition.start(durationDateTime);

        Organizers.index();
    }

    public static void stopCompetition(Long id) {
        Competition competition = Competition.findById(id);

        competition.stop();

        Organizers.index();
    }

    public static void canSubmit(Long id, boolean canSubmit) {
        Participant participant = Participant.findById(id);

        participant.canSubmit(canSubmit);

        Organizers.index();
    }
}
