import java.util.*;

public class TreeIterator {
    static class Node {
        int value;
        Node left;
        Node right;

        Node(int value, Node left, Node right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }

        Node(int value) {
            this.value = value;
        }
    }

    private Node current;
    private boolean init = true;
    private final List<Node> path = new LinkedList<Node>();

    public TreeIterator(Node root) {
        current = root;
        while(current.left != null) {
            path.add(current);
            current = current.left;
        }
    }

    public boolean hasNext() {
        if (init && current != null) {
            return true;
        }

        return !path.isEmpty() || current.right != null;
    }

    public Node next() {
        if (path.isEmpty() && (init && current == null)) {
            throw new IllegalStateException("There is no more elements in tree");
        }
        if (init) {
            init = false;
            return current;
        }

        if (current.right != null) {
            current = current.right;
            while(current.left != null) {
                path.add(current);
                current = current.left;
            }

        } else {
            current = path.remove(path.size() - 1);
        }
        return current;
    }

    static List<Node> getTreeInOrder(Node root){
        if (root == null) return null;
        List<Node> parents = new LinkedList<Node>();
        Node cur = root;
        while(cur.left != null) {
            parents.add(cur);
            cur = cur.left;
        }
        List<Node> result = new ArrayList<Node>();

        while(true) {
            result.add(cur);
            Node next = null;

            if (cur.right != null) {
                parents.add(cur);
                next = cur.right;
                while(next.left != null) {
                    parents.add(next);
                    next = next.left;
                }

            } else {
                while(next == null && !parents.isEmpty()) {
                    Node parent = parents.remove(parents.size()-1);
                    if (parent.right != cur) {
                        next = parent;
                    } else {
                        cur = parent;
                    }
                }
            }
            if (next == null) break;
            cur = next;
        }
        return result;
    }

    public static void main(String[] args) {
        Node n20 = new Node(20);
        Node n60 = new Node(60);
        Node n80 = new Node(80);

        Node n70 = new Node(70, n60, n80);

        Node n50 = new Node(50, n20, n70);

        Node n700 = new Node(700);
        Node n1500 = new Node(1500);
        Node n1950 = new Node(1950);

        Node n1900 = new Node(1900, n1500, n1950);

        Node n1000 = new Node(1000, n700, n1900);

        Node root = new Node(100, n50, n1000);

        TreeIterator iter = new TreeIterator(root);
        while (iter.hasNext()) {
            System.out.print(iter.next().value + " ");
        }

        System.out.println();

        List<Node> treeInOrder = getTreeInOrder(root);
        for (Node node : treeInOrder) {
            System.out.print(node.value + " ");
        }
    }

}