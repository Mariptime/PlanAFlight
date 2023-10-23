import java.io.*;
import java.util.*;
public class Graph {

    public LinkedList<City> adjList;

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

                for (City city : adjList) {
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
                    adjList.add(c1);
                }

                if (!exists2) {
                    c2.neighbors.add(c1);
                    c2.edgeList.add(e);
                    adjList.add(c2);
                }
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }

    public Graph(String flightData, String requestedFlightData)
    {
        adjList = new LinkedList<>();
        generateGraph(flightData);
        //generateGraph(requestedFlightData);
    }

    public String toString()
    {
        String s = "";
        for (City city : adjList) {
            s += city + ":\n";
            s += city.printNeighbors() + "\n";
        }
        return s;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String infile1 = args[0];
        String infile2 = args[1];
        String outFile = args[2];
        PrintWriter out = new PrintWriter(outFile);
        Graph g = new Graph(infile1, infile2);
        System.out.print(g);
    }

}