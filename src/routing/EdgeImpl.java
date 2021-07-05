package routing;

public class EdgeImpl implements Edge{
    
    private Node start;

    private Node end;

    private double length;

    private Direction direction;

    private TravelType travelType = null;

    private TravelType forbidType = null;
    
    public TravelType getForbidType() {
        return forbidType;
    }

    public void setForbidType(TravelType forbidType) {
        this.forbidType = forbidType;
    }

    public EdgeImpl(Node start, Node end, Direction direction, TravelType forbidType) {
        this.start = start;
        this.end = end;
        this.length = start.getDistance(end);
        this.direction = direction;
        this.forbidType = forbidType;
    }

    EdgeImpl(Node start, Node end, TravelType travelType, Direction direction) {
        this.start = start;
        this.end = end;
        this.travelType = travelType;
        this.direction = direction;
        this.length = start.getDistance(end);
    }
    public TravelType getTravelType() {
        return travelType;
    }

    public void setTravelType(TravelType travelType) {
        this.travelType = travelType;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public boolean allowsTravelType(TravelType tt, Direction dir) {
        if(forbidType==TravelType.ANY) {
            return false;
        } 
        if(tt==TravelType.ANY) {
            return dir == direction;
        } 
        if(travelType==TravelType.ANY) {
            return dir == direction;
        }
        if(forbidType!=null) {
            return tt!=forbidType&&(dir==direction);
        } else {
            return tt==travelType&&dir==direction;
        }
    } 
    

    @Override
    public Node getEnd() {
        return end;
    }

    @Override
    public double getLength() {
        return this.length;
    }

    @Override
    public Node getStart() {
        return start;
    }
    
}
