public class Edge {
    public int time;
    public double cost;

    public Edge(int time, int cost) {
        this.time = time;
        this.cost = cost;
    }

    public String toString() {
        return "Time: " + time + " Cost: " + String.format("%.2f", cost);
    }
}