import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import bonzai.api.GameState;
import bonzai.api.Position;

public class AStarPathfinder implements Pathfinder {
	private PathNode[][] graph=null;
	private int width;
	private int height;
	
	public AStarPathfinder(GameState state) {
		this.width=state.getFarmWidth();
		this.height=state.getFarmHeight();
		this.graph=new PathNode[width][height];
		for(int x=0;x<width;++x) {
			for(int y=0;y<height;++y) {
				graph[x][y]=new PathNode(x,y,state);
			}
		}
		for(int x=0;x<width;++x) {
			for(int y=0;y<height;++y) {
				graph[x][y].calcAdjacent(state,graph);
			}
		}
	}
	
	@Override
	public Pathfinder.PathResult nextPathNode(Position startPos,Position goalPos,GameState state) {
		clearGraph();
		graph[startPos.getX()][startPos.getY()].setStart(true);
		graph[goalPos.getX()][goalPos.getY()].setEnd(true);
		
		PathNode goal=graph[goalPos.getX()][goalPos.getY()];
		
		List<PathNode> opened=new ArrayList<PathNode>();
		List<PathNode> closed=new ArrayList<PathNode>();
		
		Set<PathNode> adjacent=graph[startPos.getX()][startPos.getY()].getAdjacent();
		for(PathNode curAdj:adjacent) {
			curAdj.setParent(graph[startPos.getX()][startPos.getY()]);
			if(!curAdj.isStart()) {
				opened.add(curAdj);
			}
		}
		
		while(opened.size()>0) {
			PathNode best=findBestPassThru(goal,opened);
			opened.remove(best);
			closed.add(best);
			if(best.isEnd()) {
				return calcBestPath(goal,opened);
			} else {
				Set<PathNode> neighbors=best.getAdjacent();
				for(PathNode curNeighbor:neighbors) {
					if(opened.contains(curNeighbor)) {
						PathNode tmp=new PathNode(curNeighbor.getX(),curNeighbor.getY(),state);
						tmp.setParent(best);
						if(tmp.getPassThruCost(goal)>=curNeighbor.getPassThruCost(goal)) {
							continue;
						}
					}
					
					if(closed.contains(curNeighbor)) {
						PathNode tmp=new PathNode(curNeighbor.getX(),curNeighbor.getY(),state);
						tmp.setParent(best);
						if(tmp.getPassThruCost(goal)>=curNeighbor.getPassThruCost(goal)) {
							continue;
						}
					}
					
					curNeighbor.setParent(best);
					opened.remove(curNeighbor);
					closed.remove(curNeighbor);
					opened.add(0,curNeighbor);
				}
			}
		}
		
		return null;
	}
	
	private void clearGraph() {
		for(int x=0;x<width;++x) {
			for(int y=0;y<height;++y) {
				graph[x][y].setStart(false);
				graph[x][y].setEnd(false);
			}
		}
	}
	
	private PathNode findBestPassThru(PathNode goal,List<PathNode> opened) {
		PathNode best=null;
		for(PathNode cur:opened) {
			if(best==null||cur.getPassThruCost(goal)<best.getPassThruCost(goal)) {
				best=cur;
			}
		}
		return best;
	}
	
	private PathResult calcBestPath(PathNode goal,List<PathNode> opened) {
		double cost=0.0;
		PathNode cur=goal;
		while(!cur.getParent().isStart()) {
			cost+=cur.getPassThruCost(goal);
			cur=cur.getParent();
		}
		return new PathResult(cur.getTile().getPosition(),cost);
	}
}
