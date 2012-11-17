import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;

public class Permutation {
    public static void main(String args[] ) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        BufferedReader br = new BufferedReader(new FileReader("/Users/anton/own/repo/algo/test.txt"));
        String line = br.readLine();
        int K = Integer.parseInt(line);
        int[] P = new int[K];
        String pStr = br.readLine();
        String[] split = pStr.split(" ");
        for (int i = 0; i < K; i++) {
            P[i] = Integer.valueOf(split[i]);
        }
        boolean[][] path = new boolean[K][K];
        for (int i = 0; i < K; i++) {
            char[] row = br.readLine().toCharArray();
            for (int j = 0; j < K; j++) {
                path[i][j] = 'Y' == row[j];
            }
        }

        boolean[] prev = new boolean[K];
        for (int i = 0; i < K; i++) {
            boolean[][] newPath = copy(path);
            if (i > 0) {
                int p = P[i-1];
                prev[p-1] = true;
            }
            int newVal = findPath(newPath, prev, P, P[i], i);
            if (newVal < P[i]) {
                for (int j = 0; j < P.length; j++) {
                    if (P[j] == newVal) {
                        P[j] = P[i];
                        P[i] = newVal;
                        break;
                    }
                }
            }
        }
        for (int j = 0; j < P.length; j++) {
            if (j > 0) {
                System.out.print(" ");
            }
            System.out.print(P[j]);
        }
    }

    private static boolean[][] copy(boolean[][] path) {
        boolean[][] newPath = new boolean[path.length][path.length];
        for (int i = 0; i < path.length; i++) {
            for (int j = 0; j < path.length; j++) {
                newPath[i][j] = path[i][j];
            }
        }
        return newPath;
    }

    private static int findPath(boolean[][] path, boolean[] prev, int[] P, int toCheck, int currentPosition) {
        int current = P[currentPosition];
        if (current < toCheck && !prev[current-1]) {
            toCheck = current;
        }
        boolean[] currentRow = path[currentPosition];
        for (int i = 0; i < path.length; i++) {
            if (currentPosition != i && currentRow[i]) {
                clearDirection(path, i);
                toCheck = findPath(path, prev, P, toCheck, i);
            }
        }
        return toCheck;
    }

    private static void clearDirection(boolean[][] path, int position) {
        for (boolean[] row : path) {
            row[position] = false;
        }
    }
}

/**
 * A permutation is a list of  K numbers, each between 1 and K (both inclusive), that has no duplicate elements.
 * <p/>
 * Permutation X is lexicographically smaller than Permutation Y iff for some i <= K:
 * <p/>
 * All of the first i-1 elements of X are equal to first i-1 elements of Y.
 * ith element of X is smaller than ith element of Y.
 * You are given a permutation P, you can exchange some of its elements as many times as you want in any order. You
 * have to find the lexicographically smallest Permutation that you can obtain from P.
 * <p/>
 * K is less than 101.
 * <p/>
 * Input Format:
 * <p/>
 * First line of input contains K being the size of permutation.
 * Next line contains K single spaced numbers indicating the permutation.
 * Each of next K lines contains K characters, character j of line i is equal to 'Y' if you can exchange ith and jth
 * element of a permutation, and 'N' otherwise.
 * <p/>
 * Output Format:
 * <p/>
 * Print K numbers with a space separating each of them indicating the permutation.
 * <p/>
 * Sample Input
 * <p/>
 * 3
 * 3 1 2
 * NNY
 * NNN
 * YNN
 * <p/>
 * Sample Output
 * <p/>
 * 2 1 3
 * <p/>
 * In the first example you can exchange first element with last element to obtain 2 1 3 from 3 1 2.
 */
