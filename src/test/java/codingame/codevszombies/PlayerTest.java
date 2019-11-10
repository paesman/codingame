package codingame.codevszombies;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;

/**
 * Created by pascal on 04.02.16.
 */
public class PlayerTest {

    protected static List<Player.Human> victims;
    protected static List<Player.Zombie> zombies;

    protected static Player.Human ash;
    protected static Player.Human h1;
    protected static Player.Human h2;

    protected static Player.Zombie z1;
    protected static Player.Zombie z2;

    @BeforeClass
    public static void initZombies() {
        ash = new Player.Human(101, 5000, 0);
        h1 = new Player.Human(1, 950, 6000);
        h2 = new Player.Human(2, 8000, 6100);

        victims = new ArrayList<>();
        victims.add(h1);
        victims.add(h2);

        z1 = new Player.Zombie(1, 3100, 7000, 0, 0, victims);
        z2 = new Player.Zombie(2, 11500, 7100, 0, 0, victims);

        zombies = new ArrayList<>();
        zombies.add(z1);
        zombies.add(z2);
    }

    @Test
    public void testChooseZombieToAttach() {
        zombies.sort((Player.Zombie z1, Player.Zombie z2) -> Player.compare(z1.getClosestVictim().getDistanceToZombie(), z2.getClosestVictim().getDistanceToZombie()));
        assertEquals(z1, Player.chooseZombieToAttack(ash, zombies));
    }

    @Test
    public void testCanAshSaveHumanBeforeZombie() {
        assertEquals(true, Player.canAshSaveHumanBeforeZombie(ash, z1));
        assertEquals(true, Player.canAshSaveHumanBeforeZombie(ash, z2));
    }

    @Test
    public void testComparatorAsExpected() {
        zombies.sort((Player.Zombie z1, Player.Zombie z2) -> Player.compare(z1.getClosestVictim().getDistanceToZombie(), z2.getClosestVictim().getDistanceToZombie()));

        assertEquals(h1, z1.getClosestVictim());
        assertEquals(h2, z2.getClosestVictim());
    }

    @Test
    public void testCalculateClosestVictim() {
        zombies.forEach(zombie -> zombie.calculateClosestVictim());

        assertEquals(h1, z1.getClosestVictim());
        assertEquals(h2, z2.getClosestVictim());
    }

    @Test
    public void testCalculateDistanceToHuman() {
        int x = h1.getX() - z1.getX();
        int y = h1.getY() - z1.getY();
        int z1DistanceV1 = (int) Math.floor(Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)));
        System.out.println("Distance z" + z1.getId() + " to h" + h1.getId() + ": " + z1DistanceV1);

        x = h2.getX() - z1.getX();
        y = h2.getY() - z1.getY();
        int z1DistanceV2 = (int) Math.floor(Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)));
        System.out.println("Distance z" + z1.getId() + " to h" + h2.getId() + ": " + z1DistanceV2);

        x = h1.getX() - z2.getX();
        y = h1.getY() - z2.getY();
        int z2DistanceV1 = (int) Math.floor(Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)));
        System.out.println("Distance z" + z2.getId() + " to h" + h1.getId() + ": " + z2DistanceV1);

        x = h2.getX() - z2.getX();
        y = h2.getY() - z2.getY();
        int z2DistanceV2 = (int) Math.floor(Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)));
        System.out.println("Distance z" + z2.getId() + " to h" + h2.getId() + ": " + z2DistanceV2);

        assertEquals(z1DistanceV1, z1.getVictims().get(0).getDistanceToZombie());
        assertEquals(z1DistanceV2, z1.getVictims().get(1).getDistanceToZombie());
        assertEquals(z2DistanceV1, z2.getVictims().get(1).getDistanceToZombie());
        assertEquals(z2DistanceV2, z2.getVictims().get(0).getDistanceToZombie());

    }
}
