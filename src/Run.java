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
        g.iterativeDFS();
        System.out.print(g);
        System.out.println("Plan:");
        System.out.println(g.printPlan());
    }
}
