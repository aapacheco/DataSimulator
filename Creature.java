package quizGame;

import java.sql.ResultSet;

/*
 * An abstract class defining the framework for each creature that can be quizzed for 
 * within the game.
 */
public abstract class Creature {

	//Getter Methods for viewing a Creature
	public String getBreed(int index) {
		// TODO Auto-generated method stub
		return null;
	}
	public abstract String getPersonality(int index);
	public abstract String getLikes(int index); 
	public abstract String getDislikes(int index);

	//Setter Methods for editing a Creature
	public abstract void setBreed(int index, String newBreed);
	public abstract void setPersonality(int index, String newPersonality);
	public abstract void setLikes(int index, String newLikes);
	public abstract void setDislikes(int index, String newDislikes);
	
	//polymorphic method
	public static void printMore(ResultSet rs) {
		System.out.println("---MORE INFO---");
	}
}
