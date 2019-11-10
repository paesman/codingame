package codingame.temperatures;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Solution {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt(); // the number of temperatures to analyse

        List<Temperature> temperatureList = new ArrayList<>(n);

        for (int i = 0; i < n; i++) {
            int t = in.nextInt(); // a temperature expressed as an integer ranging from -273 to 5526
            temperatureList.add(new Temperature(t));
        }

        Temperature minTemp = temperatureList
                .stream()
                .min((getComparator()))
                .orElse(new Temperature(0));

        // Write an action using System.out.println()
        // To debug: System.err.println("Debug messages...");

        System.out.println(minTemp.getTemperature());
    }

    static class Temperature {

        int temperature;

        public Temperature(int temperature) {
            this.temperature = temperature;
        }

        public int getTemperature() {
            return this.temperature;
        }
    }

    static Comparator<Temperature> getComparator() {

        return new Comparator<Temperature>() {
            @Override
            public int compare(Temperature o1, Temperature o2) {
                if (Math.abs(o1.getTemperature() - 0) > Math.abs(o2.getTemperature() - 0))
                    return 1;
                else if (Math.abs(o1.getTemperature() - 0) < Math.abs(o2.getTemperature() - 0))
                    return -1;
                else {
                    if (o1.getTemperature() < 0)
                        return 1;
                    else if (o2.getTemperature() < 0)
                        return -1;
                    else
                        return 0;
                }
            }
        };
    }
}