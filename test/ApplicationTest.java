import org.junit.*;
import play.test.*;
import play.mvc.*;
import play.mvc.Http.*;
import org.joda.time.DateTime;
import org.joda.time.format.*;
import models.*;
import java.util.Date;

public class ApplicationTest extends FunctionalTest {

    @Test
    public void crearConcurso() {
        // Crear un nuevo concurso
        new Concurso("Concurso Ejemplar", "", null, null).save();

        Concurso concurso = Concurso.all().first();

        assertNotNull(concurso);

        // Crear un organizador para este concurso
        Organizador org = concurso.crearOrganizador("El Gran", "Maestro", "organizador", "organizador");
        assertNotNull(org);
        assertEquals(org.rol, ApplicationRole.getByName("organizador"));

        // Crear un Equipo
        Equipo equipo1 = concurso.crearEquipo("Equipo Ejemplar");

        assertNotNull(equipo1);

        // Crear concursantes para equipo1
        Concursante concursante1 = equipo1.crearConcursante("Kenny", "Meyer", "kenny", "meyer");
        Concursante concursante2 = equipo1.crearConcursante("Alumno", "Ejemplar 2", "alumno", "ejemplar");

        assertNotNull(concursante1);
        assertNotNull(concursante2);

        // Crear otro equipo
        Equipo equipo2 = concurso.crearEquipo("Equipo Ejemplar 2");
        assertNotNull(equipo2);

        // Crear concursante para equipo2
        Concursante concursante3 = equipo2.crearConcursante("Alumno", "Ejemplar 3", "test", "test");
        assertNotNull(concursante3);

        // Crear jurado para el concurso
        Jurado jurado = concurso.crearJurado("Juan", "Perez", "jurado", "jurado");
        assertNotNull(jurado);
    }

}
