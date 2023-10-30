import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Run
{
    public static void main(String[] args) throws FileNotFoundException {
        String infile1 = args[0];
        String infile2 = args[1];
        String outFile = args[2];
        PrintWriter out = new PrintWriter(outFile);
        Graph g = new Graph(infile1, infile2);
        g.printAllPaths();
        //System.out.print(g);
        //System.out.println("Plan:");
        //System.out.println(g.printPlan());

        //System.out.println("DFS:");
        //System.out.println(g.printDFS());
    }
}
/*
import java.util.*;

class Graph {
    private int V;
    private LinkedList<Integer> adj[];

    Graph(int v) {
        V = v;
        adj = new LinkedList[v];
        for (int i = 0; i < v; ++i)
            adj[i] = new LinkedList();
    }

    void addEdge(int v, int w) {
        adj[v].add(w);
        adj[w].add(v); // Add w to v's list to make the graph undirected
    }

    void printAllPaths(int s, int d) {
        boolean[] isVisited = new boolean[V];
        Stack<Integer> pathStack = new Stack<>();
        pathStack.push(s);

        printAllPathsUtil(s, d, isVisited, pathStack);
    }

    private void printAllPathsUtil(Integer u, Integer d,
                                   boolean[] isVisited,
                                   Stack<Integer> localPathStack) {

        isVisited[u] = true;

        if (u.equals(d)) {
            System.out.println(localPathStack);
        }

        for (Integer i : adj[u]) {
            if (!isVisited[i]) {
                localPathStack.push(i);
                printAllPathsUtil(i, d, isVisited, localPathStack);

                localPathStack.pop();
            }
        }

        isVisited[u] = false;
    }
}

public class Main {
    public static void main(String[] args) {
        Graph g = new Graph(4);

        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(1, 2);
        g.addEdge(1, 3);

        int s = 3;
        int d = 0;

        System.out.println("Following are all different paths from " + s + " to " + d);
        g.printAllPaths(s, d);
    }
}
*/
