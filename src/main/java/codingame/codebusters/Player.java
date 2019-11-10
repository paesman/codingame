package codingame.codebusters;

import java.util.*;

/**
 * Send your busters out into the fog to trap ghosts and bring them home!
 **/
class Player {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int bustersPerPlayer = in.nextInt(); // the amount of busters you control
        int ghostCount = in.nextInt(); // the amount of ghosts on the map
        int myTeamId = in.nextInt(); // if this is 0, your base is on the top left of the map,
                                     // if it is one, on the bottom right

        BusterManager busterManager = new BusterManager(myTeamId, bustersPerPlayer);

        // game loop
        while (true) {
            int entities = in.nextInt(); // the number of busters and ghosts visible to you
            for (int i = 0; i < entities; i++) {
                int entityId = in.nextInt(); // buster id or ghost id
                int x = in.nextInt();
                int y = in.nextInt(); // position of this buster / ghost
                int entityType = in.nextInt(); // the team id if it is a buster, -1 if it is a ghost.
                int state = in.nextInt(); // For busters: 0=idle, 1=carrying a ghost.
                int value = in.nextInt(); // For busters: Ghost id being carried.
                                          // For ghosts: number of busters attempting to trap this ghost.
                //System.err.println("id: " + entityId + ", entityType: " + entityType + ", x/y= " + x + "/" + y);

                if (entityType == -1) {
                    Ghost ghost = new Ghost(entityId, x, y);
                    busterManager.addGhost(ghost);
                } else {
                    if (entityType == myTeamId) {
                        Buster buster = new Buster(entityId, x, y, state);
                        busterManager.addBuster(buster);
                    }
                }
            }
            for (int i = 0; i < bustersPerPlayer; i++) {

                // Write an action using System.out.println()
                // To debug: System.err.println("Debug messages...");

                //System.out.println("MOVE 8000 4500"); // MOVE x y | BUST id | RELEASE
                String move = busterManager.getNextBusterMove(i);
                System.err.println("Id: " + i + ", " + move);
                System.out.println(move);
            }
        }
    }

    static class BusterManager {
        private static final int MAX_X = 16000;
        private static final int MAX_Y = 9000;

        private int baseX, baseY;
        private int teamId;
        private int firstBusterId;
        private Map<Integer, Buster> busters;
        private Map<Integer, Coordinate> busterCoordinates;

        private Random random = new Random();

        public BusterManager(int teamId, int bustersPerPlayer) {
            this.busters = new HashMap<>(bustersPerPlayer);
            this.busterCoordinates = new HashMap<>(bustersPerPlayer);
            this.teamId = teamId;
            switch (teamId) {
                case 0:
                    this.baseX = 0;
                    this.baseY = 0;
                    this.firstBusterId = 0;
                    break;
                case 1:
                    this.baseX = 16000;
                    this.baseY = 9000;
                    this.firstBusterId = bustersPerPlayer;
                    break;
            }
            for (int i = firstBusterId; i < firstBusterId + bustersPerPlayer; i++) {
                System.err.println("Id: " + i);
                busterCoordinates.put(i, getCoordinate(i, teamId));
            }
        }

        public void addBuster(Buster buster) {
            Coordinate coordinate = this.busterCoordinates.get(buster.getId());
            buster.setTargetX(coordinate.getX());
            buster.setTargetY(coordinate.getY());
            this.busters.put(buster.getId(), buster);
        }

        public void addGhost(Ghost ghost) {
            int busterId = getNearestBuster(this.busters.values(), ghost);
            this.busters.get(busterId).setClosestGhost(ghost);
        }

        public String getNextBusterMove(int index) {
            index = index + this.firstBusterId;
            Buster buster = this.busters.get(index);
            if (buster.getState() == 1) {
                // has ghost -> return to base
                double distance = distanceBetweenPoints(buster.getX(), buster.getY(), this.baseX, this.baseY);
                if (distance <= 1600) {
                    return "RELEASE";
                } else {
                    return getNextMove(this.baseX, this.baseY);
                }
            } else {
                // Try to bust ghost
                if (buster.getClosestGhost() != null) {
                    double distance = buster.getClosestGhost().getDistanceToNearestBuster();
                    if (distance <= 1760 && distance > 900) {
                        return "BUST " + buster.getClosestGhost().getId();
                    } else {
                        return getNextMove(buster.getClosestGhost().getX(), buster.getClosestGhost().getY());
                    }
                } else {
                    double distanceToTarget = distanceBetweenPoints(buster.getX(), buster.getY(),
                            buster.getTargetX(), buster.getTargetY());
                    // If reached the target -> go back to base
                    if (distanceToTarget <= 2000) {
                        Coordinate coordinate = this.busterCoordinates.get(index);
                        if (coordinate.getX() != this.baseX && coordinate.getY() != this.baseY) {
                            this.busterCoordinates.put(index, new BaseCoordinate(this.baseX, this.baseY));
                            buster.setTargetX(this.baseX);
                            buster.setTargetY(this.baseY);
                        } else {
                            busterCoordinates.put(index, getCoordinate(index, teamId));
                        }
                    }
                    return getNextMove(buster.getTargetX(), buster.getTargetY());
                }
            }
        }

        private Coordinate getCoordinate(int index, int teamId) {
            if ((index + 1) % 2 == 0) {
                if (teamId == 0) {
                    return new Coordinate(random.nextInt(MAX_X) + 1, MAX_Y);
                } else {
                    return new Coordinate(random.nextInt(MAX_X) + 1, 0);
                }
            } else {
                if (teamId == 0) {
                    return new Coordinate(MAX_X, random.nextInt(MAX_Y) + 1);
                } else {
                    return new Coordinate(0, random.nextInt(MAX_Y) + 1);
                }
            }
        }
    }

    static class Buster {
        private int id;
        private int x, y;
        private int targetX, targetY;
        private int state;
        private Ghost closestGhost;

        public Buster(int id, int x, int y, int state) {
            this.id = id;
            this.x = x;
            this.y = y;
            this.state = state;
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

        public int getTargetX() {
            return targetX;
        }

        public void setTargetX(int targetX) {
            this.targetX = targetX;
        }

        public int getTargetY() {
            return targetY;
        }

        public void setTargetY(int targetY) {
            this.targetY = targetY;
        }

        public int getState() {
            return state;
        }

        public Ghost getClosestGhost() {
            return closestGhost;
        }

        public void setClosestGhost(Ghost closestGhost) {
            this.closestGhost = closestGhost;
        }
    }

    static class Ghost {
        private int id;
        private int x, y;
        private double distanceToNearestBuster;

        public Ghost(int id, int x, int y) {
            this.id = id;
            this.x = x;
            this.y = y;
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

        public double getDistanceToNearestBuster() {
            return distanceToNearestBuster;
        }

        public void setDistanceToNearestBuster(double distanceToNearestBuster) {
            this.distanceToNearestBuster = distanceToNearestBuster;
        }
    }

    static class Coordinate {
        private int x, y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }
    }

    static class BaseCoordinate extends Coordinate {

        public BaseCoordinate(int baseX, int baseY) {
            super(baseX, baseY);
        }
    }

    public static String getNextMove(int x, int y) {
        return "MOVE " + x + " " + y;
    }

    static int getNearestBuster(Collection<Buster> busters, Ghost ghost) {
        double distance = 1_000_000d;
        Buster nearestBuster = null;
        for (Buster buster : busters) {
            double newDistance = distanceBetweenPoints(buster.getX(), buster.getY(), ghost.getX(), ghost.getY());
            if (newDistance < distance) {
                distance = newDistance;
                nearestBuster = buster;
            }
        }
        ghost.setDistanceToNearestBuster(distance);
        return nearestBuster.getId();
    }

    static double distanceBetweenPoints(int x1, int y1, int x2, int y2) {
        int x = x2 - x1;
        int y = y2 - y1;
        return Math.floor(Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)));
    }
}
