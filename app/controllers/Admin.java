package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

@With(Secure.class)
public class Admin extends Controller {

    @Before
    static void setConnectedUser() {
        if (Security.isConnected()) {
            Usuario usua = Usuario.find("byLogin", Security.connected()).first();
            renderArgs.put("usuario", usua.login);
        }
    }

    public static void index() {
        render();
    }
}