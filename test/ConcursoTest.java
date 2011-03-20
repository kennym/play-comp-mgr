import org.junit.*;
import play.test.*;
import play.mvc.Http.*;

import models.*;

public class ConcursoTest extends UnitTest {

    @Test
    public void crearConcurso() {
        // Create a new contest
        new Concurso("Concurso Ejemplar", "", null, null).save();

        Concurso concurso = Concurso.all().first();

        assertNotNull(concurso);

        // Create an organizer for this contest
        Organizador org = concurso.crearOrganizador("El Gran", "Maestro", "organizador", "organizador");
        assertNotNull(org);
        assertEquals(org.rol, ApplicationRole.getByName("organizador"));

        // Create a team
        Equipo equipo1 = concurso.crearEquipo("Equipo Ejemplar");

        assertNotNull(equipo1);

        // Create team members
        Concursante concursante1 = concurso.crearConcursante(equipo1, "Kenny", "Meyer", "kenny", "meyer");
        Concursante concursante2 = concurso.crearConcursante(equipo1, "Alumno", "Ejemplar 2", "alumno", "ejemplar");

        assertNotNull(concursante1);
        assertNotNull(concursante2);

        // Create another team
        Equipo equipo2 = concurso.crearEquipo("Equipo Ejemplar 2");
        assertNotNull(equipo2);

        // Create team members for the team
        Concursante concursante3 = concurso.crearConcursante(equipo2, "Alumno", "Ejemplar 3", "test", "test");
        assertNotNull(concursante3);

        // Create a jury
        Jurado jurado = concurso.crearJurado("Juan", "Perez", "jurado", "jurado");
        assertNotNull(jurado);

        // Verify that we have some participants
        assertFalse(concurso.concursantes.isEmpty());
    }
}