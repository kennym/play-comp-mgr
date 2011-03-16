import play.test.*;
import play.mvc.*;
import play.mvc.Http.*;

import org.junit.*;
import org.joda.time.DateTime;
import org.joda.time.format.*;

import models.*;
import controllers.*;

public class OrganizadorTest extends FunctionalTest {

    @Test
    public void blockAnonymousAccess() {
        Response response = GET("/organizadores/index");
        assertStatus(403, response);
    }

    @Test
    public void autorizarAccessoDeOrganizador() {
        fail();
    }

    @Test
    public void organizarConcurso() {
        // Crear un nuevo concurso
        new Concurso("Concurso Ejemplar", "", null, null).save();

        Concurso concurso = Concurso.all().first();

        assertNotNull(concurso);

        // Crear un organizador para este concurso
        Organizador org = concurso.crearOrganizador("El Gran", "Maestro", "organizador", "organizador");
        assertNotNull(org);
        assertEquals(org.rol, ApplicationRole.getByName("organizador"));

        // Verificar que el concurso no se inició aún
        assertEquals(concurso.tiempoInicial, null);
        assertEquals(concurso.tiempoFinal, null);

        // Determinar duracion
        DateTimeFormatter dtf = DateTimeFormat.forPattern("HH:mm");
        DateTime duracion = dtf.parseDateTime("00:05"); // 5 minutos

        concurso.iniciar(duracion);
        assertNotNull(concurso.tiempoInicial);

        concurso.parar();
        assertNotNull(concurso.tiempoFinal);
    }
}