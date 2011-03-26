import org.junit.*;

import play.test.*;
import play.mvc.*;
import play.mvc.Http.*;

import models.*;

public class ApplicationTest extends FunctionalTest {

    @Test
    public void redirectAnonymousAccess() {
        Response response = GET("/organizador/index");
        assertStatus(302, response);
    }
}
