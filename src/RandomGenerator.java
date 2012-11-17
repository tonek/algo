import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RandomGenerator {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        BufferedReader br = new BufferedReader(new FileReader("/Users/anton/own/repo/algo/test.txt"));
        String line = br.readLine();
        int K = Integer.parseInt(line);
        for (int i = 0; i < K; i++) {
            String[] split = br.readLine().split(" ");
            int a = Integer.valueOf(split[0]);
            int b = Integer.valueOf(split[1]);
            if (b > a) {
                int t = a;
                a = b;
                b = t;
            }
            int c = Integer.valueOf(split[2]);
            int[] res = new int[2];
            res[1] = 2 * a * b;
            if (c >= a + b) {
                res[0] = 1;
                res[1] = 1;
            } else if (c <= a && c <= b) {
                res[0] = c * c;
            } else if (c > a && c > b) {
                res[0] = 2 * a * b - ((a + b - c) * (a + b - c));
            } else {
                res[0] = 2 * b * c - b * b;
            }
            simplify(res);
            System.out.println(res[0] + "/" + res[1]);
        }
    }

    private static void simplify(int[] vals) {
        if (vals[0] == vals[1]) {
            vals[0] = 1;
            vals[1] = 1;
        }
        int a;
        int b;
        if (vals[0] > vals[1]) {
            a = vals[0];
            b = vals[1];
        } else {
            a = vals[1];
            b = vals[0];
        }
        int div = b;
        int r;
        while (true) {
            r = a % b;
            if (r > 0) {
                div = r;
            } else {
                break;
            }
            a = b;
            b = r;
        }
        vals[0] = vals[0] / div;
        vals[1] = vals[1] / div;
    }
}

/**
 * Random number generator
 * <p/>
 * There is an ideal random number generator, which given a positive integer M can generate any real number between 0
 * to M with equal probability.
 * Suppose we generate 2 numbers x and y via the generator by giving it 2 positive intergers A and B,
 * what's the probability that x + y is less than C? where C is a positive integer.
 * <p/>
 * Input Format
 * The first line of the input is an integer N, the number of test cases.
 * N lines follow. Each line contains 3 positive integers A, B and C.
 * All the integers are no larger than 10000.
 * <p/>
 * Output Format
 * For each output, output a fraction that indicates the probability. The greatest common divisor of each pair of
 * numerator and denominator should be 1.
 * <p/>
 * Input
 * 3
 * 1 1 1
 * 1 1 2
 * 1 1 3
 * <p/>
 * Output
 * 1/2
 * 1/1
 * 1/1
 */
