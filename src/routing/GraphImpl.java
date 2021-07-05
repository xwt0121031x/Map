package routing;


import java.util.*;

public class GraphImpl implements Graph{

    private Map<Long, Node> nodesMap;

    private List<Edge> edges;
    
    public GraphImpl(Map<Long, Node> nodesMap, List<Edge> edges) {
        this.nodesMap = nodesMap;
        this.edges = edges;
    }

    @Override
    public Node getNode(long id) {
        return nodesMap.get(id);
    }

    @Override
    public Coordinate getNWCoordinate() {
        Coordinate ret = new Coordinate(0, 0);
        for(Node node:nodesMap.values()) {
            if(node.getCoordinate().getLatitude()>ret.getLatitude()
                ||node.getCoordinate().getLongitude()>ret.getLongitude()){
                    ret = node.getCoordinate();
                }
        }
        return ret;
    }

    @Override
    public Coordinate getSECoordinate() {
        Coordinate ret = new Coordinate(0, 0);
        for(Node node:nodesMap.values()) {
            if(node.getCoordinate().getLatitude()<ret.getLatitude()
                ||node.getCoordinate().getLongitude()<ret.getLongitude()){
                    ret = node.getCoordinate();
                }
        }
        return ret;
    }

    @Override
    public Iterator<Node> iterator() {
        return nodesMap.values().iterator();
    }

    @Override
    public int numEdges() {
        return edges.size();
    }

    @Override
    public int numNodes() {
        return nodesMap.size();
    }

    @Override
    public int removeIsolatedNodes() {
        int count = 0;
        List<Long> del_list = new ArrayList<>();
        for(Map.Entry<Long, Node> entry :nodesMap.entrySet()) {
            if(!entry.getValue().iterator().hasNext()){
                del_list.add(entry.getKey());
            }
        }
        for(Long del_id:del_list) {
            nodesMap.remove(del_id);
            count++;
        }
        return count;
    }


    @Override
    public int removeUntraversableEdges(RoutingAlgorithm ra, TravelType tt) {
        
        int count = 0;
        List<Integer> dels = new ArrayList<>();
        for(int i = 0;i < edges.size();i++) {
            Edge e = edges.get(i);
            if(!e.allowsTravelType(tt, Direction.FORWARD)) {
                if(nodesMap.containsKey(e.getStart().getId())) {
                    Node node = nodesMap.get(e.getStart().getId());
                    node.getEdges().remove(e);
                }
                dels.add(i);
                count++;
            }
        }
        int k = 0;
        for(int del : dels) {
            edges.remove(del-k);
            k++;
        }
        return count;
    }

    @Override
    public boolean isOverlayGraph() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Node getNodeInUnderlyingGraph(long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public double getMaxLongitude() {
        double ret = Double.MIN_VALUE;
        for(Node node:nodesMap.values()) {
            if(Double.compare(node.getCoordinate().getLongitude(),ret)>0){
                ret = node.getCoordinate().getLongitude();
            }
        }
        return ret;
    }

    @Override
    public double getMinLongitude() {
        double ret = Double.MAX_VALUE;
        for(Node node:nodesMap.values()) {
            if(Double.compare(node.getCoordinate().getLongitude(),ret)<0){
                ret = node.getCoordinate().getLongitude();
            }
        }
        return ret;
    }

    @Override
    public double getMaxLatitude() {
        double ret = Double.MIN_VALUE;
        for(Node node:nodesMap.values()) {
            if(Double.compare(node.getCoordinate().getLatitude(),ret)>0){
                ret = node.getCoordinate().getLatitude();
            }
        }
        return ret;
    }
    @Override
    public double getMinLatitude() {
        double ret = Double.MAX_VALUE;
        for(Node node:nodesMap.values()) {
            if(Double.compare(node.getCoordinate().getLatitude(),ret)<0){
                ret = node.getCoordinate().getLatitude();
            }
        }
        return ret;
    }
}
