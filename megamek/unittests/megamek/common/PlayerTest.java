package megamek.common;

import megamek.client.ui.swing.util.PlayerColour;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Vector;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class PlayerTest {

    @Test
    public void testGetColorForPlayer() {
        String playerName = "jefke";
        Player player = new Player(0, playerName);
        assertEquals("<B><font color='8080b0'>" + playerName + "</font></B>", player.getColorForPlayer());

        playerName = "Jeanke";
        Player player2 = new Player(1, playerName);
        player2.setColour(PlayerColour.FUCHSIA);
        assertEquals("<B><font color='f000f0'>" + playerName + "</font></B>", player2.getColorForPlayer());
    }

    @Test
    public void testSetTeam() {
        String playerName = "jefke";
        Player player = new Player(0, playerName);
        player.setTeam(0);
        assertEquals(player.getTeam(), 0);
    }

    @Test
    public void testAddMinefield() {
        Coords coords = new Coords(10, 10);
        String playerName = "adam";
        Player player = new Player(0, playerName);
        Minefield mf = Minefield.createMinefield(coords, 0, 1, 1);
        player.addMinefield(mf);

        assertEquals(player.getMinefields().size(), 1);

    }

    @Test
    public void testAddMinefields() {
        Coords coords = new Coords(10, 10);
        String playerName = "adam";
        Player player = new Player(0, playerName);

        Minefield mf = Minefield.createMinefield(coords, 0, 1, 1);
        Minefield mf2 = Minefield.createMinefield(coords, 0, 2, 2);
        Vector<Minefield> minefieldVector = new Vector<>();
        minefieldVector.add(mf);
        minefieldVector.add(mf2);

        player.addMinefields(minefieldVector);
        assertEquals(player.getMinefields().size(), 2);

    }

    @Test
    public void testRemoveMinefield() {
        Coords coords = new Coords(10, 10);
        String playerName = "adam";
        Player player = new Player(0, playerName);

        Minefield mf = Minefield.createMinefield(coords, 0, 1, 1);
        Minefield mf2 = Minefield.createMinefield(coords, 0, 2, 2);
        Vector<Minefield> minefieldVector = new Vector<>();
        minefieldVector.add(mf);
        minefieldVector.add(mf2);

        player.addMinefields(minefieldVector);
        assertEquals(player.getMinefields().size(), 2);
        player.removeMinefield(mf);
        assertEquals(player.getMinefields().size(), 1);

    }

    @Test
    public void testRemoveMinefields() {
        Coords coords = new Coords(10, 10);
        String playerName = "adam";
        Player player = new Player(0, playerName);

        Minefield mf = Minefield.createMinefield(coords, 0, 1, 1);
        Minefield mf2 = Minefield.createMinefield(coords, 0, 2, 2);
        Vector<Minefield> minefieldVector = new Vector<>();
        minefieldVector.add(mf);
        minefieldVector.add(mf2);

        player.addMinefields(minefieldVector);
        assertEquals(player.getMinefields().size(), 2);
        player.removeMinefields();
        assertEquals(player.getMinefields().size(), 0);

    }

    @Test
    public void testContainsMinefield(){
        Coords coords = new Coords(10, 10);
        String playerName = "adam";
        Player player = new Player(0, playerName);

        Minefield mf = Minefield.createMinefield(coords, 0, 1, 1);
        Minefield mf2 = Minefield.createMinefield(coords, 0, 2, 2);
        Vector<Minefield> minefieldVector = new Vector<>();
        minefieldVector.add(mf);
        minefieldVector.add(mf2);

        player.addMinefields(minefieldVector);
        assertTrue(player.containsMinefield(mf));
        assertTrue(player.containsMinefield(mf2));
        assertFalse(player.containsMinefield(Minefield.createMinefield(coords,3,3,3)));
    }

    @Test
    public void testGetElo(){
        String playerName = "etienne";
        Player player = new Player(0, playerName);
        player.setElo(100);
        assertEquals(player.getElo(), (Integer) 100);
    }

}
