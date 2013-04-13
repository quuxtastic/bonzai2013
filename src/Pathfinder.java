import bonzai.api.GameState;
import bonzai.api.Position;


public interface Pathfinder {
	static class PathResult {
		public PathResult(Position nextNode,double cost) {
			this.nextNode=nextNode;
			this.cost=cost;
		}
		public Position nextNode;
		public double cost;
	}
	
	PathResult nextPathNode(Position start, Position goal,GameState state);
}
