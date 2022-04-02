package megamek.server;

import megamek.common.Player;
import megamek.common.Team;

import java.util.*;

public class EloManager {
    private final int INITIAL_ELO = 800;

    // The Key is a composite key of the IP address of the player and if it's a bot.
    // The Value is the elo.
    private final Map<String, Integer> eloRanking = new HashMap<>();

    public void addPlayer(Player player) {
        String key = createCompositeKey(player.getIp(), player.isBot());
        Integer elo = eloRanking.get(key);

        if (elo == null)
            elo = INITIAL_ELO;

        player.setElo(elo);
    }

    public void updateRankings(Team[] teams, int winningTeam) {
        if (teams.length > 2 || winningTeam > 2)
            return; // NOT SUPPORTED, ONLY SUPPORTED FOR 2 TEAMS.

        int eloGain = calculateEloGain(teams);

        if (winningTeam == 1) {
            updateTeamRanking(teams[0], eloGain);
            updateTeamRanking(teams[1], -eloGain);
        }

        if (winningTeam == 2) {
            updateTeamRanking(teams[0], -eloGain);
            updateTeamRanking(teams[1], eloGain);
        }
    }

    private void updateTeamRanking(Team team, int elo) {
        for (Player player: Collections.list(team.getPlayers())) {
            String key = createCompositeKey(player.getIp(), player.isBot());
            int newElo = player.getElo() + elo;

            player.setElo(newElo);
            eloRanking.put(key, newElo);
        }
    }

    private Integer calculateEloGain(Team[] teams) {
        if (teams.length > 2)
            return 0; // NOT SUPPORTED, ONLY SUPPORTED FOR 2 TEAMS.

        int team1AverageElo = teams[0].getTeamAvgElo();
        int team2AverageElo = teams[1].getTeamAvgElo();

        // calculate expected outcome
        double exponent = (double) (team2AverageElo - team1AverageElo) / 400;
        double expectedOutcome = (1 / (1 + (Math.pow(10, exponent))));

        // K-factor
        int K = 32;

        int eloGain = (int) Math.round(K * (1 - expectedOutcome));

        return eloGain;
    }

    private String createCompositeKey(String ip, boolean isBot) {
        return isBot ? ip + "_true" : ip + "_false";
    }
}
