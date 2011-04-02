import org.junit.*;

import java.util.List;

import play.test.*;
import play.mvc.Http.*;
import play.db.jpa.Blob;

import models.*;

public class ParticipantTest extends UnitTest {

    @Test
    public void crearConcursante() {
        // Crear un nuevo concurso
        new Competition("Concurso Ejemplar", "", null, null).save();

        Competition concurso = Competition.all().first();

        assertNotNull(concurso);

        // Crear un Team;
        Team equipo1 = concurso.createTeam("Equipo Ejemplar");

        assertNotNull(equipo1);

        // Crear concursantes para equipo1
        Participant concursante1 = concurso.createParticipant(equipo1, "Kenny", "Meyer", "kenny", "meyer");

        // Obtener los problemas
        List<Problem> problems = Problem.all().fetch();
        assertNotNull(problems);

        // Participant creates a solution for problem #1
        concursante1.submitSolution(problems.get(0), new Blob());
        assertNotNull(concursante1.solutions.get(0).blob);
        assertNotNull(concursante1.solutions.get(0).problem);
    }

    @Test
    public void submitSolutions() {

    }
}
