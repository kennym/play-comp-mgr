package controllers;

import models.*;

public class Security extends Secure.Security {

    static boolean authentify(String usuario, String password) {
        return Usuario.connect(usuario, password) != null;
    }

    static void onDisconnected() {
        Application.index();
    }

    static void onAuthenticated() {
        Admin.index();
    }

    static boolean check(String profile) {
        Usuario user = Usuario.find("byLogin", profile).first();
        if (user.rol == ApplicationRole.getByName("administrador"))
            return true;
        return false;
    }

}
