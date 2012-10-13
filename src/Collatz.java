import java.util.HashMap;

/**
 * @author anton.safonov
 */
public class Collatz {

    private static final HashMap<Long, Integer> cache = new HashMap<Long, Integer>();

    public static void main(String[] args) {
        solve(201, 210);
//        solve(100, 200);
//        solve(1, 1000000);
//        for (String arg : args) {
//            String[] splitted = arg.split(" ");
//            int i = Integer.valueOf(splitted[0]);
//            int j = Integer.valueOf(splitted[1]);
//            solve(i, j);
//        }

    }

    public static void solve(int i, int j) {
        int max = 1;

        for (int n = i; n < j; n++) {
            int result = count(n);
            if (result > max) {
                max = result;
            }
        }
        System.out.println(i + " " + j + " " + max);
    }

    public static int count(long n) {
        if (n == 1) {
            return 1;
        }
        if (cache.containsKey(n)) {
            return cache.get(n);
        }
        if (n % 2 == 0) {
            return 1 + count(n / 2);
        } else {
            int result = 1 + count(3 * n + 1);
            cache.put(n, result);
            return result;
        }
    }
}
