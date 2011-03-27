package controllers;


import java.util.List;

import play.db.jpa.Blob;

import models.*;


public class Participants extends Application {

    public static void index() {
        User user = connected();

        try {
            if (user.role != ApplicationRole.getByName("participant"))
                forbidden("No eres un concursante.");
        } catch (NullPointerException e) {
            try {
                Secure.login();
            } catch (Throwable ex) {
                ex.printStackTrace();
            }
        }

        Participant participant = Participant.find("byLogin", user.login).first();

        Competition competition = participant.competition;
        List<Participant> participants = Participant.find("byCompetitionLike", competition).fetch();
        String team = participant.team.toString();
        Problem problem = competition.problem;

        render(participant, participants, competition, team, problem);
    }

    public static void submitWork(Long id, Blob work) {
        // Obtener concursante de la base de datos
        Participant participant = Participant.findById(id);

        // Verificar que el usuario puede subir su trabajo y que el concurso
        // está en progreso.
        if (participant.team.competition.endTime != null) {
            // TODO: Visualizar un error en la página web.
        }

        if (participant.canSubmitWork != true) {
            // TODO
        }

        // Actualizar el trabajo
        participant.createWork(work);

        // Volver al índice
        Participants.index();
    }

    public static void showWork(Long id) {
        // Obtener concursante de la base de datos
        Participant participant = Participant.findById(id);

        // Rendir la URI del trabajo si existe.
        if (participant.work != null && participant.work.exists()) {
            response.contentType = participant.work.blob.type();
            renderBinary(participant.work.blob.get(), participant.work.blob.length());
        } else {
            notFound();
        }
    }
}
