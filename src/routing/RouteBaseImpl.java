package routing;

import java.util.Iterator;
import java.util.List;

public class RouteBaseImpl extends RouteBase{

    private List<RouteLeg> routeLegs;

    private double distance;

    private TravelType travelType;

    public void setTravelType(TravelType travelType) {
        this.travelType = travelType;
    }

    RouteBaseImpl(List<RouteLeg> routeLegs) {
        this.routeLegs = routeLegs;
        for(RouteLeg leg: this.routeLegs) {
            this.distance += leg.getDistance();
        }
    }

    @Override
    public double distance() {
        return this.distance;
    }

    @Override
    public Node getEndNode() {
        return routeLegs.get(routeLegs.size()-1).getEndNode();
    }

    @Override
    public Node getStartNode() {
        return routeLegs.get(0).getStartNode();
    }

    @Override
    public Iterator<RouteLeg> iterator() {
        return this.routeLegs.iterator();
    }

    @Override
    public int size() {
        return this.routeLegs.size();
    }

    @Override
    public TravelType getTravelType() {
        return travelType;
    }
    
}
