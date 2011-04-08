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
        // Get the authenticated user
        User user = User.find("byLogin", connected()).first();

        // Redirect the authenticated user to its correspondent page
        if (user.role == ApplicationRole.getByName("participant")) {
            Participants.index();
        } else if (user.role == ApplicationRole.getByName("judge")) {
            Judges.index();
        } else if (user.role == ApplicationRole.getByName("organizer")) {
            Organizers.index();
        } else {
            Admin.index();
        }
    }

    static boolean check(String profile) {
        User user = User.find("byLogin", profile).first();
        if (user.role == ApplicationRole.getByName("administrator"))
            return true;
        return false;
    }

}
