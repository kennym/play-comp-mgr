package controllers;

import play.*;
import play.mvc.*;

import models.*;

public class Application extends CRUD {

    /**
     *
     */
    @Before
    static void checkConnected() {
        if (Security.isConnected()) {
            User usuario = User.find("byLogin", Security.connected()).first();
            renderArgs.put("usuario", usuario);
        }
    }

    /**
     * Verificar que un Ussser está conectado y devolverlo.
     */
    static User connected() {
        if (renderArgs.get("usuario") != null) {
            return renderArgs.get("usuario", User.class);
        }
        String username = session.get("usuario");
        if (username != null) {
            return User.find("byLogin", username).first();
        }
        return null;
    }


    public static void index() {
        render();
    }
};
