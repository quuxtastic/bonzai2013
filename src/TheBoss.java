import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

//import Pathfinder.PathResult;
import bonzai.api.Duck;
import bonzai.api.Farmhand;
import bonzai.api.FarmhandAction;
import bonzai.api.GameState;
import bonzai.api.Position;
import bonzai.api.list.FarmhandList;


public class TheBoss {

	public TheBoss(GameState state, Pathfinder pathfinder){
		this.states = new HashMap<Integer,STATES>();
		this.pathfinder = pathfinder;
		for (Integer i = 0;i<state.getMyFarmhands().size();++i)
			states.put(i, STATES.NOSTATE);		
	}//end constructor

	public void process(GameState state){
		FarmhandList hands = state.getMyFarmhands();
		Collection<FarmhandAction> actions = new ArrayList<FarmhandAction>(); 
		for(int i = 0;i<hands.size(); ++i){
			if(states.get(i) == null || states.get(i) == STATES.NOSTATE){
				//TODO:Calculate closest duck vs. closest egg
				
				//otherwise doBaleing
				
			}else if(states.get(i) == STATES.DUCKING)
				System.out.println("yourmom");
		}
	}//end process

	private FarmhandAction doDucking(Integer i, GameState state){
		Farmhand hand = state.getMyFarmhands().get(i);
		if(hand.getHeldObject() instanceof Duck){
			/* Farmhand has duck, run home */
			//FIXME: If adjacent
			Pathfinder.PathResult p = pathfinder.nextPathNode(hand.getPosition(),state.getMyBase().getPosition(),state);
			return hand.move(p.nextNode);
		}else{ /* Get that duck*/
			/*If you're by a duck, you get that duck*/						
			if(true){
				//FIXME - MIKE: If adjacent
			}
			if(!targets.containsKey(i)){
				/*aquire new target*/
				targets.put(i,getClosestDuck(hand, state));
			}				
			/* Continue moving towards the target*/
			Pathfinder.PathResult p = pathfinder.nextPathNode(hand.getPosition(),targets.get(i).getPosition(),state);
			return hand.move(p.nextNode);
		}		
	}

	private void doEgging(){

	}

	private void doBaleing(){

	}

	private void doTaunting(){
		//TODO: taunt everyone
	}

	/**
	 * Gets the closest un-targeted duck
	 * 
	 * @param hand
	 * @param state
	 * @return
	 */
	private Duck getClosestDuck(Farmhand hand, GameState state){
		Duck bestDuck = null;
		Position pos = hand.getPosition(); //Hey! Where's your hand?
		for(Duck d:state.getMyDucks()){
			Position duckPosition = d.getPosition();
		}
		return null;
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
	private Map<Integer,Duck> targets;
	private Pathfinder pathfinder;
}