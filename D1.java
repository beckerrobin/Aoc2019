import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class D1 {
    static void run() throws IOException {
        File inputFile = new File("src/d1.txt");
        ArrayList<Integer> inputArray = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile));
        String in;
        while ((in = bufferedReader.readLine()) != null) {
            inputArray.add(Integer.parseInt(in));
        }
        bufferedReader.close();

        int fuelSum = 0;
        for (Integer part : inputArray) {
            fuelSum += (Math.floor(part / 3.0) - 2);
        }
        System.out.println("Part 1: " + fuelSum);

        int testsum = 0;
        int testmass = 1969;
        int newFuel = testmass;
        while (true) {
            newFuel = (int) (Math.floor(newFuel / 3.0) - 2);
            if (newFuel < 0) {
                break;
            }
            testsum += newFuel;
        }
        System.out.println("Test: " + testsum);

        int sum2 = 0;
        for (Integer part : inputArray) {
            newFuel = part;
            while (true) {
                newFuel = (int) (Math.floor(newFuel / 3.0) - 2);
                if (newFuel < 0)
                    break;
                sum2 += newFuel;
            }
        }
        System.out.println("Part 2: " + sum2);
    }
}
