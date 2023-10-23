import java.util.LinkedList;

public class City
{
    public String name;
    public LinkedList<City> neighbors;
    public LinkedList<Edge> edgeList;
    public boolean visited;

    public City(String name) {
        this.name = name;
        this.neighbors = new LinkedList<>();
        this.edgeList = new LinkedList<>();
    }

    public String printNeighbors()
    {
        String s = "";
        for (int i = 0; i < neighbors.size(); i++) {
            s += neighbors.get(i).name + " " + edgeList.get(i).toString() + "\n";
        }
        return s;
    }
    public String toString()
    {
        return name;
    }
}
