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
 * @author Kenny Meyer <knny.myer@gmail.com>
 */
@Every("5s")
public class TimeChecker extends Job {

    @Override
    public void doJob() {
        Logger.info("TimeChecker started");

        // Get all contests which have already started, not yet finished
        List<Concurso> concursos = Concurso.find(
                "select c from Concurso c where c.tiempoInicial != null "
                + "and c.tiempoFinal = null")
                .fetch();

        // Iterate through contests and check if time limit has been passed
        for (Concurso concurso: concursos) {
            // Check if initialTime + duration => currentTime
            DateTime initialTime = new DateTime(concurso.tiempoInicial);
            if (initialTime.plus(concurso.duracion.getMillisOfDay()).isBeforeNow()) {
                // Set final time or stop it
                concurso.parar();
                // Block submissions
                concurso.blockSubmissions();

                Logger.info("TimeChecker: " + concurso.toString() + " stopped!");
            }
        }

        Logger.info("TimeChecker terminated");
    }
}