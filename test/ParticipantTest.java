import org.junit.*;

import java.util.List;

import play.test.*;
import play.mvc.Http.*;
import play.db.jpa.Blob;

import models.*;

public class ParticipantTest extends UnitTest {

    @Test
    public void createCompetition() {
        // Create a nwe competition
        new Competition("Concurso Ejemplar", "", null, null).save();

        Competition concurso = Competition.all().first();

        assertNotNull(concurso);

        // Create a team
        Team equipo1 = concurso.createTeam("Equipo Ejemplar");
        assertNotNull(equipo1);

        // Crear concursantes para equipo1
        Participant concursante1 = concurso.createParticipant(equipo1, "Kenny", "Meyer", "kenny", "meyer");
    }

    /**
     * Specifies expected behavior for submitting solutions to problems.
     */
    @Test
    public void submitSolutions() {
        Competition competition = new Competition("Concurso Ejemplar", "", null, null).save();

        Problem problem_1 = competition.createProblem("hello", "world");
        Problem problem_2 = competition.createProblem("test", "2");
        
        Team team = competition.createTeam("Equipo Ejemplar");

        Participant concursante1 = competition.createParticipant(team, "Kenny", "Meyer", "kenny", "meyer");

        concursante1.submitSolution(problem_1, new Blob());
        assertNotNull(concursante1.solutions.get(0));
        concursante1.submitSolution(problem_2, new Blob());
        assertNotNull(concursante1.solutions.get(1));
    }

    @Test
    public void blockParticipantSubmission() {
        Competition competition = new Competition("Concurso Ejemplar", "", null, null).save();

        Team team = competition.createTeam("Equipo Ejemplar");
        Participant competitor_1 = competition.createParticipant(team, "Kenny", "Meyer", "kenny", "meyer");
        Participant competitor_2 = competition.createParticipant(team, "Kenny", "Meyer", "kenny", "meyer");

        assertFalse(competitor_1.canSubmitSolution);
        assertFalse(competitor_2.canSubmitSolution);
        competitor_1.canSubmitSolution(false);
        competitor_2.canSubmitSolution(false);
        assertTrue(competitor_1.canSubmitSolution);
        assertTrue(competitor_2.canSubmitSolution);


    }
}
