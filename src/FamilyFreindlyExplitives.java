import java.util.Random;


public class FamilyFreindlyExplitives {

	private static final String[] EXPLICIT = {
			"Your mother was a hamster and your father smells of elderberries",
			"Four out of five old maids use Prell.",
			"Got Dirty Dishes? Put Dawn on them.",
			"Build Ford Tough.",
			"If it's on the road, There's a Fram that fits",
			"I Should Have Used Preparation H"
	};
	
	public static String getRandomExplitive(){
		return EXPLICIT[new Random().nextInt() % EXPLICIT.length];		
	}
}
