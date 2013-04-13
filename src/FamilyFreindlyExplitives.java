import java.util.Random;


public class FamilyFreindlyExplitives {

	private static final String[] EXPLICIT = {
			"Your mother was a hamster and your father smells of elderberries"
	};
	
	public static String getRandomExplitive(){
		return EXPLICIT[new Random().nextInt() % EXPLICIT.length];		
	}
}
