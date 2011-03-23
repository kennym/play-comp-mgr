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
        new Concurso("Concurso Ejemplar", "", null, null).save();

        Concurso concurso = Concurso.all().first();

        assertNotNull(concurso);

        // Crear un Equipo;
        Equipo equipo1 = concurso.crearEquipo("Equipo Ejemplar");

        assertNotNull(equipo1);

        // Crear concursantes para equipo1
        Concursante concursante1 = concurso.crearConcursante(equipo1, "Kenny", "Meyer", "kenny", "meyer");

        // Crear trabajo para concursante 1
        concursante1.crearTrabajo(new Blob());

        assertNotNull(concursante1.trabajo);
        assertNotNull(concursante1.trabajo.blob);
    }
}
