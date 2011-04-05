package controllers;

import java.util.List;

import play.mvc.*;

import models.*;

/**
 * Class Name
 *
 * Class description - Explain why you need it and what it does.
 *
 * @author Kenny Meyer <knny.myer@gmail.com>
 */
@With(Secure.class)
public class Judges extends Application {
    public static void index() {
        User user = connected();

        try {
            if (user.role != ApplicationRole.getByName("judge"))
                forbidden("No eres un jurado.");
        } catch (NullPointerException e) {
            try {
                Secure.login();
            } catch (Throwable ex) {
                ex.printStackTrace();
            }
        }

        Judge judge = Judge.find("byLogin", user.login).first();
        Competition competition = judge.competition;
        List<Participant> participants = Participant.all().fetch();

        render(judge, competition, participants);
    }

    /**
     * Evaluar concursante por su trabajo subido.
     *
     * @param id Identificador del concursante
     */
    public static void evaluateWork(Long id) {
        // TODO
    }
}