import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.BitSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * @author anton.safonov
 */
public class NCr {

    private static final HashMap<Key, Long> cache = new HashMap<Key, Long>();
    private static int[] primes;
    public static void main(String args[] ) throws Exception {
//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader br = new BufferedReader(new FileReader("/Users/anton/own/repo/algo/test.txt"));
        String line = br.readLine();
        int T = Integer.parseInt(line);
        Key[] requests = new Key[T];
        int maxN=0;
        int maxR=0;

        for (int i = 0; i < T; i++) {
            String s = br.readLine();
            String[] split = s.split(" ");
            int n = Integer.valueOf(split[0]);
            int r = Integer.valueOf(split[1]);
            List<Integer> allPrimes = getPrimes(n);

            int[] primes1 = new int[40000];
            int[] primes2 = new int[40000];
            int[] primes3 = new int[40000];
            fillPrimes(n, primes1, allPrimes);
            fillPrimes(r, primes2, allPrimes);
            fillPrimes(n-r, primes3, allPrimes);

            int res = 1;
            for (int j = 2; j < primes1.length; j++) {
                primes1[j] = primes1[j] - primes2[j] - primes3[j];
                for (int p = 0; p < primes1[j]; p++) {
                    res = res * j;
                }
                res = res % 142857;
            }
            System.out.println(res);
        }

    }

    private static void fillPrimes(int n, int[] primes, List<Integer> allPrimes) {
        for (Integer prime : allPrimes) {
            if (prime > n) {
                return;
            }
            primes[prime] = getPower(n, prime);
        }
    }

    private static int getPower(int n, int prime) {
        int p = 0;
        int v = prime;
        while (n/v > 0) {
            p = p + n/v;
            v = v * prime;
        }
        return p;
    }

    private static List<Integer> getPrimes(int n) {
        BitSet bitSet = new BitSet();
        int p = 2;
        while (p * p <= n) {
            for (int i = 2*p; i < n; i = i + p) {
                bitSet.set(i);
            }
            for (int i = p+1; i < n; i++) {
                if (!bitSet.get(i)) {
                    p = i;
                    break;
                }
            }
        }
        List<Integer> res = new LinkedList<Integer>();
        for (int i = 2; i < n; i++) {
            if (!bitSet.get(i)) {
                res.add(i);
            }
        }
        return res;
    }

//    private Long calculate2(int n, int r) {
//
//    }

    private static Long calculate(int n, int r) {
        if (n == 0 || n < r) {
            return 0L;
        }
        if (r == 0 || n == r) {
            return 1L;
        }
        Key k = new Key(n, r);
        Long val = cache.get(k);
        if (val == null) {
            val = calculate(n-1, r) + calculate(n-1, r-1);
            val = val % 142857;
            cache.put(k, val);
        }
        return val;
    }

    private static class Key {
        final int n;
        final int r;

        private Key(int n, int r) {
            this.n = n;
            this.r = r;
        }

        @Override
        public int hashCode() {
            return n + 31*r;
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof Key && ((Key) obj).n == this.n && ((Key) obj).r == this.r;
        }
    }

}
