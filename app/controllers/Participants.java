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
        List<Problem> problems = competition.problems;
        String team = participant.team.toString();

        render(participant, participants, competition, team, problems);
    }

    public static void submitWork(Long participant_id,
                                  Long problem_id,
                                  Blob work) {
        // Get the participant and the problem from the database.
        Participant participant = Participant.findById(participant_id);
        Problem problem = Problem.findById(problem_id);

        // Verificar que el usuario puede subir su trabajo y que el concurso
        // está en progreso.
        if (participant.team.competition.endTime != null) {
            // TODO: Visualizar un error en la página web.
        }

        if (participant.canSubmitWork != true) {
            // TODO
        }

        // Actualizar el trabajo
        participant.submitSolution(problem, work);

        // Volver al índice
        Participants.index();
    }

    public static void showSolution(Long solution_id) {
        Solution solution = Solution.findById(solution_id);

        // Render the solution blob if available
        if (solution != null && solution.exists()) {
            response.contentType = solution.blob.type();
            renderBinary(solution.blob.get(), solution.blob.length());
        } else {
            notFound();
        }
    }
}
