<<<<<<< HEAD
import java.util.ArrayList;
import java.util.Collection;
=======
>>>>>>> 923b1a9a6fe0c4d82846793d389c5358e56bdb07
import java.util.HashMap;
import java.util.Map;

import bonzai.api.Duck;
import bonzai.api.Entity;
import bonzai.api.Farmhand;
import bonzai.api.FarmhandAction;
import bonzai.api.GameState;
import bonzai.api.Item;
import bonzai.api.Position;
import bonzai.api.Tile;
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
				
			}else if(states.get(i) == STATES.DUCKING)
		}
	}//end process

	private FarmhandAction doDucking(Integer i, GameState state){
		Farmhand hand = state.getMyFarmhands().get(i);
		if(hand.getHeldObject() instanceof Duck){
			/* Farmhand has duck, run home */
			//FIXME: If adjacent
<<<<<<< HEAD
			Pathfinder.PathResult p = pathfinder.nextPathNode(hand.getPosition(),state.getMyBase().getPosition(),state);
=======
			Pathfinder.PathResult p = pathfinder.nextPathNode(hand.getPosition(),state.getMyBase().getPosition(), state);
>>>>>>> 923b1a9a6fe0c4d82846793d389c5358e56bdb07
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
<<<<<<< HEAD
			Pathfinder.PathResult p = pathfinder.nextPathNode(hand.getPosition(),targets.get(i).getPosition(),state);
=======
			Pathfinder.PathResult p = pathfinder.nextPathNode(hand.getPosition(),targets.get(i).getPosition(), state);
>>>>>>> 923b1a9a6fe0c4d82846793d389c5358e56bdb07
			return hand.move(p.nextNode);
		}		
	}

	private void doEgging(){

	}
	
	private FarmhandAction doBaleing(Integer i, GameState gs){
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
				//Am I holding something? Try to sell it( or put the duck down)
				if(item != null){
					return fh.sell();
				}else if(holding != null){
					// I've got a duck
					fh.dropItem(new Position(myBase.getX(), myBase.getY()));
				}
			}
		}
		return fh.shout("I'M STUPID");
	}

	private boolean isAdjacent(int x, int y, int x2, int y2) {
		return Math.abs(x-x2) <=1 && Math.abs(y-y2) <=1;
	}

	private void doTaunting(){

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