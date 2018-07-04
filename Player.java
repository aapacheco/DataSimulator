package quizGame;

import java.util.ArrayList;
import java.util.List;


import net.datastructures.LinkedQueue;

import quizGame.AnalyzeResults;


/*
 * Player Class contains the framework for the Player Data-type which is used when conducting a survey
 * to run through the quiz and answer each question.
 */
public class Player extends LinkedQueue<Player>{
	
	public static LinkedQueue<Player> Players = new LinkedQueue<>();
	int questionNumber;
//	int priority;
	public static String[][] sampleAnswers = new String[7][2];
	
	/*
	 * creates a new player beginning the quiz at question number 0
	 */
	public Player(int questionNumber) {
		this.questionNumber = questionNumber;
	}
	
	/*returns the current question number for the player*/
	public int getQuestionNumber() {return this.questionNumber;}
	
	
	/*
	 * creates a sorted priority queue containing Players
	 * using a shared player object and inserting it into the queue - flyweight design pattern concept
	 */
	public static synchronized void buildPlayersQueue(int sampleSize) {
		AnalyzeResults.numSamples = sampleSize;
		Player p = new Player(0);
		
		while(Players.size() < sampleSize) {
			//p.setPriority((int) ((Math.random()*9)+1));
			Players.enqueue(p);
		}
	}
	
	/*
	 * outlines the available answer choices for each player in a Matrix Array where the first index is the question number
	 * and the second index is each of the 2 available answers
	 */
	public static void fillAnswers() {
		sampleAnswers[0][0]= "Winter";
		sampleAnswers[0][1]= "Summer";
		sampleAnswers[1][0]= "Dogs";
		sampleAnswers[1][1]= "Cats";
		sampleAnswers[2][0]= "Yes";
		sampleAnswers[2][1]= "No";
		sampleAnswers[3][0]= "Yes";
		sampleAnswers[3][1]= "No";
		sampleAnswers[4][0]="Hip Hop";
		sampleAnswers[4][1]= "Country";
		sampleAnswers[5][0]= "Driving";
		sampleAnswers[5][1]= "Flying";
		sampleAnswers[6][0]= "Yes";
		sampleAnswers[6][1]= "No";
	}

	/*a function for the Player to select an answer at random from the answer matrix defined in fillAnswers()*/
	public synchronized List<Answer> completeQuiz() {
		List<Answer> answers = new ArrayList<>(7);
		answers.add(0, new Answer(sampleAnswers[0][((int) (Math.random()*10)%2)]));
		answers.add(1, new Answer(sampleAnswers[1][((int) (Math.random()*10)%2)]));
		answers.add(2, new Answer(sampleAnswers[2][((int) (Math.random()*10)%2)]));
		answers.add(3, new Answer(sampleAnswers[3][((int) (Math.random()*10)%2)]));
		answers.add(4, new Answer(sampleAnswers[4][((int) (Math.random()*10)%2)]));
		answers.add(5, new Answer(sampleAnswers[5][((int) (Math.random()*10)%2)]));
		answers.add(6, new Answer(sampleAnswers[6][((int) (Math.random()*10)%2)]));
		return answers;
	}

}
