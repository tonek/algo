/*
 * Main.java
 *  java program model for www.programming-challenges.com
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

class Main implements Runnable{
    static String ReadLn(int maxLength){  // utility function to read from stdin,
        // Provided by Programming-challenges, edit for style only
        byte line[] = new byte [maxLength];
        int length = 0;
        int input = -1;
        try{
            while (length < maxLength){//Read untill maxlength
                input = System.in.read();
                if ((input < 0) || (input == '\n')) break; //or untill end of line ninput
                line [length++] += input;
            }

            if ((input < 0) && (length == 0)) return null;  // eof
            return new String(line, 0, length);
        }catch (IOException e){
            return null;
        }
    }

    public static void main(String args[])  // entry point from OS
    {
        Main myWork = new Main();  // Construct the bootloader
        myWork.run();            // execute
    }

    public void run() {
        new myStuff().run();
    }
}
class myStuff implements Runnable{
    public void run(){
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        List<String> args = new LinkedList<String>();
        while(true) {
            String line;
            try {
                line = bufferedReader.readLine();
            } catch (IOException e) {
                break;
            }
            if (line == null) {
                break;
            }
            args.add(line);
        }
        for (String arg : args) {
            String[] split = arg.split(" ");
            Collatzer.solve(Integer.valueOf(split[0]), Integer.valueOf(split[1]));
        }
    }

    // You can insert more classes here if you want.
}

class Collatzer {

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

