import org.junit.*;
import play.test.*;
import play.mvc.Http.*;

import models.*;

public class ConcursoTest extends FunctionalTest {

    @Test
    public void crearConcurso() {

        new Concurso("Concurso Ejemplar", "", null, null).save();

        Concurso concurso = Concurso.find("byTitulo", "Concurso Ejemplar").first();

        assertNotNull(concurso);
        assertEquals("Concurso Ejemplar", concurso.titulo);
    }
    
}