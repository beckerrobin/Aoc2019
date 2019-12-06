public class D4 {
    static void run() {
        final int start = 183564;
        final int end = 657474;

        int current = start;
        int part1Counter = 0;
        int part2Counter = 0;
        while (current <= end) {
            current = skipCheck(current);
            if (current > end)
                break;
            if (checkReoccurring(current)) {
                part1Counter++;
            }
            if (checkDoubleDigit(current))
                part2Counter++;
            current++;
        }

        System.out.println("Part 1: " + part1Counter);
        System.out.println("Part 2: " + part2Counter);
    }

    static int skipCheck(int n) {
        String[] nStringArr = String.valueOf(n).split("");
        for (int i = 0; i < nStringArr.length - 1; i++) {
            int current = Integer.parseInt(nStringArr[i]);
            int next = Integer.parseInt(nStringArr[i + 1]);
            if (current > next) {
                nStringArr[i + 1] = String.valueOf(next + current - next);
            }
        }
        return Integer.parseInt(String.join("", nStringArr));
    }

    static boolean checkReoccurring(int n) {
        // Part 1
        String[] nStringArr = String.valueOf(n).split("");
        boolean hasDouble = false;
        for (int i = 0; i < nStringArr.length - 1; i++) {
            if (nStringArr[i].equals(nStringArr[i + 1])) {
                hasDouble = true;
                break;
            }
        }
        return hasDouble;
    }

    static boolean checkDoubleDigit(int n) {
        // Part 2
        String[] nStringArr = String.valueOf(n).split("");
        boolean hasDouble = false;
        for (int i = 0; i < nStringArr.length - 1; i++) {
            if (nStringArr[i].equals(nStringArr[i + 1])) {
                if (i > 0 && nStringArr[i].equals(nStringArr[i - 1])) {
                    hasDouble = false;
                } else
                    hasDouble = true;
            } else if (hasDouble)
                return true;
        }
        return hasDouble;
    }
}
