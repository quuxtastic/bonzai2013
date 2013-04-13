
import java.util.Collection;
import java.util.LinkedList;

import bonzai.api.AI;
import bonzai.api.Duck;
import bonzai.api.Farmhand;
import bonzai.api.FarmhandAction;
import bonzai.api.GameState;

public class MikeAI implements AI {

	private TheBoss theBoss;

	@Override
	public Collection<FarmhandAction> turn(GameState state) {
		//INIT
		if(this.theBoss == null) 
			this.theBoss = new TheBoss(state);
		
		return theBoss.process(state);
	}
}
