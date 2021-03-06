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
        List<Problem> problems = competition.getProblems();
        if (problems == null) {
            problems = null;
        }
        String team = participant.team.toString();

        render(participant, participants, competition, team, problems);
    }

    public static void submitSolution(Long participant_id,
                                      Long problem_id,
                                      Blob solution) {
        validation.required(participant_id);
        validation.required(problem_id);
        validation.required(solution);
        if (validation.hasErrors()) {

            params.flash();
            validation.keep();
            Participants.index();
        }

        // Get the participant and the problem from the database.
        Participant participant = Participant.findById(participant_id);
        Problem problem = Problem.findById(problem_id);

        // Verificar que el usuario puede subir su trabajo y que el concurso
        // está en progreso.
        if (participant.competition.endTime != null) {
            // TODO: Visualizar un error en la página web.
        }

        if (participant.canSubmitSolution != true) {
            // TODO
        }

        // Actualizar el trabajo
        participant.submitSolution(problem, solution);

        // Volver al índice
        Participants.index();
    }

    public static void showSolution(Long participant_id, Long problem_id) {
        Participant participant = Participant.findById(participant_id);

        List<Solution> solutions = participant.getSolutions();

        assert(solutions.isEmpty() == false);
        for (Solution solution: solutions) {
            if (solution.problem.id == problem_id) {
                response.contentType = solution.blob.type();
                renderBinary(solution.blob.get(), solution.blob.length());
                break;
            }
        }
        notFound();
    }
}
