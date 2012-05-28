import java.util.LinkedList;
import java.util.List;

/**
 * How would you determine the minimum number of multiplication to evaluate A ^ B
 *
 * @author anton.safonov
 */
public class ACarrotB {
    public static void main(String[] args) {
        solve(23);
        solve(30);
        solve(27);
        solve(33);
        solve(43);
        solve(47);
        solve(60);
    }

    public static List<Integer> solve(int b) {
        System.out.println("-------- Solving for B = " + b + " --------");
        List<List<Integer>> solutions = new LinkedList<List<Integer>>();
        List<Integer> s0 = new LinkedList<Integer>();
        s0.add(1);
        solutions.add(s0);
        while (!solutions.isEmpty()) {
            List<Integer> current = solutions.remove(0);
            Integer lastElement = current.get(current.size() - 1);
            for (Integer i : current) {
                for (Integer j : current) {
                    if (i + j == b) {
                        List<Integer> solution = new LinkedList<Integer>(current);
                        solution.add(b);
                        System.out.println("Number of solutions tried: " + solutions.size());
                        System.out.println("Optimal solution is: " + solution);
                        System.out.println();
                        return solution;
                    } else if (i + j > lastElement && (i + j) < b) {
                        List<Integer> solution = new LinkedList<Integer>(current);
                        solution.add(i + j);
                        solutions.add(solution);
                    }
                }
            }
        }
        return null;
    }
}
