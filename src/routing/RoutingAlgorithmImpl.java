package routing;

import java.util.*;

import routing.Node.NodeInfo;

public class RoutingAlgorithmImpl implements RoutingAlgorithm {

    Graph g = null;

    RoutingAlgorithmImpl(Graph g) {
        this.g = g;
    }

    @Override
    public Route computeRoute(Graph g, List<Node> nodes, TravelType tt) throws NoSuchRouteException {
        List<RouteLeg> routeLegs = new ArrayList<RouteLeg>();
        for(int i = 0; i < nodes.size() - 1; i++) {
            RouteLeg leg = computeRouteLeg(g, nodes.get(i), nodes.get(i+1), tt);
            ((RouteLegBaseImpl)leg).setTravelType(tt);
            routeLegs.add(leg);
        }
        RouteBaseImpl rbi = new RouteBaseImpl(routeLegs);
        rbi.setTravelType(tt);
        return rbi;
    }

    @Override
    public RouteLeg computeRouteLeg(Graph g, long startId, long endId, TravelType tt) throws NoSuchRouteException {
        Node start_node = g.getNode(startId);
        Node end_node = g.getNode(endId);
        if(start_node==null||end_node==null) throw new NoSuchRouteException();
        return computeRouteLeg(g, start_node, end_node, tt);
    }

    @Override
    public RouteLeg computeRouteLeg(Graph G, Node start_node, Node end_node, TravelType TT) throws NoSuchRouteException {
        Set<Node> closed = new HashSet<>(); // closeset
        Queue<Node> queue = new PriorityQueue<Node>(new Comparator<Node>(){
            @Override
            public int compare(Node o1, Node o2) {
                if(Double.compare(o1.getNodeInfo().getF(),o2.getNodeInfo().getF())==0) {
                    return 0;
                }else if(Double.compare(o1.getNodeInfo().getF(),o2.getNodeInfo().getF()) > 0) {
                    return 1;
                }else {
                    return -1;
                }
            }
        }); //openset
        boolean flag = false;
        double start_h = start_node.getDistance(end_node);
        NodeInfo start_info = new NodeInfo(start_h, 0, null);
        start_node.setNodeInfo(start_info);
        queue.offer(start_node);
        while(!queue.isEmpty()) {
            if(queue.contains(end_node)) {
                flag = true;
                break;
            }
            Node curNode = queue.poll();
            closed.add(curNode);
            for(Edge e : curNode.getEdges()) {
                if(e.getStart()==curNode&&e.allowsTravelType(TT, Direction.FORWARD)){
                    Node neighbor = e.getEnd();
                    if(closed.contains(neighbor)) {continue;}
                    if(!queue.contains(neighbor)) {
                        double h = neighbor.getDistance(end_node);
                        double g = curNode.getNodeInfo().getG() + neighbor.getDistance(curNode);
                        NodeInfo info = new NodeInfo(h,g,curNode);
                        neighbor.setNodeInfo(info);
                        queue.add(neighbor);
                    }else {
                        double g = curNode.getNodeInfo().getG() + neighbor.getDistance(curNode);
                        if(Double.compare(g, neighbor.getNodeInfo().getG())<0) {
                            queue.remove(neighbor);
                            neighbor.getNodeInfo().updateFG(g);
                            neighbor.getNodeInfo().setPre(curNode);
                            queue.add(neighbor);
                        }
                    }
                }
            }
        }
        if(!flag) throw new NoSuchRouteException();
        Stack<Node> stack = new Stack();
        Node cur = end_node;
        while(cur!=null) {
            stack.push(cur);
            cur = cur.getNodeInfo().getPre();
        }
        List<Node> path = new ArrayList<>();
        while(!stack.isEmpty()) {
            path.add(stack.pop());
        }
        RouteLeg leg = new RouteLegBaseImpl(path, end_node.getNodeInfo().getF()/2);
        return leg;
    }


    @Override
    public boolean isBidirectional() {
        return false;
    }
}
