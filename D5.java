import java.util.ArrayList;
import java.util.Arrays;

public class D5 {
    static final int INPUT_INTEGER = 5; // Input parameter

    static void run() {
        String input = "3,225,1,225,6,6,1100,1,238,225,104,0,1102,45,16,225,2,65,191,224,1001,224,-3172,224,4,224,102,8,223,223,1001,224,5,224,1,223,224,223,1102,90,55,225,101,77,143,224,101,-127,224,224,4,224,102,8,223,223,1001,224,7,224,1,223,224,223,1102,52,6,225,1101,65,90,225,1102,75,58,225,1102,53,17,224,1001,224,-901,224,4,224,1002,223,8,223,1001,224,3,224,1,224,223,223,1002,69,79,224,1001,224,-5135,224,4,224,1002,223,8,223,1001,224,5,224,1,224,223,223,102,48,40,224,1001,224,-2640,224,4,224,102,8,223,223,1001,224,1,224,1,224,223,223,1101,50,22,225,1001,218,29,224,101,-119,224,224,4,224,102,8,223,223,1001,224,2,224,1,223,224,223,1101,48,19,224,1001,224,-67,224,4,224,102,8,223,223,1001,224,6,224,1,223,224,223,1101,61,77,225,1,13,74,224,1001,224,-103,224,4,224,1002,223,8,223,101,3,224,224,1,224,223,223,1102,28,90,225,4,223,99,0,0,0,677,0,0,0,0,0,0,0,0,0,0,0,1105,0,99999,1105,227,247,1105,1,99999,1005,227,99999,1005,0,256,1105,1,99999,1106,227,99999,1106,0,265,1105,1,99999,1006,0,99999,1006,227,274,1105,1,99999,1105,1,280,1105,1,99999,1,225,225,225,1101,294,0,0,105,1,0,1105,1,99999,1106,0,300,1105,1,99999,1,225,225,225,1101,314,0,0,106,0,0,1105,1,99999,7,226,677,224,102,2,223,223,1005,224,329,1001,223,1,223,8,226,677,224,1002,223,2,223,1005,224,344,101,1,223,223,8,226,226,224,1002,223,2,223,1006,224,359,101,1,223,223,1008,677,226,224,1002,223,2,223,1005,224,374,1001,223,1,223,108,677,677,224,1002,223,2,223,1005,224,389,1001,223,1,223,1107,226,677,224,1002,223,2,223,1006,224,404,101,1,223,223,1008,226,226,224,102,2,223,223,1006,224,419,1001,223,1,223,7,677,226,224,1002,223,2,223,1005,224,434,101,1,223,223,1108,226,226,224,1002,223,2,223,1005,224,449,101,1,223,223,7,226,226,224,102,2,223,223,1005,224,464,101,1,223,223,108,677,226,224,102,2,223,223,1005,224,479,1001,223,1,223,1007,677,226,224,1002,223,2,223,1006,224,494,1001,223,1,223,1007,677,677,224,1002,223,2,223,1006,224,509,1001,223,1,223,107,677,677,224,1002,223,2,223,1005,224,524,101,1,223,223,1108,226,677,224,102,2,223,223,1006,224,539,1001,223,1,223,8,677,226,224,102,2,223,223,1005,224,554,101,1,223,223,1007,226,226,224,102,2,223,223,1006,224,569,1001,223,1,223,107,677,226,224,102,2,223,223,1005,224,584,1001,223,1,223,108,226,226,224,102,2,223,223,1006,224,599,1001,223,1,223,107,226,226,224,1002,223,2,223,1006,224,614,1001,223,1,223,1108,677,226,224,1002,223,2,223,1005,224,629,1001,223,1,223,1107,677,677,224,102,2,223,223,1005,224,644,1001,223,1,223,1008,677,677,224,102,2,223,223,1005,224,659,101,1,223,223,1107,677,226,224,1002,223,2,223,1006,224,674,101,1,223,223,4,223,99,226";
        ArrayList<Integer> integerArrayList = new ArrayList<>();
        for (String s : input.split(",")) {
            integerArrayList.add(Integer.parseInt(s));
        }
        opComputer(integerArrayList);
    }

    static void opComputer(ArrayList<Integer> array) {

        int stepSize;
        for (int pos = 0; pos < array.size(); pos += stepSize) {

            int data = array.get(pos), opcode = data;
            ArrayList<String> modes = new ArrayList<>(Arrays.asList("0", "0"));

            if (data > 100) {
                String s = String.valueOf(data);
                opcode = Integer.parseInt(s.substring(s.length() - 1));
                if (data > 1000)
                    modes = new ArrayList<>(Arrays.asList(s.substring(0, s.length() - 1).split("")));
                else
                    modes = new ArrayList<>(Arrays.asList("0", s.substring(0, 1)));
            }

            switch (opcode) {
                case 1:
                    int val1 = modes.get(1).equals("1") ? array.get(pos + 1) : array.get(array.get(pos + 1));
                    int val2 = modes.get(0).equals("1") ? array.get(pos + 2) : array.get(array.get(pos + 2));
                    array.set(array.get(pos + 3), val1 + val2);
                    stepSize = 4;
                    break;
                case 2:
                    val1 = modes.get(1).equals("1") ? array.get(pos + 1) : array.get(array.get(pos + 1));
                    val2 = modes.get(0).equals("1") ? array.get(pos + 2) : array.get(array.get(pos + 2));
                    array.set(array.get(pos + 3), val1 * val2);
                    stepSize = 4;
                    break;
                case 3:
                    System.out.println("Input");
                    array.set(array.get(pos + 1), D5.INPUT_INTEGER);
                    stepSize = 2;
                    break;
                case 4:
                    System.out.println("Output: " + (modes.get(1).equals("0") ? array.get(array.get(pos + 1)) : array.get(pos + 1)));
                    stepSize = 2;
                    break;
                case 5:
                    val1 = modes.get(1).equals("1") ? array.get(pos + 1) : array.get(array.get(pos + 1));
                    if (val1 != 0) {
                        pos = (modes.get(0).equals("1") ? array.get(pos + 2) : array.get(array.get(pos + 2)));
                        stepSize = 0;
                    } else {
                        stepSize = 3;
                    }
                    break;
                case 6:
                    val1 = modes.get(1).equals("1") ? array.get(pos + 1) : array.get(array.get(pos + 1));
                    if (val1 == 0) {
                        pos = (modes.get(0).equals("1") ? array.get(pos + 2) : array.get(array.get(pos + 2)));
                        stepSize = 0;
                    } else {
                        stepSize = 3;
                    }
                    break;
                case 7:
                    val1 = modes.get(1).equals("1") ? array.get(pos + 1) : array.get(array.get(pos + 1));
                    val2 = modes.get(0).equals("1") ? array.get(pos + 2) : array.get(array.get(pos + 2));

                    array.set(array.get(pos + 3), val1 < val2 ? 1 : 0);
                    stepSize = 4;
                    break;
                case 8:
                    val1 = modes.get(1).equals("1") ? array.get(pos + 1) : array.get(array.get(pos + 1));
                    val2 = modes.get(0).equals("1") ? array.get(pos + 2) : array.get(array.get(pos + 2));

                    array.set(array.get(pos + 3), val1 == val2 ? 1 : 0);
                    stepSize = 4;
                    break;
                case 99:
                    //stepSize = 1;
                    return;
                default:
                    System.out.println("error!");
                    stepSize = 1;
                    break;
            }
        }
    }
}
