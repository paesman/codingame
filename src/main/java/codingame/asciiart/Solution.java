package codingame.asciiart;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Solution {

    /*
    4
    5
    MANHATTAN
0     #  ##   ## ##  ### ###  ## # # ###
1    # # # # #   # # #   #   #   # #  #
2    ### ##  #   # # ##  ##  # # ###  #
3    # # # # #   # # #   #   # # # #  #
4    # # ##   ## ##  ### #    ## # # ###
     */

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        //width
        int L = in.nextInt();
        //height
        int H = in.nextInt();
        if (in.hasNextLine()) {
            in.nextLine();
        }
        String T = in.nextLine();

        CharacterManager characterManager = new CharacterManager(H);

        for (int i = 0; i < H; i++) {
            String ROW = in.nextLine();

            int characterCounter = 1;
            for (int j = 0; j < ROW.length(); j += L) {
                String subRow = ROW.substring(j, j+L);
                characterManager.addContentToCharacter(characterCounter, i, subRow);
                characterCounter++;
            }
        }

        // Write an action using System.out.println()
        // To debug: System.err.println("Debug messages...");

        System.out.println(characterManager.getAsciiByString(T));
    }

    static class Character {

        private String key;
        private String[] content;

        Character(String key, int size) {
            this.key = key;
            this.content = new String[size];
        }

        void append(int index, String content) {
            this.content[index] = content;
        }

        String getContent() {
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < content.length; i++) {
                result.append(content[i]);
                if (i < content.length - 1)
                    result.append("\n");
            }
            return result.toString();
        }

        String getContentByLine(int lineNumber) {
            return this.content[lineNumber];
        }

        String getKey() {
            return this.key;
        }
    }

    static class CharacterManager {

//        private Map<String, Character> characterToContentMap;
        private Map<Integer, Character> characterToContentMap;
        private int asciiCharHeight;

        CharacterManager(int arraySize) {
            this.asciiCharHeight = arraySize;
            this.characterToContentMap = new HashMap<>(27);
            this.characterToContentMap.put(1, new Character("A", arraySize));
            this.characterToContentMap.put(2, new Character("B", arraySize));
            this.characterToContentMap.put(3, new Character("C", arraySize));
            this.characterToContentMap.put(4, new Character("D", arraySize));
            this.characterToContentMap.put(5, new Character("E", arraySize));
            this.characterToContentMap.put(6, new Character("F", arraySize));
            this.characterToContentMap.put(7, new Character("G", arraySize));
            this.characterToContentMap.put(8, new Character("H", arraySize));
            this.characterToContentMap.put(9, new Character("I", arraySize));
            this.characterToContentMap.put(10, new Character("J", arraySize));
            this.characterToContentMap.put(11, new Character("K", arraySize));
            this.characterToContentMap.put(12, new Character("L", arraySize));
            this.characterToContentMap.put(13, new Character("M", arraySize));
            this.characterToContentMap.put(14, new Character("N", arraySize));
            this.characterToContentMap.put(15, new Character("O", arraySize));
            this.characterToContentMap.put(16, new Character("P", arraySize));
            this.characterToContentMap.put(17, new Character("Q", arraySize));
            this.characterToContentMap.put(18, new Character("R", arraySize));
            this.characterToContentMap.put(19, new Character("S", arraySize));
            this.characterToContentMap.put(20, new Character("T", arraySize));
            this.characterToContentMap.put(21, new Character("U", arraySize));
            this.characterToContentMap.put(22, new Character("V", arraySize));
            this.characterToContentMap.put(23, new Character("W", arraySize));
            this.characterToContentMap.put(24, new Character("X", arraySize));
            this.characterToContentMap.put(25, new Character("Y", arraySize));
            this.characterToContentMap.put(26, new Character("Z", arraySize));
            this.characterToContentMap.put(27, new Character("?", arraySize));
        }

        void addContentToCharacter(int nummericIndex, int characterIndex, String content) {
            Character character = this.characterToContentMap.get(nummericIndex);
            character.append(characterIndex, content);
        }

        String getAsciiByString(String toAscii) {
            if (toAscii.length() < 2) {
                return findCharacter(toAscii).getContent();
            } else {
                StringBuilder result = new StringBuilder();

                for (int j = 0; j < this.asciiCharHeight; j++) {

                    for (int i = 0; i < toAscii.length(); i++) {
                        Character character = findCharacter(String.valueOf(toAscii.charAt(i)));
                        result.append(character.getContentByLine(j));
                    }

                    if (j < this.asciiCharHeight - 1)
                        result.append("\n");
                }

                return result.toString();
            }
        }

        private Character findCharacter(String charToFind) {
            Character result = this.characterToContentMap.values()
                    .stream()
                    .filter(character -> character.getKey().equalsIgnoreCase(charToFind))
                    .findFirst()
                    .orElse(this.characterToContentMap.get(27));
            return result;
        }

    }
}
