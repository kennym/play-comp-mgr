import org.junit.*;

import play.test.*;
import play.mvc.*;
import play.mvc.Http.*;

import models.*;

public class ApplicationTest extends FunctionalTest {

    @Test
    public void blockAnonymousAccess() {
        Response response = GET("/organizadores/index");
        assertStatus(403, response);
    }

    @Test
    public void autorizarAccessoDeOrganizador() {
        fail();
    }

}
