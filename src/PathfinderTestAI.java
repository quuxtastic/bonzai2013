import java.util.ArrayList;
import java.util.Collection;

import bonzai.api.AI;
import bonzai.api.Duck;
import bonzai.api.Farmhand;
import bonzai.api.FarmhandAction;
import bonzai.api.GameState;

public class PathfinderTestAI implements AI {
	private Pathfinder pathfinder = null;

	@Override
	public Collection<FarmhandAction> turn(GameState state) {

		ArrayList<FarmhandAction> actions = new ArrayList<FarmhandAction>();

		int i = 0;
		Farmhand farmhand = state.getFarmhands().get(0);
		pathfinder = new AStarPathfinder(state, farmhand);
		
		//Duck duck = state.getMyDucks().getNotHeld().getNotCaptured().get(0);
		Duck duck = state.getMyDucks().get(1);
		//System.out.println(duck.getPosition());
		Pathfinder.PathResult res = pathfinder.nextPathNode(
				farmhand.getPosition(), duck.getPosition(), state);
		System.out.println(duck.getPosition());
		if (res != null) {
			actions.add(farmhand.move(res.nextNode));
		} else {
			actions.add(farmhand.shout("Whaaa! c"+duck.getPosition()));
		}

		return actions;
	}

}
