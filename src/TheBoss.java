import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import bonzai.api.Farmhand;
import bonzai.api.list.FarmhandList;


public class TheBoss {
	
	public TheBoss(FarmhandList farmhandList){
		this.farmhandList  = farmhandList;
		this.states = new HashMap<Integer,STATES>();
		this.pathfinder = new Pathfinder();
		for (Integer i = 0;i<this.farmhandList.size();++i){
			states.put(i, STATES.NOSTATE);
		}
		
	}//end constructor
	
	public void getNext(){
		
	}

	private enum STATES{
		NOSTATE,
		DUCKING,
		EGGING,
		BALEING,
		TUANTING,
		SELLING
		}
	
	private FarmhandList farmhandList;
	private Map<Integer,STATES> states;
	private Pathfinder pathfinder;
}