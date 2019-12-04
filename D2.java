import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class D2 {
    static void run() throws IOException {
        File inputFile = new File("src/d2.txt");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile));
        String input = bufferedReader.readLine();
        bufferedReader.close();
        ArrayList<Integer> inputArray = Arrays.stream(input.split(",")).map(Integer::parseInt).collect(Collectors.toCollection(ArrayList::new));

        ArrayList<Integer> part1 = new ArrayList<>(inputArray);
        opComputer(part1);
        System.out.println("Part 1: " + part1.get(0));

        int targetResult = 19690720;

        outer:
        for (int i = 0; i < 99; i++) {
            for (int j = 0; j < 99; j++) {
                ArrayList<Integer> part2 = new ArrayList<>(inputArray);
                part2.set(1, i);
                part2.set(2, j);
                opComputer(part2);
                if (targetResult == part2.get(0)) {
                    System.out.println("Part 2: noun=" + i + ", verb=" + j);
                    System.out.println("Answer: " + (100 * i + j));
                    break outer;
                }
            }
        }
    }

    static void opComputer(ArrayList<Integer> array) {
        for (int pos = 0; pos < array.size(); pos += 4) {
            int data = array.get(pos);
            if (data == 1) {
                int val1 = array.get(pos + 1);
                int val2 = array.get(pos + 2);
                array.set(array.get(pos + 3), array.get(val1) + array.get(val2));
            } else if (data == 2) {
                int val1 = array.get(pos + 1);
                int val2 = array.get(pos + 2);
                array.set(array.get(pos + 3), array.get(val1) * array.get(val2));
            } else if (data == 99) {
                break;
            } else {
                System.out.println("error!");
            }
        }
    }
}

