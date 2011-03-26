import play.test.*;
import play.mvc.*;
import play.mvc.Http.*;

import org.junit.*;
import org.joda.time.DateTime;
import org.joda.time.format.*;

import models.*;

public class OrganizerTest extends UnitTest {
    @Test
    public void organizarConcurso() {
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
}