package controllers;

import java.util.List;

import org.joda.time.*;
import org.joda.time.format.*;

import models.*;

/**
 * Controller for the Organizer model.
 *
 * @author Kenny Meyer <knny.myer@gmail.com>
 */
public class Organizers extends Application {

    public static void index() {
        User user = connected();

        try {
            if (user.role != ApplicationRole.getByName("organizer")) {
                forbidden("No eres un organizador.");
            }
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

    /**
     * Begin the competition given the ID, the duration and the problem.
     *
     * @param id the ID of the competition
     * @param duration the duration of the competition
     * @param problem the ID of the problem for the competition
     */
    public static void startCompetition(final Long id,
                                        final String duration,
                                        final Long problem) {
        validation.required(id);
        validation.required(duration);
        // Duration should be formatted HH:mm:ss, but not 00:00:00
        validation.match(duration, "^[0-9]{2}:[0-9]{2}:[0-9]{2}$");
        validation.required(problem);
        if (validation.hasErrors()) {
            params.flash();
            validation.keep();
            Organizers.index();
        }

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
