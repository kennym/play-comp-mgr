import org.junit.*;

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

        // Crear trabajo para concursante 1
        Blob file = new Blob();
        Work work = new Work(concursante1, file);
        assertNotNull(work.participant);
        assertNotNull(work.file);
    }
}
