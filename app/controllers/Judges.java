package controllers;

import java.util.List;

import play.mvc.*;

import models.*;


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
        List<Participant> participants = competition.getParticipants();
        List<Problem> problems = competition.getProblems();

        render(judge, competition, participants, problems);
    }

    /**
     * Evaluate the submitted solution with the specified id.
     *
     * @param participant_id ID of the solution
     * @param solution_id ID of the solution
     */
    public static void evaluateSolution(Long participant_id,
                                        Long solution_id,
                                        Long problem_id) {
        // Get the solution
        Solution solution = Solution.findById(solution_id);
        // Get the problem to the solution
        Problem problem = Problem.findById(problem_id);
        // Get the participant
        Participant participant = Participant.findById(participant_id);

        render(participant, solution, problem);
    }
}