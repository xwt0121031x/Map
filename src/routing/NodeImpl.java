package routing;

import java.util.Iterator;
import java.util.List;


public class NodeImpl implements Node{

    /**
     * double F (总预估距离F=G+H)
     * 
     * @param id
     * @param coordinate
     * @param edges
     */

    private NodeInfo nodeInfo;

    public void setNodeInfo(NodeInfo nodeInfo) {
        this.nodeInfo = nodeInfo;
    }

    public NodeInfo getNodeInfo() {
        return nodeInfo;
    }


    NodeImpl(long id, Coordinate coordinate, List<Edge> edges) {
        this.id = id;
        this.coordinate = coordinate;
        this.edges = edges;
    }

    public NodeImpl(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
    
    private List<Edge> edges;

    private long id;

    private Coordinate coordinate;

    @Override
    public Coordinate getCoordinate() {
        return this.coordinate;
    }

    @Override
    public Edge getEdge(int idx) {
        return edges.get(idx);
    }

    @Override
    public long getId() {
        return this.id;
    }

    @Override
    public Iterator<Edge> iterator() {
        return edges.iterator();
    }

    @Override
    public int numEdges() {
        return this.edges.size();
    }

    @Override
    public void addEdge(Edge e) {
        edges.add(e);
    }

    @Override
    public void removeEdge(int i) {
        edges.remove(i);
    }


    @Override
    public List<Edge> getEdges() {
        return edges;
    }

    @Override
    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    @Override
    public double getDistance(Node node) {
        return this.getCoordinate().getDistance(node.getCoordinate());
    }
}
