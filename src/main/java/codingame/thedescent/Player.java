package codingame.thedescent;

import java.util.*;

/**
 * The while loop represents the game.
 * Each iteration represents a turn of the game
 * where you are given inputs (the heights of the mountains)
 * and where you have to print an output (the index of the mountain to fire on)
 * The inputs you are given are automatically updated according to your last actions.
 **/
class Player {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);

        // game loop
        while (true) {

            List<Mountain> mounatinHeights = new ArrayList<>(8);

            for (int i = 0; i < 8; i++) {
                int mountainH = in.nextInt(); // represents the height of one mountain.
                mounatinHeights.add(new Mountain(i, mountainH));
            }

            Optional<Mountain> mountain = mounatinHeights.stream().max(Comparator.comparingInt(Mountain::getHeight));

            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");

            System.out.println(mountain.get().getIndex()); // The index of the mountain to fire on.
        }
    }

    static class Mountain {

        int index;
        int height;

        public Mountain(int index, int height) {
            this.index = index;
            this.height = height;
        }

        public int getHeight() {
            return this.height;
        }

        public int getIndex() {
            return this.index;
        }
    }
}
