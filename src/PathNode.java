import java.util.HashSet;
import java.util.Set;

import bonzai.api.Farmhand;
import bonzai.api.GameState;
import bonzai.api.Tile;

public class PathNode {
	public PathNode(int x, int y, GameState state, Farmhand farmhand) {
		this.x = x;
		this.y = y;
		this.tile = state.getTile(x, y);
		this.state = state;
		this.farmhand = farmhand;
	}

	// dude who want to find his way
	private Farmhand farmhand;
	private final int x, y;
	private final Tile tile;
	private final GameState state;
	private boolean start = false, end = false;

	private double localCost = 0.0;
	private double parentCost = 0.0;

	private Set<PathNode> adjacent = new HashSet<PathNode>();
	private PathNode parent = null;

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Tile getTile() {
		return tile;
	}

	public Set<PathNode> getAdjacent() {
		return adjacent;
	}

	public boolean isStart() {
		return start;
	}

	public void setStart(boolean start) {
		this.start = start;
	}

	public boolean isEnd() {
		return end;
	}

	public void setEnd(boolean end) {
		this.end = end;
	}

	public PathNode getParent() {
		return parent;
	}

	public void setParent(PathNode parent) {
		this.parent = parent;
	}

	public void calcAdjacent(GameState game, PathNode[][] graph) {
		if (!canWalkOn())
			return;

		for (int offY = -1; offY <= 1; ++offY) {
			for (int offX = -1; offX <= 1; ++offX) {
				int curX = x + offX;
				int curY = y + offY;

				if (curX < graph.length && curX >= 0
						&& curY < graph[curX].length && curY >= 0) {
					if (graph[curX][curY].canWalkOn()) {
						this.adjacent.add(graph[curX][curY]);
						graph[curX][curY].adjacent.add(this);
					}
				}
			}
		}
	}

	public boolean canWalkOn() {
		if (!tile.canFarmhandCross())
			return false;
		for (Farmhand fh : state.getFarmhands().getVisible()) {
			if (fh.equals(farmhand))
				continue;
			if (tile.getPosition().equals(fh.getPosition()))
				return false;
		}
		return true;
	}

	public double getWeight() {
		if (tile.getTileState() == Tile.State.Mud)
			return 1.0;
		else
			return 0.0;
	}

	public double getLocalCost(PathNode goal) {
		if (isStart()) {
			return 0.0;
		}
		localCost = 1.0
				* (Math.abs(x - goal.getX()) + Math.abs(y - goal.getY()))
				+ getWeight();
		return localCost;
	}

	public double getParentCost() {
		if (isStart())
			return 0.0;
		if (parentCost == 0.0) {
			parentCost = 1.0 + .5 * (parent.getParentCost() - 1.0);
		}
		return parentCost;
	}

	public double getPassThruCost(PathNode goal) {
		if (isStart()) {
			return 0.0;
		}
		return getLocalCost(goal) + getParentCost();
	}
}
