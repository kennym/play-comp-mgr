package controllers;

import play.mvc.*;

import models.*;

public class Security extends Secure.Security {

    static boolean authentify(String user, String password) {
        return User.connect(user, password) != null;
    }

    static void onDisconnected() {
        Application.index();
    }

    static void onAuthenticated() {
        Admin.index();
    }

    static boolean check(String profile) {
        User user = User.find("byLogin", profile).first();
        if (user.role == ApplicationRole.getByName("administrator"))
            return true;
        return false;
    }

}
