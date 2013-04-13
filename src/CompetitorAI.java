import java.util.ArrayList;
import java.util.Collection;

import bonzai.api.AI;
import bonzai.api.Farmhand;
import bonzai.api.FarmhandAction;
import bonzai.api.GameState;


public class CompetitorAI implements AI {
	
	private AStarPathfinder pathfinder=null;
	private TheBoss boss=null;

	@Override
	public Collection<FarmhandAction> turn(GameState state) {
		return null;
	}

}
