package routing;



import java.util.ArrayList;
import java.util.List;



public class NodeFinderImpl implements NodeFinder{

    private Graph graph;

    List<Node>[][] grid = new List[101][101];

    private double maxlatitude;
    private double maxlongitude;

    private double minlatitude;
    private double minlongitude;

    private double lati_split;
    private double longi_split;

    public NodeFinderImpl() {}

    public NodeFinderImpl(Graph graph) {
        this.graph = graph;
        for(int i = 0; i < 101; i++) {
            for(int j = 0;j < 101; j++) {
                grid[i][j] = new ArrayList<>();
            }
        }
        this.maxlatitude = graph.getMaxLatitude();
        this.maxlongitude = graph.getMaxLongitude();
        this.minlatitude = graph.getMinLatitude();
        this.minlongitude = graph.getMinLongitude();
        this.lati_split = (maxlatitude - minlatitude) / 100;
        this.longi_split = (maxlongitude - minlongitude) / 100;
        for(Node n : graph) {
            Coordinate c = n.getCoordinate();
            int[] index = getGridIndex(c);
            this.grid[index[0]][index[1]].add(n);
        }
    }
    

    public Graph getGraph() {
        return graph;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }


    @Override
    public int[] getGridIndex(Coordinate c) {
        double x = c.getLatitude();
        double y = c.getLongitude();
        int[] index = new int[2];
        index[0] = Math.max(Math.min((int)((x-minlatitude)/lati_split),100),0);
        index[1] = Math.max(Math.min((int)((y-minlongitude)/longi_split),100),0);
        return index;
    }


    @Override
    public Node getNodeForCoordinates(Coordinate c) {
        double distance = Double.MAX_VALUE;
        Node ret = null;
        int[] cell = getGridIndex(c);
        boolean flag = false;
        int i = 0, j = 0, k = 0;
        boolean[][] visited = new boolean[101][101];
        if(grid[cell[0]][cell[1]].size()==0) {
            for(k=1;k<100;k++) {
                for(i=Math.max(cell[0]-k-1,0);i<=Math.min(cell[0]+k+1,100);i++) {
                    for(j=Math.max(cell[1]-k-1,0);j<=Math.min(cell[1]+k+1,100);j++) {
                        if(grid[i][j].size()==0||visited[i][j]) {
                            visited[i][j] = true;
                            continue;
                        }
                        else {
                            visited[i][j] = true;
                            for(Node node : grid[i][j]) {
                                double dis = c.getDistance(node.getCoordinate());
                                if(Double.compare(distance, dis)>0) {
                                    distance = dis;
                                    ret = node;
                                    flag = true;
                                }
                            }
                        }
                    }
                }
                if(flag) break;
            }
        } else {
            visited[cell[0]][cell[1]] = true;
            for(Node node : grid[cell[0]][cell[1]]) {
                double dis = c.getDistance(node.getCoordinate());
                if(Double.compare(distance, dis)>0) {
                    distance = dis;
                    ret = node;
                }
            }
        }
        CoordinateBox bbx = c.computeBoundingBox(distance);
        Coordinate lowerBound = bbx.getLowerBound();
        Coordinate upperBound = bbx.getUpperBound();
        int index1[] = getGridIndex(lowerBound);
        int index2[] = getGridIndex(upperBound);
        int[] spend = new int[2];
        if(this.longi_split <= this.lati_split ) {
            spend[0] = 2;
            spend[1] = (int)(this.lati_split/this.longi_split) + 3;
        } else {
            spend[0] = (int)(this.longi_split/this.lati_split) + 2;
            spend[1] = 3;
        }
        for(i=Math.max(index1[0]-spend[0],0);i<=Math.min(index2[0]+spend[0],100);i++) {
            for(j=Math.max(index1[1]-spend[1],0);j<=Math.min(index2[1]+spend[1],100);j++) {
                if(grid[i][j].size()==0||visited[i][j]) continue;
                visited[i][j] = true;
                for(Node node : grid[i][j]) {
                    double dis = c.getDistance(node.getCoordinate());
                    if(Double.compare(distance, dis)>0) {
                        distance = dis;
                        ret = node;
                    }
                }
            }
        }
        return ret;
    }
}
