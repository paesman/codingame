package codingame.shadowsofthenight.p1;

import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Player {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int W = in.nextInt(); // width of the building.
        int H = in.nextInt(); // height of the building.
        int N = in.nextInt(); // maximum number of turns before game over.
        int X0 = in.nextInt();
        int Y0 = in.nextInt();

        // nur wenn UR, DR, DL oder UL die Richtung ist
        // row = (rightTopPointY + leftBottomPointY) / 2
        // column = (leftBottomPointX + rightTopPointX) / 2

        //areaLeftBottomPoint = leftBottomPointX, leftBottomPointY
        //areaRighTopPoint = rightTopPointX, rightTopPointY

        // game loop
        while (true) {
            String bombDir = in.next(); // the direction of the bombs from batman's current location (U, UR, R, DR, D, DL, L or UL)

            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");
            switch (bombDir) {
                case "U":
                    break;
                case "UR":
                    X0 = X0 + 1;
                    Y0 = Y0 - 1;
                    System.out.println(""+X0+" "+Y0);
                    break;
                case "R":

                    break;
                case "DR":
                    X0 = X0 + 1;
                    Y0 = Y0 + 1;
                    System.out.println(""+X0+" "+Y0);
                    break;
                case "D":
//                    int[] windows = new int[H - (Y0 + 1)];
//                    for (int i = Y0; i < H; i++) {
//                        Arrays.binarySearch()
//                    }
                    break;
                case "DL":
                    X0 = X0 - 1;
                    Y0 = Y0 + 1;
                    System.out.println(""+X0+" "+Y0);
                    break;
                case "L":

                    break;
                case "UL":
                    X0 = X0 - 1;
                    Y0 = Y0 - 1;
                    System.out.println(""+X0+" "+Y0);
                    break;
            }


            // the location of the next window Batman should jump to.
            //System.out.println("0 0");
        }
    }
}
