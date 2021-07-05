package routing;

import java.util.Iterator;
import java.util.List;

public class RouteLegBaseImpl extends RouteLegBase{

    private List<Node> nodes;

    private double distance;

    private TravelType travelType = TravelType.ANY;

    RouteLegBaseImpl(List<Node> nodes, TravelType travelType) {
        this.nodes = nodes;
        this.travelType  = travelType;
    }

    public RouteLegBaseImpl(List<Node> nodes, double distance) {
        this.nodes = nodes;
        this.distance = distance;
    }

    public TravelType getTravelType() {
        return travelType;
    }

    public void setTravelType(TravelType travelType) {
        this.travelType = travelType;
    }

    @Override
    public double getDistance() {
        double total_dis = 0;
        for(int i=0;i<nodes.size()-1;i++) {
            total_dis +=nodes.get(i).getCoordinate().getDistance(nodes.get(i+1).getCoordinate());
        }
        this.distance = total_dis;
        return this.distance;
    }

    @Override
    public Node getEndNode() {
        if(nodes.size()>0)
            return nodes.get(nodes.size()-1);
        return null;
    }

    @Override
    public Node getStartNode() {
        return nodes.get(0);
    }

    @Override
    public Iterator<Node> iterator() {
        return nodes.iterator();
    }

    @Override
    public int size() {
        return nodes.size();
    }
    
}
