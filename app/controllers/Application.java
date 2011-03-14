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
            Usuario usuario = Usuario.find("byLogin", Security.connected()).first();
            renderArgs.put("usuario", usuario);
        }
    }

    /**
     * Verificar que un Usuario está conectado y devolverlo.
     */
    static Usuario connected() {
        if (renderArgs.get("usuario") != null) {
            return renderArgs.get("usuario", Usuario.class);
        }
        String username = session.get("usuario");
        if (username != null) {
            return Usuario.find("byLogin", username).first();
        }
        return null;
    }


    public static void index() {
        render();
    }
};
