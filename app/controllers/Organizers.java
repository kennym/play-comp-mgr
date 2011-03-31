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
    public static void startCompetition(final Long id,
                                        final String duration,
                                        final List<Long> problem_ids) {
        validation.required(id);
        validation.required(duration);
        validation.required(problem_ids);
        System.out.println(problem_ids);
        System.out.println(params.toString());
        // Duration should be formatted HH:mm:ss, but not 00:00:00
        validation.match(duration, "^[0-9]{2}:[0-9]{2}:[0-9]{2}$");
        if (validation.hasErrors()) {
            params.flash();
            validation.keep();
            Organizers.index();
        }

        Competition competition = Competition.findById(id);

        // Parse datetime
        DateTimeFormatter dtf = DateTimeFormat.forPattern("HH:mm:ss");
        DateTime durationDateTime = dtf.parseDateTime(duration);

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
