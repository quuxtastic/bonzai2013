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
		// first-time setup
		width=state.getFarmWidth();
		height=state.getFarmHeight();
		graph=new PathNode[width][height];
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
	public PathResult nextPathNode(Position start,Position goal) {
		clearGraph();
		graph[start.getX()][start.getY()].setStart(true);
		graph[goal.getX()][goal.getY()].setEnd(true);
		
		List<PathNode> opened=new ArrayList<PathNode>();
		List<PathNode> closed=new ArrayList<PathNode>();
		List<PathNode> bestPath=new ArrayList<PathNode>();
		
		Set<PathNode> adjacent=graph[start.getX()][start.getY()].getAdjacent();
		for(PathNode curAdj:adjacent) {
			curAdj.setParent(graph[start.getX()][start.getY()]);
			if(!curAdj.isStart()) {
				opened.add(curAdj);
			}
		}
		
		while(opened.size()>0) {
			//PathNode best=findBestPassThru();
			//
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
}
