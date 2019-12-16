import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class D6 {
    static Node san = new Node("SAN");
    static Node you = new Node("YOU");

    static void run() {
        Scanner scanner;
        try {
            scanner = new Scanner(new File("src/d6.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        Node root = null;

        ArrayList<Node> nodeList = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String[] line = scanner.nextLine().split("\\)");
            Node parent = new Node(line[0]);
            Node child = new Node(line[1]);
            if (nodeList.contains(parent))
                parent = nodeList.get(nodeList.indexOf(parent));
            else
                nodeList.add(parent);
            if (nodeList.contains(child))
                child = nodeList.get(nodeList.indexOf(child));
            else
                nodeList.add(child);
            parent.addChild(child);

            if (parent.equals(new Node("COM")))
                root = parent;
            if (child.equals(you))
                you = child;
            else if (child.equals(san)) {
                san = child;
                san.getParent().setStepsToSanta(0);
            }
        }

        System.out.println("Part 1: " + stepCounter(root, 0));
        santaFinder(san.getParent());
    }

    static void santaFinder(Node n) {
        boolean parentExplored = false;
        if (n.getParent() != null) {
            if (n.getParent().getStepsToSanta() != null) {
                n.setStepsToSanta(n.getParent().getStepsToSanta() + 1);
                parentExplored = true;
            } else {
                n.getParent().setStepsToSanta(n.getStepsToSanta() + 1);
            }
        }

        ArrayList<Node> children = n.getChildren();
        if (children.contains(you)) {
            System.out.println("Part 2: " + n.getStepsToSanta());
            return;
        }

        ArrayList<Node> unexplored = new ArrayList<>();
        for (Node child : children) {
            if (child.equals(san))
                continue;
            if (child.getStepsToSanta() != null) {
                if (n.getStepsToSanta() != null)
                    n.setStepsToSanta(Math.min(n.getStepsToSanta(), child.getStepsToSanta() + 1));
                else
                    n.setStepsToSanta(child.getStepsToSanta() + 1);
                continue;
            }
            unexplored.add(child);
        }
        for (Node child : unexplored) {
            santaFinder(child);
        }

        if (!parentExplored && n.getParent() != null) {
            santaFinder(n.getParent());
        }
    }

    static int stepCounter(Node n, int sum) {
        if (n.getChildren().size() == 0)
            return sum;

        int childSum = 0;
        for (Node child : n.getChildren()) {
            childSum += stepCounter(child, sum + 1);
        }

        return sum + childSum;
    }
}

class Node {
    private String value;
    private Node parent = null;
    private ArrayList<Node> children = new ArrayList<>();
    private Integer stepsToSanta = null;

    public Node(String value) {
        this.value = value;
    }

    public Integer getStepsToSanta() {
        return stepsToSanta;
    }

    public void setStepsToSanta(Integer stepsToSanta) {
        this.stepsToSanta = stepsToSanta;
    }

    ArrayList<Node> getChildren() {
        return children;
    }

    void addChild(Node c) {
        children.add(c);
        c.setParent(this);
    }

    Node getParent() {
        return parent;
    }

    void setParent(Node p) {
        parent = p;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(value, node.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
