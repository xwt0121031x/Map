package routing;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Factory {

	/**
	 * Create a graph from the description in a .nae file.
	 *
	 * @param fileName
	 *            A path to an NAE file.
	 *
	 * @return The graph as described in the .nae file.
	 *
	 * @throws IOException
	 *             If an Input/Output error occurs.
	 */
	public static Graph createGraphFromMap(String fileName) throws IOException {

		fileName = System.getProperty("user.home") + "\\Desktop\\map\\src\\routing\\" + fileName;
		Map<Long, Node> nodesMap = new HashMap<Long, Node>();
		List<Edge> edges = new ArrayList<Edge>();
		FileReader fr = new FileReader(fileName);
		BufferedReader br = new BufferedReader(fr);
		StringBuffer sb = new StringBuffer();
		String str;
		while((str=br.readLine())!=null){
			String[] a = str.split(" ");
			//Node
			if(a[0].equals("N")) {
				long id = Long.valueOf(a[1]);
				Coordinate coordinate = new Coordinate(Double.valueOf(a[2]), Double.valueOf(a[3]));
				List<Edge> t_edges = new ArrayList<Edge>();
				NodeImpl node = new NodeImpl(id, coordinate, t_edges);
				nodesMap.put(id, node);
			//Edge
			} else {
				Node A = nodesMap.get( Long.valueOf(a[1]) );
				Node B = nodesMap.get( Long.valueOf(a[2]));
				StringBuilder edgeInfo = new StringBuilder();
				for(int i=3;i<a.length;i++) {
					edgeInfo.append(a[i]);
				}
				Integer edgeInfoDigit = Integer.parseInt(edgeInfo.toString(), 2);
				EdgeImpl[] es = new EdgeImpl[2];
				for(int i=0;i<2;i++) {
					if((edgeInfoDigit&0b010101)==0b010101) {
						if(i==0) 
							es[1-i] = new EdgeImpl(B, A, TravelType.ANY, Direction.FORWARD);
						else 
							es[1-i] = new EdgeImpl(A, B, TravelType.ANY, Direction.FORWARD);
					} else if((edgeInfoDigit&0b010101)==0b010100) {
						if(i==0)
							es[1-i] = new EdgeImpl(B, A, Direction.FORWARD, TravelType.FOOT);
						else
							es[1-i] = new EdgeImpl(A, B, Direction.FORWARD, TravelType.FOOT);

					} else if((edgeInfoDigit&0b010101)==0b010001) {
						if(i==0)
							es[1-i] = new EdgeImpl(B, A, Direction.FORWARD, TravelType.BIKE);
						else 
							es[1-i] = new EdgeImpl(A, B, Direction.FORWARD, TravelType.BIKE);
					} else if((edgeInfoDigit&0b010101)==0b010000) {
						if(i==0)
							es[1-i] = new EdgeImpl(B, A, TravelType.CAR, Direction.FORWARD);
						else 
							es[1-i] = new EdgeImpl(A, B, TravelType.CAR, Direction.FORWARD);
					} else if((edgeInfoDigit&0b010101)==0b000101) {
						if(i==0)
							es[1-i] = new EdgeImpl(B, A, Direction.FORWARD, TravelType.CAR);
						else 
							es[1-i] = new EdgeImpl(A, B, Direction.FORWARD, TravelType.CAR);

					} else if((edgeInfoDigit&0b010101)==0b000100) {
						if(i==0)
							es[1-i] = new EdgeImpl(B, A, TravelType.BIKE, Direction.FORWARD);
						else
							es[1-i] = new EdgeImpl(A, B, TravelType.BIKE, Direction.FORWARD);
					} else if((edgeInfoDigit&0b010101)==0b000001) {
						if(i==0)
							es[1-i] = new EdgeImpl(B, A, TravelType.FOOT, Direction.FORWARD);
						else
							es[1-i] = new EdgeImpl(A, B, TravelType.FOOT, Direction.FORWARD);
					} else if((edgeInfoDigit&0b010101)==0b000000) {
						if(i==0)
							es[1-i] = new EdgeImpl(B, A, Direction.FORWARD, TravelType.ANY);
						else
							es[1-i] = new EdgeImpl(A, B, Direction.FORWARD, TravelType.ANY);
					}
					edgeInfoDigit >>= 1;
				}
				A.addEdge(es[0]);
				B.addEdge(es[1]);
				edges.add(es[0]);
				edges.add(es[1]);
			}
		}
		br.close();
		fr.close();
		Graph g = new GraphImpl(nodesMap, edges);
		return g;
	}

	/**
	 * Return a node finder algorithm for the graph g. The graph argument allows
	 * the node finder to build internal data structures.
	 *
	 * @param g
	 *            The graph the nodes are looked up in.
	 * @return A node finder algorithm for that graph.
	 */
	public static NodeFinder createNodeFinder(Graph g) {
		return new NodeFinderImpl(g);
	}

	/**
	 * == BONUS ==
	 *
	 * Compute the overlay graph (or junction graph).
	 *
	 * Note: This is part of a bonus exercise, not of the regular project.
	 *
	 * @return The overlay graph for the given graph g.
	 */
	public static Graph createOverlayGraph(Graph g) {
		// TODO: Implement me.
		return null;
	}

	/**
	 * Return a routing algorithm for the graph g. This allows to inspect the
	 * graph and choose from different routing strategies if appropriate.
	 *
	 * @param g
	 *            The graph the routing is performed on.
	 * @return A routing algorithm suitable for that graph.
	 */
	public static RoutingAlgorithm createRoutingAlgorithm(Graph g) {
		return new RoutingAlgorithmImpl(g);
	}

}
