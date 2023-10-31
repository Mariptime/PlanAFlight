import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;

public class Graph {

    public LinkedList<City> adjacencyList;
    public LinkedList<Plan> requestedList;
    public LinkedList<LinkedList<City>> allPaths;
    public String outFile;
    public Stack<City> pathStack;
    public boolean[] visited;

    public Graph(String filePath1, String filePath2, String outFile) {
        adjacencyList = new LinkedList<>();
        requestedList = new LinkedList<>();
        generateGraph(filePath1);
        generatePlan(filePath2);
        this.outFile = outFile;
    }

    public void generateGraph(String filePath) {
        try {
            Scanner sc = new Scanner(new File("src/" + filePath));
            int N = Integer.parseInt(sc.nextLine());
            for (int i = 0; i < N; i++) {
                String line = sc.nextLine();
                String[] tokens = line.split("\\|");

                String city1 = tokens[0];
                String city2 = tokens[1];
                int cost = Integer.parseInt(tokens[2]);
                int time = Integer.parseInt(tokens[3]);

                City c1 = new City(city1);
                City c2 = new City(city2);
                Edge e = new Edge(time, cost);

                boolean exists1 = false;
                boolean exists2 = false;

                for (City city : adjacencyList) {
                    if (city.name.equals(city1)) {
                        if (!city.neighbors.contains(c2)) {
                            city.neighbors.add(c2);
                            city.edgeList.add(e);
                        }
                        exists1 = true;
                    }
                    if (city.name.equals(city2)) {
                        if (!city.neighbors.contains(c1)) {
                            city.neighbors.add(c1);
                            city.edgeList.add(e);
                        }
                        exists2 = true;
                    }
                }
                if (!exists1) {
                    c1.neighbors.add(c2);
                    c1.edgeList.add(e);
                    adjacencyList.add(c1);
                }

                if (!exists2) {
                    c2.neighbors.add(c1);
                    c2.edgeList.add(e);
                    adjacencyList.add(c2);
                }
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }

    public void generatePlan(String filePath) {
        try {
            Scanner sc = new Scanner(new File("src/" + filePath));
            int N = Integer.parseInt(sc.nextLine());
            for (int i = 0; i < N; i++) {
                String line = sc.nextLine();
                String[] tokens = line.split("\\|");

                String city1 = tokens[0];
                String city2 = tokens[1];
                String type = tokens[2];

                Plan p = new Plan(city1, city2, type);
                requestedList.add(p);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }

    public int getCityIndex(String cityName) {
        for (int i = 0; i < adjacencyList.size(); i++) {
            if (adjacencyList.get(i).name.equals(cityName)) {
                return i;
            }
        }
        return -1;
    }

    public String toString() {
        String s = "";
        for (City city : adjacencyList) {
            s += city + ":\n";
            s += city.printNeighbors() + "\n";
        }
        return s;
    }

    public void findAllPaths() {
        for (int i = 0; i < requestedList.size(); i++) {
            Plan plan = requestedList.get(i);
            String flight = "Flight " + (i + 1) + ": " + plan;
            City start = null;
            City end = null;
            allPaths = new LinkedList<>();
            visited = new boolean[adjacencyList.size()];
            pathStack = new Stack<>();
            for (City city : adjacencyList) {
                if (city.name.equals(plan.start)) {
                    start = city;
                }
                if (city.name.equals(plan.end)) {
                    end = city;
                }
            }
            pathStack.push(start);
            findAllPaths(start, end, visited, pathStack);
            sortAllPaths(plan);
            printPathsToFile(flight);
        }
    }

    private void findAllPaths(City start, City end, boolean[] visited, Stack<City> pathStack) {
        if (start == null || end == null)
            return;

        int startInd = getCityIndex(start.name);
        int endInd = getCityIndex(end.name);

        visited[startInd] = true;

        if (start.name.equals(end.name)) {
            allPaths.add(new LinkedList<>(pathStack));
        }

        LinkedList<City> neighbors = start.neighbors;
        for (City city : neighbors) {
            int cityInd = getCityIndex(city.name);
            if (!visited[cityInd]) {
                pathStack.push(adjacencyList.get(cityInd));
                findAllPaths(adjacencyList.get(cityInd), adjacencyList.get(endInd), visited, pathStack);
                pathStack.pop();
            }
        }
        visited[startInd] = false;
    }

    public void sortAllPaths(Plan plan) {
        String type = plan.type;
        if (type.equals("Time")) {
            for (int i = 0; i < allPaths.size(); i++) {
                for (int j = 0; j < allPaths.size() - 1; j++) {
                    if (getTime(allPaths.get(j)) > getTime(allPaths.get(j + 1))) {
                        LinkedList<City> temp = allPaths.get(j);
                        allPaths.set(j, allPaths.get(j + 1));
                        allPaths.set(j + 1, temp);
                    }
                }
            }
        } else {
            for (int i = 0; i < allPaths.size(); i++) {
                for (int j = 0; j < allPaths.size() - 1; j++) {
                    if (getCost(allPaths.get(j)) > getCost(allPaths.get(j + 1))) {
                        LinkedList<City> temp = allPaths.get(j);
                        allPaths.set(j, allPaths.get(j + 1));
                        allPaths.set(j + 1, temp);
                    }
                }
            }
        }
    }

    private int getTime(LinkedList<City> cities) {
        int time = 0;
        for (int i = 0; i < cities.size() - 1; i++) {
            City city1 = cities.get(i);
            City city2 = cities.get(i + 1);
            for (int j = 0; j < city1.neighbors.size(); j++) {
                if (city1.neighbors.get(j).name.equals(city2.name)) {
                    time += city1.edgeList.get(j).time;
                }
            }
        }
        return time;
    }

    private double getCost(LinkedList<City> cities) {
        int cost = 0;

        for (int i = 0; i < cities.size() - 1; i++) {
            City city1 = cities.get(i);
            City city2 = cities.get(i + 1);
            for (int j = 0; j < city1.neighbors.size(); j++) {
                if (city1.neighbors.get(j).name.equals(city2.name)) {
                    cost += city1.edgeList.get(j).cost;
                }
            }
        }
        return cost;
    }

    public void printAllPaths() {
        for (LinkedList<City> path : allPaths) {
            for (int i = 0; i < path.size(); i++) {
                City city = path.get(i);
                if (i != path.size() - 1)
                    System.out.print(city + " -> ");
                else
                    System.out.print(city + ". ");
            }
            if (requestedList.get(0).type.equals("Time")) {
                System.out.print("Time: " + getTime(path) + " Cost: " + String.format("%.2f", getCost(path)));
            } else {
                System.out.print("Time: " + getTime(path) + " Cost: " + String.format("%.2f", getCost(path)));
            }
            System.out.println();
        }
        System.out.println();
    }

    public boolean printPathsToFile(String flight) {
        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream(new File("src/" + outFile), true));
            pw.println(flight);
            if (allPaths.isEmpty()) {
                pw.println("No flights available.");
                pw.println();
                pw.close();
                return true;
            }
            for (int j = 0; j < Math.min(3, allPaths.size()); j++) {
                LinkedList<City> path = allPaths.get(j);
                for (int i = 0; i < path.size(); i++) {
                    City city = path.get(i);
                    if (i != path.size() - 1)
                        pw.print(city + " -> ");
                    else
                        pw.print(city + ". ");
                }
                if (requestedList.get(0).type.equals("Time")) {
                    pw.print("Time: " + getTime(path) + " Cost: " + String.format("%.2f", getCost(path)));
                } else {
                    pw.print("Time: " + getTime(path) + " Cost: " + String.format("%.2f", getCost(path)));
                }
                pw.println();
            }
            pw.println();
            pw.close();
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            return false;
        }
    }
    public static void main(String[] args) throws FileNotFoundException {
        String infile1 = args[0];
        String infile2 = args[1];
        String outFile = args[2];
        Graph g = new Graph(infile1, infile2, outFile);
        g.findAllPaths();
    }
}