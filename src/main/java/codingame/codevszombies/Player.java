package codingame.codevszombies;

/**
 * Created by pascal on 04.02.16.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Save humans, destroy zombies!
 **/
class Player {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);

        //Strategy
        //1. Attack the zombie which is closest to a human and reachable by ash
        // 1.1 Ignore zombie if it's already reached
        // 1.2 Ignore zombie if it's closest victim is ash

        boolean goToHuman = true;
        Human humanToProtect = null;

        // game loop
        while (true) {
            int x = in.nextInt();
            int y = in.nextInt();

            Human ash = new Human(101, x, y);

            List<Human> humans = new ArrayList<>();

            int humanCount = in.nextInt();
            for (int i = 0; i < humanCount; i++) {
                int humanId = in.nextInt();
                int humanX = in.nextInt();
                int humanY = in.nextInt();

                humans.add(i, new Human(humanId, humanX, humanY));
            }
            humans.add(humans.size() - 1, ash);

            List<Zombie> zombies = new ArrayList<>();

            int zombieCount = in.nextInt();
            for (int i = 0; i < zombieCount; i++) {
                int zombieId = in.nextInt();
                int zombieX = in.nextInt();
                int zombieY = in.nextInt();
                int zombieXNext = in.nextInt();
                int zombieYNext = in.nextInt();

                zombies.add(i, new Zombie(zombieId, zombieX, zombieY, zombieXNext, zombieYNext, humans));
            }

            zombies.sort((Zombie z1, Zombie z2) -> compare(z1.getClosestVictim().getDistanceToZombie(), z2.getClosestVictim().getDistanceToZombie()));

            Zombie zombieToAttack = chooseZombieToAttack(ash, zombies);

            System.out.println(zombieToAttack.getNextX() + " "  + zombieToAttack.getY());

            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");

            //System.out.println("0 0"); // Your destination coordinates
        }
    }

    public static Zombie chooseZombieToAttack(Human ash, List<Zombie> zombies) {
        return zombies.stream().filter(zombie -> zombie.getClosestVictim().getDistanceToZombie() != 0 && zombie.getClosestVictim().getId() != 101 && canAshSaveHumanBeforeZombie(ash, zombie))
                .findFirst().orElse(zombies.get(0));
    }

    public static int calculateDistance(int toX, int toY, int fromX, int fromY) {
        int x = toX - fromX;
        int y = toY - fromY;
        return (int) Math.floor(Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)));
    }

    public static int compare(int dist1, int dist2) {
        if (dist1 < dist2) {
            return -1;
        } else if (dist1 > dist2) {
            return 1;
        } else {
            return 0;
        }
    }

    public static boolean canAshSaveHumanBeforeZombie(Human ash, Zombie zombie) {
        int distanceToAsh = calculateDistance(zombie.getClosestVictim().getX(), zombie.getClosestVictim().getY(), ash.getX(), ash.getY());
        int turnsNeededForAsh = (distanceToAsh - 2000) / 1000;
        int turnsNeededForZombie = zombie.getClosestVictim().getDistanceToZombie() / 400;
        return turnsNeededForAsh <= turnsNeededForZombie;
    }

    static class Zombie {
        private int id;
        private int x;
        private int y;
        private int nextX;
        private int nextY;
        private List<Human> victims;
        private Human closestVictim;

        public Zombie(int id, int x, int y, int nextX, int nextY, List<Human> victims) {
            this.id = id;
            this.x = x;
            this.y = y;
            this.nextX = nextX;
            this.nextY = nextY;
            this.victims = new ArrayList<>();
            victims.forEach(victim -> this.victims.add(new Human(victim)));
            calculateDistanceToHuman();
            this.closestVictim = calculateClosestVictim();
        }

        public int getId() {
            return id;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getNextX() {
            return nextX;
        }

        public int getNextY() {
            return nextY;
        }

        public List<Human> getVictims() {
            return victims;
        }

        public Human getClosestVictim() {
            return closestVictim;
        }

        public Human calculateClosestVictim() {
            victims.sort((Human h1, Human h2) -> compare(h1.getDistanceToZombie(), h2.getDistanceToZombie()));
            return victims.get(0);
        }

        public void calculateDistanceToHuman() {
            victims.stream().forEach(victim -> victim.setDistanceToZombie(calculateDistance(victim.getX(), victim.getY(), this.getX(),this.getY())));
        }

        @Override
        public String toString() {
            return "Zombie{" +
                    "id=" + id +
                    ", x=" + x +
                    ", y=" + y +
                    ", nextX=" + nextX +
                    ", nextY=" + nextY +
                    '}';
        }
    }

    static class Human {
        private int id;
        private int x;
        private int y;
        private int distanceToZombie;

        public Human(int id, int x, int y) {
            this.id = id;
            this.x = x;
            this.y = y;
        }

        public Human(Human human) {
            this.id = human.getId();
            this.x = human.getX();
            this.y = human.getY();
        }

        public int getId() {
            return id;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getDistanceToZombie() {
            return distanceToZombie;
        }

        public void setDistanceToZombie(int distanceToZombie) {
            this.distanceToZombie = distanceToZombie;
        }

        @Override
        public String toString() {
            return "Human{" +
                    "id=" + id +
                    ", x=" + x +
                    ", y=" + y +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Human human = (Human) o;

            return id == human.id;
        }

        @Override
        public int hashCode() {
            return id;
        }
    }
}
