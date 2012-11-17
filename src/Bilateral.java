import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * <a href="http://www.spotify.com/int/jobs/tech/bilateral-projects/">link</a>
 *
 * @author anton.safonov
 */
public class Bilateral {

    public static void main(String[] args) throws IOException {
//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader br = new BufferedReader(new FileReader("/Users/anton/own/repo/algo/test.txt"));
        String line = br.readLine();
        int m = Integer.parseInt(line);
        Employee[] employees = new Employee[2000];
        int[][] teams = new int[m][2];
        for (int team = 0; team < m; team++) {
            String[] split = br.readLine().split(" ");
            int i = Integer.valueOf(split[0]) - 1000;
            int j = Integer.valueOf(split[1]) - 1000;
            if (employees[i] == null) {
                employees[i] = new Employee();
            }
            if (employees[j] == null) {
                employees[j] = new Employee();
            }
            employees[i].addTem(team);
            employees[j].addTem(team);
            teams[team][0] = i;
            teams[team][1] = j;
        }
        List<Integer> result = new LinkedList<Integer>();
        int teamsInvited = 0;
        while (teamsInvited < m) {
            int maxIndex = -1;
            Employee e = null;
            for (int i = 0; i < employees.length; i++) {
                Employee emp = employees[i];
                if (emp == null) {
                    continue;
                }
                if (e == null || e.getTeams().size() < emp.getTeams().size()
                        || (e.getTeams().size() == emp.getTeams().size() && i == 9)) {
                    maxIndex = i;
                    e = emp;
                }
            }
            result.add(maxIndex);
            employees[maxIndex] =  null;
            teamsInvited += e.getTeams().size();
            for (Integer team : e.getTeams()) {
                clearTeam(employees, teams[team][0], team);
                clearTeam(employees, teams[team][1], team);
            }
        }
        System.out.println(result.size());
        for (Integer index : result) {
            System.out.println(index + 1000);
        }
    }

    private static void clearTeam(Employee[] employees, int index, Integer team) {
        Employee employee = employees[index];
        if (employee != null) {
            employee.getTeams().remove(team);
            if (employee.getTeams().isEmpty()) {
                employees[index] =  null;
            }
        }
    }

    private static class Employee {
        private final Set<Integer> teams = new HashSet<Integer>();
        public void addTem(int team) {
            teams.add(team);
        }

        public Set<Integer> getTeams() {
            return teams;
        }
    }
}
