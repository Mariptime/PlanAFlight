public  class Edge
{
    public int time;
    public int cost;

    public Edge(int time, int cost)
    {
        this.time = time;
        this.cost = cost;
    }

    public String toString()
    {
        return "Time: " + time + " Cost: " + cost;
    }
}