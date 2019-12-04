// Crossing wires

import java.awt.*;
import java.awt.geom.Line2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class D3 {
    static void run() throws FileNotFoundException {
        Point currentPoint = new Point(0, 0);
        ArrayList<Point> wires1Points = new ArrayList<>(); // [x, y]
        ArrayList<Point> wires2Points = new ArrayList<>(); // [x, y]
        wires1Points.add(new Point(currentPoint));
        wires2Points.add(new Point(currentPoint));

        File file = new File("src/d3.txt");
//        File file = new File("src/d3test.txt");
        Scanner fileScanner = new Scanner(file);
        String[] wire1DataRow = fileScanner.nextLine().split(",");
        for (String s : wire1DataRow) {
            translatePoint(s, currentPoint);
            wires1Points.add(new Point(currentPoint));
        }

        String[] wire2DataRow = fileScanner.nextLine().split(",");
        currentPoint.setLocation(0, 0);

        int minManhattanDistance = Integer.MAX_VALUE;
        ArrayList<Point> intersections = new ArrayList<>();

        for (String s : wire2DataRow) {
            Point nextPoint = new Point(currentPoint);
            translatePoint(s, nextPoint);
            wires2Points.add(new Point(nextPoint));

            for (int i = 0; i < wires1Points.size() - 1; i++) {
                if (currentPoint.equals(new Point(0, 0)))
                    continue;
                Point intersection = findIntersection(currentPoint, nextPoint, wires1Points.get(i), wires1Points.get(i + 1));
                if (intersection != null) {
                    System.out.println("Intersection at: " + intersection.toString());
                    intersections.add(new Point(intersection));
                    minManhattanDistance = Math.min(minManhattanDistance, Math.abs(intersection.x) + Math.abs(intersection.y));
                }
            }
            currentPoint = nextPoint;
        }
        fileScanner.close();

        System.out.println("Part 1: " + minManhattanDistance);

        // TODO: Rounding error?
        int lowestTravel = Integer.MAX_VALUE;
        int wire1Traversal = 0;
        for (int i = 0; i < wires1Points.size() - 1; i++) {
            Line2D wire1 = new Line2D.Double(wires1Points.get(i), wires1Points.get(i + 1));
            for (Point intersection : intersections) {
                if (wire1.ptLineDist(intersection) == 0.0) {
                    System.out.println(intersection.toString() + " is on " + wire1.getP1().toString() + ":" + wire1.getP2().toString());
                    int wire2Traversal = 0;
                    for (int j = 0; j < wires2Points.size() - 1; j++) {
                        Line2D wire2 = new Line2D.Double(wires2Points.get(j), wires2Points.get(j + 1));
                        if (wire2.ptLineDist(intersection) == 0.0) {
                            lowestTravel = (int) Math.min(lowestTravel, wire1Traversal + wires1Points.get(i).distance(intersection) + wire2Traversal + wires2Points.get(j).distance(intersection));
                            break;
                        }
                        wire2Traversal += wire2.getP1().distance(wire2.getP2());
                    }
                }
            }
            wire1Traversal += wire1.getP1().distance(wire1.getP2());
        }

        System.out.println("Part 2: " + lowestTravel);
    }

    static void translatePoint(String string, Point currentPoint) {
        String dir = String.valueOf(string.charAt(0));
        int length = Integer.parseInt(string.substring(1));
        switch (dir) {
            case "R":
                currentPoint.translate(length, 0);
                break;
            case "L":
                currentPoint.translate(-length, 0);
                break;
            case "U":
                currentPoint.translate(0, length);
                break;
            case "D":
                currentPoint.translate(0, -length);
                break;
        }
    }

    static Point findIntersection(Point a1, Point a2, Point b1, Point b2) {
        Line2D lineA = new Line2D.Double(a1, a2);
        Line2D lineB = new Line2D.Double(b1, b2);

        if (lineA.intersectsLine(lineB)) {
            if (a1.x == a2.x) {
                // line a is vertical, a1.x == a2.x, line b is therefore horizontal
                return new Point(a1.x, b1.y);
            } else {
                return new Point(b1.x, a1.y);
            }
        }
        return null;
    }
}
