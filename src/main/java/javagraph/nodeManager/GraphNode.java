package javagraph.nodeManager;

public class GraphNode {
    private int[] NodeNumber;
    private double[] NodeWeight;
    public int Id;
    public boolean visited;
    public double distance = 0;

    public GraphNode(double NodeWeight[], int NodeNumber[], boolean visited, int Id) {
        this.NodeNumber = NodeNumber;
        this.NodeWeight = NodeWeight;
        this.visited = visited;
        this.Id = Id;

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