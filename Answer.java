package quizGame;

/*
 * The Answer class contains the framework for the Answer Data-type which will be used to store
 * the responses by the person playing the game for future analytics.
 * 
 * Each answer contains a value as its only property.
 */
public class Answer {

	private String value; //value contained in that answer

	
	/*
	 * store(Answer answer) looks at the current array of Answer Objects
	 * If the tail is empty (the first item in the array is empty signifying that the array itself is empty
	 * add the answer to the beginning and increase the cursors pointer to the next location.
	 * 
	 * If the tail is not empty, insert the answer at the next available location (where the cursor is pointing)
	 * and increment the cursors pointer to the next location. 
	 */
	public void store(Answer answer) {		
		if(Answers.answers.isEmpty()) {
			Answers.answers.add(Answers.tail,answer);
			Answers.cursor = 1;
		}
		else {
			Answers.answers.add(Answers.cursor,answer);
			Answers.cursor++;
			}
		
		}
	
	
	/*
	 *This function checks the current Answer object to see if it contains a value or not.  
	 */
	public boolean isEmpty() {return (this.value.equals("empty") ? true:false);}

	
	/*returns the value of the Answer Object calling the function*/
	public String get() {return value;}
	
	/*sets the value of the Answer Object calling the function to the newValue*/
	public void setValue(String newValue) {value = newValue;}
	
	//constructors
	/*creates a new Answer Object with the desired value*/
	public Answer(String newValue) {value = newValue;} 
	
	/*creates a new Answer Object that is "empty" */
	public Answer() {value = "empty";}

}
