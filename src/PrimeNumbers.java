import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.BitSet;

/**
 * @author anton.safonov
 */
public class PrimeNumbers {
    public static void main(String[] args) throws IOException {
        BitSet bitSet = new BitSet();
        int n = 1000000000;
        int p = 2;
        while (p*p < n) {
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
        FileOutputStream fout = new FileOutputStream(new File("/Users/anton/own/repo/algo/primes.txt"));
        PrintStream out = new PrintStream(fout);
        for (int i = 0; i < n; i++) {
            if (!bitSet.get(i)) {
                out.println(i + ",");
            }
        }
        out.close();

    }
}
