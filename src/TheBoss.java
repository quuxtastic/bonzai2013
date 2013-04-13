import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import Pathfinder.PathResult;
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
	List<FarmhandState> farmhandStates = new ArrayList<FarmhandState>();

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
			if(isAdjacent(hand.getPosition(),state.getMyBase().getPosition()))
				hand.dropItem(state.getMyBase().getPosition());
			Pathfinder.PathResult p = pathfinder.nextPathNode(hand.getPosition(),state.getMyBase().getPosition(),state);
			return hand.move(p.nextNode);
		}else{ 
			/* Michael, Get that duck*/
			Duck closestDuck = getClosestDuck(hand, state);
			if(closestDuck == null){
				/*what duck? I don't see a duck?*/
				return null;				
			}else if(isAdjacent(hand.getPosition(), closestDuck.getPosition())){
				/*If you're by a duck, you get that duck*/
				return hand.pickUp(closestDuck);
			} else if(!targets.containsKey(i)){
				/*otherwise aquire new target*/
				targets.put(i,getClosestDuck(hand, state));
			}				
			
			/* Continue moving towards the target*/
			Pathfinder.PathResult p = pathfinder.nextPathNode(hand.getPosition(),targets.get(i).getPosition(),state);
			return hand.move(p.nextNode);
		}		
	}

	private void doEgging(){

	}
	
	private FarmhandAction doBaleing(Integer fhIndex, GameState gs){
		Tile myBase = gs.getMyBase();
		Farmhand fh = gs.getFarmhands().get(fhIndex);
		Entity holding = fh.getHeldObject();
		Item item = null;
		if(holding instanceof Item){
			item = (Item) holding;
		}
		// do I have a pitchfork?
		if(item != null && item.getType().equals(Item.Type.Pitchfork)){
			// do I have a bale?
			if(item.getFull()){
				//go home kid
				return fh.move(pathfinder.nextPathNode(fh.getPosition(), myBase.getPosition(), gs).nextNode);
			}else{
				//1800getabale
				for(int i = 0; i < gs.getFarmWidth(); i++){
					for(int j = 0; j < gs.getFarmHeight(); j++){
						if(gs.getTile(j, i).getTileState().equals(Tile.State.Straw)){
							return fh.move(pathfinder.nextPathNode(fh.getPosition(), myBase.getPosition(), gs).nextNode);
						}
					}
				}
				// go on a walk to find a bale
				
			}
		}else{
			//Am I at the base?
			if(isAdjacent(myBase.getPosition(), fh.getPosition())){
				//Am I holding something? Try to sell it( or put the duck down)
				if(item != null){
					return fh.sell();
				}else if(holding != null){
					// I've got a duck
					return fh.dropItem(new Position(myBase.getX(), myBase.getY()));
				}else{
					//dude, youre getting a pitchfork
					return fh.purchase(Item.Type.Pitchfork);
				}
			}else{
				// return to base
				return fh.move(pathfinder.nextPathNode(fh.getPosition(), myBase.getPosition(), gs).nextNode);
			}
		}
		return fh.shout("I'M STUPID");
	}

	private boolean isAdjacent(Position p1, Position p2){
		return isAdjacent(p1.getX(),p1.getY(), p2.getX(),p2.getY());
	}
	
	private boolean isAdjacent(int x, int y, int x2, int y2) {
		return Math.abs(x-x2) <= 1 && Math.abs(y-y2) <=1;
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