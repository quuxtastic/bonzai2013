import java.util.HashSet;
import java.util.Set;

import bonzai.api.GameState;
import bonzai.api.Tile;


public class PathNode {
	public PathNode(int x, int y,GameState state) {
		this.x=x;
		this.y=y;
		this.tile=state.getTile(x, y);
	}

	private final int x, y;
	private final Tile tile;
	private boolean start=false,end=false;
	
	private double localCost=0.0;
	private double parentCost=0.0;
	
	private Set<PathNode> adjacent=new HashSet<PathNode>();
	private PathNode parent=null;
	
	public int getX() { return x; }
	public int getY() { return y; }
	
	public Tile getTile() { return tile; }
	
	public Set<PathNode> getAdjacent() {
		return adjacent;
	}
	
	public boolean isStart() { return start; }
	public void setStart(boolean start) { this.start=start; }
	public boolean isEnd() { return end; }
	public void setEnd(boolean end) { this.end=end; }
	
	public PathNode getParent() { return parent; }
	public void setParent(PathNode parent) { this.parent=parent; }
	
	public void calcAdjacent(GameState game,PathNode[][] graph) {
		for(int curY=0;curY<3;++curY){
			for(int curX=0;curX<3;++curX) {
				if(curY==1&&curX==1) continue;
				
				if(curX<graph.length&&curY<graph[curX].length) {
					if(graph[curX][curY].getTile().getTileState()!=Tile.State.Water) {
						this.adjacent.add(graph[curX][curY]);
						graph[curX][curY].adjacent.add(this);
					}
				}
			}
		}
	}
	
	public double getLocalCost(PathNode goal) {
		if(isStart()) {
			return 0.0;
		}
		double tileWeight=0.0;
		if(tile.getTileState()==Tile.State.Mud) tileWeight=1.0;
		localCost= 1.0*(Math.abs(x-goal.getX())+Math.abs(y-goal.getY())) + tileWeight;
		return localCost;
	}
	public double getParentCost() {
		if(isStart()) return 0.0;
		if(parentCost==0.0) {
			parentCost=1.0+.5*(parent.getParentCost()-1.0);
		}
		return parentCost;
	}
	public double getPassThruCost(PathNode goal) {
		if(isStart()) {
			return 0.0;
		}
		return getLocalCost(goal)+getParentCost();
	}
}
