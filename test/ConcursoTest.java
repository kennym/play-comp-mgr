import org.junit.*;
import play.test.*;
import play.mvc.Http.*;

import models.*;

public class ConcursoTest extends UnitTest {

    @Test
    public void crearConcurso() {
        // Create a new contest
        Competition contest = new Competition("Concurso Ejemplar", "", null, null).save();

        assertNotNull(contest);

        // Create an organizer for this contest
        Organizer org = contest.crearOrganizador("El Gran", "Maestro", "organizador", "organizador");
        assertNotNull(org);
        assertEquals(org.rol, ApplicationRole.getByName("organizador"));

        // Create a team
        Team equipo1 = contest.crearEquipo("Equipo Ejemplar");

        assertNotNull(equipo1);

        // Create team members
        Participant concursante1 = contest.crearConcursante(equipo1, "Kenny", "Meyer", "kenny", "meyer");
        Participant concursante2 = contest.crearConcursante(equipo1, "Alumno", "Ejemplar 2", "alumno", "ejemplar");

        assertNotNull(concursante1);
        assertNotNull(concursante2);

        // Create another team
        Team equipo2 = contest.crearEquipo("Equipo Ejemplar 2");
        assertNotNull(equipo2);

        // Create team members for the team
        Participant concursante3 = contest.crearConcursante(equipo2, "Alumno", "Ejemplar 3", "test", "test");
        assertNotNull(concursante3);

        // Create a jury
        Jury jurado = contest.crearJurado("Juan", "Perez", "jurado", "jurado");
        assertNotNull(jurado);

    }
}