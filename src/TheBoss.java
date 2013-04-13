import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import bonzai.api.Farmhand;
import bonzai.api.GameState;
import bonzai.api.list.FarmhandList;


public class TheBoss {
	
	public TheBoss(GameState state){
		this.states = new HashMap<Integer,STATES>();
		this.pathfinder = new Pathfinder();
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
	
	private void doBaleing(){
		
	}
	
	private void doTaunting(){
		
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