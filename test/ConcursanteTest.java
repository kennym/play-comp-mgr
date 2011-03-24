import org.junit.*;

import play.test.*;
import play.mvc.*;
import play.mvc.Http.*;
import play.db.jpa.Blob;

import models.*;

public class ConcursanteTest extends UnitTest {

    @Test
    public void crearConcursante() {
        // Crear un nuevo concurso
        new Competition("Concurso Ejemplar", "", null, null).save();

        Competition concurso = Competition.all().first();

        assertNotNull(concurso);

        // Crear un Team;
        Team equipo1 = concurso.crearEquipo("Equipo Ejemplar");

        assertNotNull(equipo1);

        // Crear concursantes para equipo1
        Participant concursante1 = concurso.crearConcursante(equipo1, "Kenny", "Meyer", "kenny", "meyer");

        // Crear trabajo para concursante 1
        concursante1.crearTrabajo(new Blob());

        assertNotNull(concursante1.trabajo);
        assertNotNull(concursante1.trabajo.blob);
    }
}
