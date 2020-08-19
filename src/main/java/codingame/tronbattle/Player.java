package codingame.tronbattle;

import java.util.*;
import java.io.*;
import java.math.*;

import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/

class Player {

    static final int LEFT = 0;
    static final int UP = 1;
    static final int RIGHT = 2;
    static final int DOWN = 3;
    static int dir = LEFT;
    static int x, y;
    static int[][] positions = new int[30][20];

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);

        // game loop
        while (true) {
            int N = in.nextInt(); // total number of players (2 to 4).
            int P = in.nextInt(); // your player number (0 to 3).

            //System.err.println("Number of players: " + N);
            //System.err.println("Player Number: " + P);

            for (int i = 0; i < N; i++) {
                int X0 = in.nextInt(); // starting X coordinate of lightcycle (or -1)
                int Y0 = in.nextInt(); // starting Y coordinate of lightcycle (or -1)
                int X1 = in.nextInt(); // starting X coordinate of lightcycle (can be the same as X0 if you play before this player)
                int Y1 = in.nextInt(); // starting Y coordinate of lightcycle (can be the same as Y0 if you play before this player)

                positions[X1][Y1] = 1;
                positions[X0][Y0] = 1;

                if (i == P) {
                    x = X1;
                    y = Y1;
                }
            }

            if (!lookAhead(dir, x, y, 1)) {

                dir = chooseDirection(dir, x, y);

                if (!lookAhead(dir, x, y, 1)) {
                    dir = turnLeft(dir);
                    dir = turnLeft(dir);
                }

            }

            switch(dir) {
                case LEFT:
                    System.out.println("LEFT");
                    break;
                case UP:
                    System.out.println("UP");
                    break;
                case RIGHT:
                    System.out.println("RIGHT");
                    break;
                case DOWN:
                    System.out.println("DOWN");
                    break;
            }
        }
    }

    static int turnRight(int currentDir) {
        return (currentDir + 1) % 4;
    }

    static int turnLeft(int currentDir) {
        return (currentDir + 3) % 4;
    }

    static boolean lookAhead(int dir, int x, int y, int steps) {
        int x2 = x;
        int y2 = y;

        if (dir == LEFT) x2 = x2 - steps;
        if (dir == UP) y2 = y2 - steps;
        if (dir == RIGHT) x2 = x2 + steps;
        if (dir == DOWN) y2 = y2 + steps;

        try {
            return positions[x2][y2]< 1;
        } catch(Exception e) {
            return false;
        }
    }

    static int chooseDirection(int dir, int x, int y) {
        int dirRight = turnRight(dir);
        int dirLeft = turnLeft(dir);
        int stepsLeft = 1;
        int stepsRight = 1;
        while (lookAhead(dirLeft, x, y, stepsLeft)) {
            stepsLeft++;
        }
        while (lookAhead(dirRight, x, y, stepsRight)) {
            stepsRight++;
        }
//        System.err.println("Dir: " + dir + ", x/y: " + x + "/" + y);
//        System.err.println("dirRight: " + dirRight + ", dirLeft: " + dirLeft);
//        System.err.println("Left: " + stepsLeft + ", right: " + stepsRight);
        if (stepsLeft > stepsRight)
            return dirLeft;
        else
            return dirRight;


//        if (dir == LEFT || dir == RIGHT) {
//            return y > 10 ? UP : DOWN;
//        } else {
//            return x > 15 ? LEFT : RIGHT;
//        }
    }
}
