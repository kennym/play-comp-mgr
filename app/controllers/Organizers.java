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
     * Begin the competition given the ID and the duration.
     *
     * @param id the ID of the competition
     * @param duration the duration of the competition
     */
    public static void startCompetition(final Long competition_id,
                                        final String duration) {
        validation.required(competition_id);
        validation.required(duration);
        System.out.println(request.params.urlEncode());
        // Duration should be formatted HH:mm:ss, but not 00:00:00
        validation.match(duration, "^[0-9]{2}:[0-9]{2}:[0-9]{2}$");
        if (validation.hasErrors()) {
            params.flash();
            validation.keep();
            Organizers.index();
        }

        Competition competition = Competition.findById(competition_id);

        // Parse datetime
        DateTimeFormatter dtf = DateTimeFormat.forPattern("HH:mm:ss");
        DateTime durationDateTime = dtf.parseDateTime(duration);

        competition.start(durationDateTime);

        Organizers.index();
    }

    public static void createProblem() {

    }

    public static void stopCompetition(Long id) {
        Competition competition = Competition.findById(id);

        competition.stop();

        Organizers.index();
    }

    public static void resetCompetition(Long id) {
        Competition competition = Competition.findById(id);
        competition.reset();

        Organizers.index();
    }

    public static void blockSubmission(Long id, boolean canSubmit) {
        Participant participant = Participant.findById(id);

        participant.canSubmitSolution(canSubmit);

        Organizers.index();
    }
}
