import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

//import Pathfinder.PathResult;
import bonzai.api.Duck;
import bonzai.api.Entity;
import bonzai.api.Farmhand;
import bonzai.api.FarmhandAction;
import bonzai.api.GameState;
import bonzai.api.Item;
import bonzai.api.Item.Type;
import bonzai.api.Position;
import bonzai.api.Tile;
import bonzai.api.list.FarmhandList;


public class TheBoss {

	public TheBoss(GameState state, Pathfinder pathfinder){
		this.states = new HashMap<Integer,STATES>();
		this.pathfinder = pathfinder;
		for (Integer i = 0;i<state.getMyFarmhands().size();++i)
			states.put(i, STATES.CHILLIN);		
	}//end constructor

	public Collection<FarmhandAction> process(GameState state){
		FarmhandList hands = state.getMyFarmhands();
		Collection<FarmhandAction> actions = new ArrayList<FarmhandAction>(); 
		for(int i = 0;i<hands.size(); ++i){
			FarmhandAction fa;
			if(states.get(i) == null || states.get(i) == STATES.CHILLIN){
				//TODO:Calculate closest duck vs. closest egg
				
				//otherwise doBaleing
				
			}else if(states.get(i) == STATES.DUCKING){
			//	 = doDucking(i, state);
				actions.add(doDucking(i, state));
			}
			
			actions.add(fa);
		}
		return actions;
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
				targets.put(i,closestDuck);
			}				
			
			/* Continue moving towards the target*/
			Pathfinder.PathResult p = pathfinder.nextPathNode(hand.getPosition(),targets.get(i).getPosition(),state);
			return hand.move(p.nextNode);
		}		
	}

	private FarmhandAction seekItem(Integer i, GameState state,Type itemType){
		Farmhand hand = state.getMyFarmhands().get(i);
		if(hand.getHeldObject() instanceof Item && ((Item)hand.getHeldObject()).getType().equals(itemType)){
			/* Farmhand has item, run home */
			if(isAdjacent(hand.getPosition(),state.getMyBase().getPosition()))
				hand.dropItem(state.getMyBase().getPosition());
			Pathfinder.PathResult p = pathfinder.nextPathNode(hand.getPosition(),state.getMyBase().getPosition(),state);
			return hand.move(p.nextNode);
		}else{ 
			/* Michael, Get that item*/
			Item closestItem = getClosestItem(hand, state,itemType);
			if(closestItem == null){
				/*what item? I don't see a item?*/
				return null;				
			}else if(isAdjacent(hand.getPosition(), closestItem.getPosition())){
				/*If you're by a item, you get that item*/
				return hand.pickUp(closestItem);
			} else if(!targets.containsKey(i)){
				/*otherwise aquire new target*/
				itemTargets.put(i,closestItem);
			}				
			
			/* Continue moving towards the target*/
			Pathfinder.PathResult p = pathfinder.nextPathNode(hand.getPosition(),targets.get(i).getPosition(),state);
			return hand.move(p.nextNode);
		}		
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
		if(item != null && item.getType() == Type.Pitchfork){
			// do I have a bale?
			if(item.getFull()){
				
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
					return fh.purchase(Type.Pitchfork);
				}
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

	private void doTaunting(Integer i, GameState state){
		Farmhand hand = state.getMyFarmhands().get(i);
		hand.shout(FamilyFreindlyExplitives.getRandomExplitive());
	}
	
	
	/**
	 * Gets the closest duck
	 * 
	 * @param hand
	 * @param state
	 * @return
	 */
	private Duck getClosestDuck(Farmhand hand, GameState state){
		Duck bestDuck = null;
		double bestC = Double.MAX_VALUE;
		Position pos = hand.getPosition(); //Hey! Where's your hand?
		for(Duck d:state.getMyDucks()){
			Position duckPosition = d.getPosition();
			double c = this.pathfinder.nextPathNode(pos, duckPosition, state).cost;
			if(c < bestC){
				bestDuck = d;
				bestC = c;
			}
		}
		return bestDuck;
	}
	
	private Item getClosestItem(Farmhand hand, GameState state, Type itemType){
		Item bestItem = null;
		double bestC = Double.MAX_VALUE;
		Position pos = hand.getPosition(); //Hey! Where's your hand?
		for(Item d:state.getItems()){
			Position itemPosition = d.getPosition();
			double c = this.pathfinder.nextPathNode(pos, itemPosition, state).cost;
			if(c < bestC && d.getType().equals(itemType)){
				bestItem = d;
				bestC = c;
			}
		}
		return bestItem;
	}

	private enum STATES{
		CHILLIN,
		DUCKING,
		EGGING,
		BALEING,
		TUANTING,
		RETREATIN
	}

	private Map<Integer,STATES> states;
	private Map<Integer,Duck> targets;
	private Map<Integer,Item> itemTargets;
	private Pathfinder pathfinder;
}