import play.test.*;
import play.mvc.*;
import play.mvc.Http.*;

import java.util.List;

import org.junit.*;
import org.joda.time.DateTime;
import org.joda.time.format.*;

import models.*;
import controllers.Organizers;

public class OrganizerTest extends UnitTest {
    List<Long> problem_ids;

    @Test
    public void organizeCompetition() {
        // Crear un nuevo concurso
        new Competition("Concurso Ejemplar", "", null, null).save();

        Competition concurso = Competition.all().first();

        assertNotNull(concurso);

        // Crear un organizador para este concurso
        Organizer org = concurso.createOrganizer("El Gran", "Maestro", "organizador", "organizador");
        assertNotNull(org);
        assertEquals(org.role, ApplicationRole.getByName("organizer"));

        // Verificar que el concurso no se inició aún
        assertEquals(concurso.startTime, null);
        assertEquals(concurso.endTime, null);

        // Determinar duracion
        DateTimeFormatter dtf = DateTimeFormat.forPattern("HH:mm");
        DateTime duracion = dtf.parseDateTime("00:05"); // 5 minutos

        concurso.start(duracion);
        assertNotNull(concurso.startTime);

        concurso.stop();
        assertNotNull(concurso.endTime);
    }

    /**
     * Start a competition with specified problems.
     */
    @Test
    public void startCompetitionWithProblems() {
        problem_ids.clear();

        Competition competition = new Competition("Other competition", "", null, null);
        competition.save();

        // Create some problems
        Problem problem_1 = new Problem("Test 1", "Test 1");
        problem_1.save();
        problem_ids.add(problem_1.id);
        Problem problem_2 = new Problem("Test 2", "Test 2");
        problem_2.save();
        problem_ids.add(problem_2.id);

        competition.addProblem(problem_1);
        competition.addProblem(problem_2);

    }
}