import java.io.*;
import java.util.*;
public class Graph {

    public LinkedList<City> adjacencyList;
    public LinkedList<Plan> requestedList;
    public LinkedList<LinkedList<City>> allPaths;
    public Stack<City> stack;
    public boolean[] visited;

    public Graph(String filePath1, String filePath2)
    {
        adjacencyList = new LinkedList<>();
        requestedList = new LinkedList<>();
        generateGraph(filePath1);
        generatePlan(filePath2);
    }

    public void generateGraph(String filePath)
    {
        try {
            Scanner sc = new Scanner(new File("src/" + filePath));
            int N = Integer.parseInt(sc.nextLine());
            for (int i = 0; i < N; i++) {
                String line = sc.nextLine();
                String[] tokens = line.split("\\|");

                String city1 = tokens[0];
                String city2 = tokens[1];
                int time = Integer.parseInt(tokens[2]);
                int cost = Integer.parseInt(tokens[3]);

                City c1 = new City(city1);
                City c2 = new City(city2);
                Edge e = new Edge (time, cost);

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
                if(!exists1) {
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

    public void generatePlan(String filePath)
    {
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
    public String toString()
    {
        String s = "";
        for (City city : adjacencyList) {
            s += city + ":\n";
            s += city.printNeighbors() + "\n";
        }
        return s;
    }

    public String printPlan()
    {
        String s = "";
        for (Plan plan : requestedList) {
            s += plan + "\n";
        }
        return s;
    }

    public void printAllPaths(){
        for (int i = 0; i < requestedList.size(); i++) {
            Plan plan = requestedList.get(i);
            System.out.println("Flight " + i + ": " + plan);
            City start = null;
            City end = null;
            allPaths = new LinkedList<>();
            visited = new boolean[adjacencyList.size()];
            Stack<City> pathStack = new Stack<>();
            for (City city : adjacencyList) {
                if (city.name.equals(plan.start)) {
                    start = city;
                }
                if (city.name.equals(plan.end)) {
                    end = city;
                }
            }
            pathStack.push(start);
            printAllPaths(start, end, visited, pathStack);
            System.out.println("");
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

    private void printAllPaths(City start, City end, boolean[] visited, Stack<City> pathStack)
    {
        int startInd = getCityIndex(start.name);
        int endInd = getCityIndex(end.name);

        visited[startInd] = true;

        if (start.name.equals(end.name)) {
            allPaths.add(new LinkedList<>(pathStack));
            System.out.println(pathStack);
        }

        LinkedList<City> neighbors = start.neighbors;
        for (City city : neighbors) {
            int cityInd = getCityIndex(city.name);
            if (!visited[cityInd]) {
                pathStack.push(adjacencyList.get(cityInd));
                printAllPaths(adjacencyList.get(cityInd), adjacencyList.get(endInd), visited, pathStack);
                pathStack.pop();
            }
        }
        visited[startInd] = false;
    }
}