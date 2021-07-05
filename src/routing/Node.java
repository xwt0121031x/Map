package routing;

import java.util.Iterator;
import java.util.List;


/**
 * Nodes are next to edges the building blocks of a graph. In this project they
 * contain information about the outgoing edges but also about their OSM id and
 * coordinates.
 */
public interface Node extends Iterable<Edge> {


	class NodeInfo {
		private double F;

		private double G;

		private double H;	

		private Node pre;

		private boolean canSearch=false;

		public void updateFG(double g) {
			G = g;
			F = G + H;
		}


		public NodeInfo(double h, double g, Node pre) {
			H = h;
			G = g;
			F = g + h;
			this.pre = pre;
			canSearch=true;
		}

	
		public NodeInfo(double f, double g, double h, Node pre) {
			F = f;
			G = g;
			H = h;
			this.pre = pre;
			this.canSearch = true;
		}

		public boolean isCanSearch() {
			return canSearch;
		}

		public void setCanSearch(boolean canSearch) {
			this.canSearch = canSearch;
		}

		public Node getPre() {
			return pre;
		}

		public void setPre(Node pre) {
			this.pre = pre;
		}

		public NodeInfo() {
		}

		public double getF() {
			return F;
		}

		public void setF(double f) {
			F = f;
		}

		public double getG() {
			return G;
		}

		public void setG(double g) {
			G = g;
		}

		public double getH() {
			return H;
		}

		public void setH(double h) {
			H = h;
		}
	}

	/**
	 * 
	 * @param nodeInfo
	 */
	public void setNodeInfo(NodeInfo nodeInfo);

	/**
	 * @return The coordinates of this node.
	 */
	public Coordinate getCoordinate();

	/**
	 * Get an outgoing edge of this node.
	 * 
	 * @param idx
	 *            The edge index.
	 * 
	 * @return The "idx'th" outgoing edge if it exists.
	 * 
	 * @throws IndexOutOfBoundsException
	 *             If idx is not a valid edge index.
	 */
	public Edge getEdge(int idx);

	/**
	 * @return The OSM id of this node.
	 */
	public long getId();

	/**
	 * @return An iterator over all outgoing edges of this node.
	 */
	@Override
	public Iterator<Edge> iterator();

	/**
	 * @return The number of outgoing edges of this node.
	 */
	public int numEdges();

	/**
	 * Add an outgoing edge to this node.
	 * 
	 * @param e
	 *            The edge to add.
	 */
	public void addEdge(Edge e);

	/**
	 * Remove an outgoing edge from this node.
	 * 
	 * @param i The index of the edge that should be removed.
	 */
	public void removeEdge(int i);

	public List<Edge> getEdges();

    public void setEdges(List<Edge> edges);

	public NodeInfo getNodeInfo();

	public double getDistance(Node node);
}
