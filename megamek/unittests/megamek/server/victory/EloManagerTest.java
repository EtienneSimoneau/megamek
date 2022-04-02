package megamek.server.victory;

import megamek.common.Player;
import megamek.common.Team;
import megamek.server.EloManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class EloManagerTest {

    @Test
    public void playerAddedToDictionary_WithDefaultElo() {
        EloManager eloManager = new EloManager();

        Player etien = new Player(0, "etien");
        Player colin = new Player(1, "colin");

        eloManager.addPlayer(etien);
        eloManager.addPlayer(colin);

        assertEquals(800, etien.getElo().intValue());
        assertEquals(800, colin.getElo().intValue());
    }

    @Test
    public void GameSimulation_Team1Wins_Team1GainEloAndTeam2LoseElo() {
        EloManager eloManager = new EloManager();

        Player etien = new Player(0, "etien");
        Player colin = new Player(1, "colin");

        etien.setIp("192.168.2.1");
        colin.setIp("192.168.2.2");

        eloManager.addPlayer(etien);
        eloManager.addPlayer(colin);

        Team etienTeam = new Team(0);
        Team colinTeam = new Team(1);

        etienTeam.addPlayer(etien);
        colinTeam.addPlayer(colin);

        Team[] teams = new Team[] { etienTeam, colinTeam };

        eloManager.updateRankings(teams, 1);

        assertEquals(816, etien.getElo().intValue());
        assertEquals(784, colin.getElo().intValue());
    }

    @Test
    public void GameSimulation_Team1WinsThenTeam2Wins_EloStaysApproximatelyTheSame() {
        EloManager eloManager = new EloManager();

        Player etien = new Player(0, "etien");
        Player colin = new Player(1, "colin");

        etien.setIp("192.168.2.1");
        colin.setIp("192.168.2.2");

        eloManager.addPlayer(etien);
        eloManager.addPlayer(colin);

        Team etienTeam = new Team(0);
        Team colinTeam = new Team(1);

        etienTeam.addPlayer(etien);
        colinTeam.addPlayer(colin);

        Team[] teams = new Team[] { etienTeam, colinTeam };

        eloManager.updateRankings(teams, 1);
        eloManager.updateRankings(teams, 2);

        assertEquals(801, etien.getElo().intValue());
        assertEquals(799, colin.getElo().intValue());
    }

    @Test
    public void Player1Wins_LeaveTheGame_KeepsTheEloOnReconnect() {
        EloManager eloManager = new EloManager();

        Player etien = new Player(0, "etien");
        Player colin = new Player(1, "colin");

        etien.setIp("192.168.2.1");
        colin.setIp("192.168.2.2");

        eloManager.addPlayer(etien);
        eloManager.addPlayer(colin);

        Team etienTeam = new Team(0);
        Team colinTeam = new Team(1);

        etienTeam.addPlayer(etien);
        colinTeam.addPlayer(colin);

        Team[] teams = new Team[] { etienTeam, colinTeam };

        eloManager.updateRankings(teams, 1);

        Player etienReconnect = new Player(0, "etien");
        etienReconnect.setIp("192.168.2.1");
        eloManager.addPlayer(etienReconnect);

        assertEquals(816, etienReconnect.getElo().intValue());
    }
}
