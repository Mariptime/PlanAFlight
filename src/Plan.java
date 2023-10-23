import com.sun.org.apache.xpath.internal.objects.XString;

public class Plan
{
    public String start;
    public String end;
    public String type;
    public int cost;

    public Plan(String start, String end, String type)
    {
        this.start = start;
        this.end = end;

        if(type.equals("T"))
        {
            this.type = "Time";
        }
        else
        {
            this.type = "Cost";
        }
    }

    public String toString()
    {
        return "Start: " + start + " End: " + end + " Type: " + type;
    }
}
