import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * @author anton.safonov
 */
public class PowerOutage1 {
    public static void main(String[] args) throws IOException {
//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader br = new BufferedReader(new FileReader("/Users/anton/own/repo/algo/test.txt"));
        String line = br.readLine();
        int t = Integer.parseInt(line);
        for (int i = 0; i < t; i++) {
            String[] split = br.readLine().split(" ");
            int n = Integer.valueOf(split[0]);
            int m = Integer.valueOf(split[1]);
            int k = Integer.valueOf(split[2]);

            List<Road>[] roads = new List[n];

            for (int j = 0; j < m; j++) {
                String l = br.readLine();
                int index1 = l.indexOf(' ');
                int index2 = l.indexOf(' ', index1 + 1);
                int x = Integer.valueOf(l.substring(0, index1)) - 1;
                int y = Integer.valueOf(l.substring(index1 + 1, index2)) - 1;
                int weigh = Integer.valueOf(l.substring(index2 + 1));
                if (roads[x] == null) {
                    roads[x] = new ArrayList<Road>(n);
                }
                roads[x].add(new Road(y, weigh));
                if (roads[y] == null) {
                    roads[y] = new ArrayList<Road>(n);
                }
                roads[y].add(new Road(x, weigh));
            }

            boolean[] processed = new boolean[n];
            List<boolean[]> clusters = new LinkedList<boolean[]>();
                for (int node = 0; node < n; node++) {
                    if (processed[node]) {
                        continue;
                    }
                    boolean[] cluster = findCluster(roads, new boolean[n], node, processed);
                    clusters.add(cluster);
                }

            if (clusters.size() > k) {
                System.out.println("Impossible!");
                continue;
            }
            List<Integer> minRoads = new ArrayList<Integer>();
            for (boolean[] cluster : clusters) {
                minRoads.addAll(findMinRoads(roads, cluster));
            }
            int diff = 0;
            if (k > clusters.size() && !minRoads.isEmpty()) {

                TreeMap<Integer, Integer> counts = new TreeMap<Integer, Integer>();
                for (Integer minRoad : minRoads) {
                    Integer count = counts.get(minRoad);
                    if (count == null) {
                        counts.put(minRoad, 1);
                    } else {
                        counts.put(minRoad, count + 1);
                    }
                }
                int kLeft = k - clusters.size();
                for (Map.Entry<Integer, Integer> entry : counts.descendingMap().entrySet()) {
                    if (kLeft == 0) {
                        break;
                    }
                    for (int j = 0; j < entry.getValue(); j++) {
                        if (kLeft == 0) {
                            break;
                        }
                        diff = diff + entry.getKey();
                        kLeft--;
                    }
                }
            }
            int totalWeight = -diff;
            for (Integer minRoad : minRoads) {
                totalWeight += minRoad;
            }
            System.out.println(totalWeight < 0 ? 0 : totalWeight);
        }
    }

    private static boolean[] findCluster(List<Road>[] roads, boolean[] cluster, int node, boolean[] processed) {
        cluster[node] = true;
        processed[node] = true;
        List<Road> road = roads[node];
        if (road != null) {
            for (Road r : road) {
                if (!cluster[r.dest]) {
                    findCluster(roads, cluster, r.dest, processed);
                }
            }
        }
        return cluster;
    }

    private static List<Integer> findMinRoads(List<Road>[] roads, boolean[] cluster) {
        int size = 0;
        int firstIndex = -1;
        for (int i = 0; i < cluster.length; i++) {
            if (cluster[i]) {
                if (firstIndex < 0) {
                    firstIndex = i;
                }
                size++;
            }
        }
        if (size == 1) {
            return Collections.emptyList();
        }
        boolean [] processedFlags = new boolean[cluster.length];
        int[] processed = new int[size];
        for (int i = 0; i < size; i++) {
            processed[i] = -1;
        }
        int processedCount = 1;
        processed[0] = firstIndex;
        processedFlags[firstIndex] = true;
        List<Integer> result = new ArrayList<Integer>();
        while (size > processedCount) {
            Road next = null;
            for (int node : processed) {
                if (node == -1) {
                    break;
                }
                List<Road> road = roads[node];
                if (road != null) {
                    for (Road r : road) {
                        if (node != r.dest && !processedFlags[r.dest]  && (next == null || r.weigh < next.weigh)) {
                            next = r;
                        }
                    }
                }
            }
            if (next == null) {
                throw new IllegalStateException("nextNode == null");
            }
            processedFlags[next.dest] = true;
            processedCount++;
            processed[processedCount - 1] = next.dest;
            result.add(next.weigh);
        }
        return result;
    }

    private static class Road {
        int dest;
        int weigh;

        private Road(int dest, int weigh) {
            this.dest = dest;
            this.weigh = weigh;
        }
    }
}
