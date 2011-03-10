import org.junit.*;
import play.test.*;
import play.mvc.*;
import play.mvc.Http.*;

import java.util.*;

import models.*;

public class ConcursoTest extends FunctionalTest {

    @Test
    public void crearConcurso() {

        new Concurso("Concurso Ejemplar", "", new Date()).save();

        Concurso concurso = Concurso.find("byTitulo", "Concurso Ejemplar").first();

        assertNotNull(concurso);
        assertEquals("Concurso Ejemplar", concurso.titulo);
    }
    
}