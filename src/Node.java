// import java.beans.Visibility;

public class Node {
    private int[] NodeNumber;
    private double[] NodeWeight;
    public boolean visited;

    public Node(double NodeWeight[], int NodeNumber[], boolean visited) {
        this.NodeNumber = NodeNumber;
        this.NodeWeight = NodeWeight;
        this.visited = visited;
    }

    public int getNodeNum(int i) {
        return NodeNumber[i];
    }

    public void setNodeNum(int i, int value) {
        NodeNumber[i] = value;
    }

    public double getNodeWeight(int i) {
        return NodeWeight[i];
    }

    public void setNodeWeight(int i, double value) {
        NodeWeight[i] = value;
    }

}
