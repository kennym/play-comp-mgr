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
            User user = User.find("byLogin", Security.connected()).first();
            renderArgs.put("user", user);
        }
    }

    /**
     * Verificar que un Ussser está conectado y devolverlo.
     */
    static User connected() {
        if (renderArgs.get("user") != null) {
            return renderArgs.get("user", User.class);
        }
        String username = session.get("user");
        if (username != null) {
            return User.find("byLogin", username).first();
        }
        return null;
    }


    public static void index() {
        render();
    }
};
