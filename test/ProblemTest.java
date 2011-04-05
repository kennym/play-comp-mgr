import org.junit.*;
import play.test.*;
import play.mvc.Http.*;

import models.*;

public class ProblemTest extends UnitTest {
    private Competition concurso;

    public Competition createContest() {
        concurso = new Competition("Concurso Ejemplar", "", null, null).save();

        Organizer org = concurso.createOrganizer("El Gran", "Maestro", "organizador", "organizador");

        Team equipo1 = concurso.createTeam("Equipo Ejemplar");
        Participant concursante1 = concurso.createParticipant(equipo1, "Kenny", "Meyer", "kenny", "meyer");
        Participant concursante2 = concurso.createParticipant(equipo1, "Alumno", "Ejemplar 2", "alumno", "ejemplar");

        return concurso;
    }

    @Test
    public void createProblem() {
        Problem problem = new Problem(concurso, "Test problem", "Just a test");

        System.out.println(problem.title);
        System.out.println(problem.description);
        assertNotNull(problem.title);
        assertNotNull(problem.description);
    }
}
