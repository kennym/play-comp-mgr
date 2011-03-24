package jobs;

import java.util.List;

import org.joda.time.*;

import play.jobs.*;
import play.Logger;

import models.*;

/**
 * TimeChecker to track contest duration
 *
 * Checks when contest finsished, and sets the final time accordingly plus
 * blocks the user submissions.
 *
 * Think of it as a timer.
 *
 * @author Kenny Meyer <knny.myer@gmail.com>
 */
@Every("5s")
public class TimeChecker extends Job {

    @Override
    public void doJob() {
        Logger.info("TimeChecker started");

        // Get all contests which have already started, not yet finished
        List<Competition> competitions = Competition.find(
                "select c from Competition c where c.startTime != null "
                + "and c.endTime = null")
                .fetch();

        // Iterate through contests and check if time limit has been passed
        for (Competition competition: competitions) {
            // Check if initialTime + duration => currentTime
            DateTime startTime = new DateTime(competition.startTime);
            if (startTime.plus(competition.duration.getMillisOfDay()).isBeforeNow()) {
                // Set final time or stop it
                competition.stop();
                // Block submissions
                competition.blockSubmissions();

                Logger.info("TimeChecker: " + competition.toString() + " stopped!");
            }
        }

        Logger.info("TimeChecker terminated");
    }
}