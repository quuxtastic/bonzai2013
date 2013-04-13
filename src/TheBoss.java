import java.util.HashMap;
import java.util.Map;

import bonzai.api.Entity;
import bonzai.api.Farmhand;
import bonzai.api.GameState;
import bonzai.api.Item;
import bonzai.api.Tile;
import bonzai.api.list.FarmhandList;


public class TheBoss {
	
	public TheBoss(GameState state){
		this.states = new HashMap<Integer,STATES>();
		this.pathfinder = new AStarPathfinder(state);
		for (Integer i = 0;i<state.getMyFarmhands().size();++i)
			states.put(i, STATES.NOSTATE);		
	}//end constructor
	
	public void process(GameState state){
		FarmhandList hands = state.getMyFarmhands();
		
	}
	
	private void doDucking(){
		
	}
	
	private void doEgging(){
		
	}
	
	private void doBaleing(Integer i, GameState gs){
		Tile myBase = gs.getMyBase();
		Farmhand fh = gs.getFarmhands().get(i);
		Entity holding = fh.getHeldObject();
		Item item = null;
		if(holding instanceof Item){
			item = (Item) holding;
		}
		// do I have a pitchfork?
		if(item != null && item.getType() == Item.Type.Pitchfork){
			// do I have a bale?
			if(item.getFull()){
				
			}
		}else{
			//Am I at the base?
			if(isAdjacent(myBase.getX(), myBase.getY(), fh.getX(), fh.getY())){
				//Am I holding something? Try to sell it
			}
		}
			
			
		
	}
	
	private void doTaunting(){
		
	}
	
	private void doRetreat(){
		
	}
	
	public boolean isAdjacent(int x1, int y1, int x2, int y2){
		return Math.abs(x1-x2) <=1 && Math.abs(y1-y2) <=1;
	}
	
	private enum STATES{
		NOSTATE,
		DUCKING,
		EGGING,
		BALEING,
		TUANTING,
		SELLING
		}
	
	private Map<Integer,STATES> states;
	private Pathfinder pathfinder;
}