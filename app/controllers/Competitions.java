package controllers;

import org.joda.time.*;
import org.joda.time.format.*;

import models.*;

/**
 * Competitions
 *
 * Controller for the Competition model.
 *
 * @author Kenny Meyer <knny.myer@gmail.com>
 */
public class Competitions extends Application {

    public static void duracion(Long id) {
        Competition competition = Competition.findById(id);

        DateTime startTime = new DateTime(competition.startTime);
        DateTime endTime = new DateTime(competition.endTime);
        DateTime duration = competition.duration;

        String inputDateTime = "HH:mm:ss";
        String reprStartTime = startTime.toString(inputDateTime);

        renderText(reprStartTime);
    }
}