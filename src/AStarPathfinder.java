import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import bonzai.api.Farmhand;
import bonzai.api.GameState;
import bonzai.api.Position;

public class AStarPathfinder implements Pathfinder {
	private PathNode[][] graph = null;
	private int width;
	private int height;
	private Farmhand farmhand;

	public AStarPathfinder(GameState state, Farmhand farmhand) {
		this.farmhand = farmhand;
		this.width = state.getFarmWidth();
		this.height = state.getFarmHeight();
		this.graph = new PathNode[width][height];
		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				graph[x][y] = new PathNode(x, y, state, farmhand);
			}
		}
		calcAdjacentTiles(state);
	}

	private void calcAdjacentTiles(GameState state) {
		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				graph[x][y].calcAdjacent(state, graph);
			}
		}
	}

	@Override
	public Pathfinder.PathResult nextPathNode(Position startPos,
			Position goalPos, GameState state) {

		clearGraph();
		PathNode goal = graph[goalPos.getX()][goalPos.getY()];
		PathNode start = graph[startPos.getX()][startPos.getY()];

		start.setStart(true);
		goal.setEnd(true);

		List<PathNode> opened = new ArrayList<PathNode>();
		List<PathNode> closed = new ArrayList<PathNode>();

		Set<PathNode> adjacent = start.getAdjacent();
		for (PathNode curAdj : adjacent) {
			curAdj.setParent(start);
			if (!curAdj.isStart()) {
				opened.add(curAdj);
			}
		}

		while (opened.size() > 0) {
			PathNode best = findBestPassThru(goal, opened);
			opened.remove(best);
			closed.add(best);
			if (best.isEnd()) {
				List<PathNode> bestList = new ArrayList<PathNode>();
				double cost = calcBestPath(goal, bestList);
				return new Pathfinder.PathResult(bestList
						.get(bestList.size() - 1).getTile().getPosition(), cost);
			} else {
				Set<PathNode> neighbors = best.getAdjacent();
				for (PathNode curNeighbor : neighbors) {
					if (opened.contains(curNeighbor)) {
						PathNode tmp = new PathNode(curNeighbor.getX(),
								curNeighbor.getY(), state, farmhand);
						tmp.setParent(best);
						if (tmp.getPassThruCost(goal) >= curNeighbor
								.getPassThruCost(goal)) {
							continue;
						}
					}

					if (closed.contains(curNeighbor)) {
						PathNode tmp = new PathNode(curNeighbor.getX(),
								curNeighbor.getY(), state, farmhand);
						tmp.setParent(best);
						if (tmp.getPassThruCost(goal) >= curNeighbor
								.getPassThruCost(goal)) {
							continue;
						}
					}

					curNeighbor.setParent(best);
					opened.remove(curNeighbor);
					closed.remove(curNeighbor);
					opened.add(0, curNeighbor);
				}
			}
		}

		return null;
	}

	private void clearGraph() {
		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				graph[x][y].setStart(false);
				graph[x][y].setEnd(false);
			}
		}
	}

	private PathNode findBestPassThru(PathNode goal, List<PathNode> opened) {
		PathNode best = null;
		for (PathNode cur : opened) {
			if (best == null
					|| cur.getPassThruCost(goal) < best.getPassThruCost(goal)) {
				best = cur;
			}
		}
		return best;
	}

	private double calcBestPath(PathNode node, List<PathNode> bestList) {
		bestList.add(node);
		double cost = 1 + node.getWeight();
		if (!node.getParent().isStart()) {
			cost += calcBestPath(node.getParent(), bestList);
		}
		return cost;
	}
}
