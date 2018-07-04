package quizGame;

import java.util.*;

/*
 * This class defines the array containing the Answer Object as well as its pointers
 * 
 */
public class Answers {

	public static List<Answer> answers = new ArrayList<Answer>(7);
	public static int cursor = 0;
	public static int tail = 0; //first index
	
	/*resets the pointer for the cursor back to the tail (index 0)*/
	public static void resetCursor() {
		cursor = 0;
	}
	
}
