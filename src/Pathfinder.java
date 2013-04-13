import bonzai.api.Position;


public interface Pathfinder {
	static class PathResult {
		public Position nextNode;
		public double cost;
	}
	
	PathResult nextPathNode(Position start, Position goal);
}
