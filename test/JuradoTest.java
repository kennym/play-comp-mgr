import play.test.*;
import play.mvc.Http.*;

import org.junit.*;
import org.joda.time.DateTime;
import org.joda.time.format.*;

import models.*;

public class JuradoTest extends FunctionalTest {


    @Before
    public void setUp() {
        Fixtures.deleteAll();
        Fixtures.load("data.yml");
    }

    /**
     * El jurado debe poder evaluar el trabajo del concursante.
     */
    @Test
    public void evaluarTrabajo() {

    }
}