import org.junit.*;
import org.joda.time.DateTime;

import play.test.*;
import play.mvc.Http.*;

import models.*;

public class CompetitionTest extends UnitTest {

    @Test
    public void crearConcurso() {
        // Create a new contest
        Competition contest = new Competition("Concurso Ejemplar", "", null, null).save();
        assertNotNull(contest);

        // Create an organizer for this contest
        Organizer org = contest.createOrganizer("El Gran", "Maestro", "organizador", "organizador");
        assertNotNull(org);
        assertEquals(org.role, ApplicationRole.getByName("organizer"));

        // Create a team
        Team equipo1 = contest.createTeam("Equipo Ejemplar");

        assertNotNull(equipo1);

        // Create team members
        Participant concursante1 = contest.createParticipant(equipo1, "Kenny", "Meyer", "kenny", "meyer");
        Participant concursante2 = contest.createParticipant(equipo1, "Alumno", "Ejemplar 2", "alumno", "ejemplar");

        assertNotNull(concursante1);
        assertNotNull(concursante2);

        // Create another team
        Team equipo2 = contest.createTeam("Equipo Ejemplar 2");
        assertNotNull(equipo2);

        // Create team members for the team
        Participant concursante3 = contest.createParticipant(equipo2, "Alumno", "Ejemplar 3", "test", "test");
        assertNotNull(concursante3);

        // Create a jury
        Judge jurado = contest.createJudge("Juan", "Perez", "jurado", "jurado");
        assertNotNull(jurado);

    }

    @Test
    public void isRunning() {
        Competition competition = new Competition("Test", "", null, null).save();

        assertFalse(competition.isRunning());

        competition.start(new DateTime());

        assertTrue(competition.isRunning());

        competition.stop();

        assertFalse(competition.isRunning());
    }
}