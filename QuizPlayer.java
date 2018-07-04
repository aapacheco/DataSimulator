package quizGame;

import java.sql.Connection;
import java.util.concurrent.locks.ReentrantLock;

import quizGame_test.Quiz;


/*
 * Quiz Player Class
 * 
 * This class implements the Runnable interface to act as a Player which can be called through threading
 * 
 * QuizPlayer Class holds a Connection to the database, sample, ReentrantLock and 
 * array containing the SystemRunTimes
 * 
 */
public class QuizPlayer implements Runnable{
	Connection conn = null;
	int sample = 0;
	private ReentrantLock entryLock = new ReentrantLock();
	public static double[] SystemRunTimes;
	
	public QuizPlayer(Connection conn, int sample) {
		this.conn = conn;
		this.sample = sample;
	}
	
	
	/*
	 * Allows the QuizPlayer to be called by threads.
	 * 
	 * Builds its respective Answers array, and Players Queue
	 * 
	 */
	@Override
	public synchronized void run() {
		entryLock.lock();
		try {
			//execute the quiz on samples
			if(Answers.answers.isEmpty()) {
				for(int i = 0; i < Quiz.questions.length; i++) {
//					System.out.println("i = "+i);
					Answers.answers.add(i ,new Answer("empty"));
				}
			} else {
				for(int j = 0; j < Quiz.questions.length; j++) {
//					System.out.println("j = "+j);
					Answers.answers.set(j ,new Answer("empty"));
				}
			}
			//double runtime = 0;
			
			Answers.resetCursor();
			Player.fillAnswers();
			Dog.updateDB(conn);
			Dinosaur.updateDB(conn);
			AnalyzeResults.initArray(Dog.getSize(), Dinosaur.getSize());
			
			synchronized(entryLock){
				Player.buildPlayersQueue(sample);
				//runtime = calculateResults(entryLock);
				calculateResults(entryLock);
				//System.out.println("Storing "+ runtime + " @ Index: " + (int) (Thread.currentThread().getId()%AnalyzeResults.numThreads));
			}
			//System.out.println("Run Duration: " + runtime);
		}
		finally {
			entryLock.unlock();
		}

	}
	
	/*
	 * Called upon by the run() method
	 * 
	 * This method performs the heart of the execution.
	 * Each player from the players queue is dequeued 1-by-1 and as they are popped out, they shall randomly 
	 * complete the quiz.
	 * 
	 * The answers are then analyzed to compute the players score in which their result is returned and stored.
	 * 
	 * The run time for this computation is recored and stored in SystemRunTimes for later computations.
	 */
	public synchronized void calculateResults(ReentrantLock lock) {
			synchronized (lock) {
				double runtime = 0;
				Player p = null;
				double startTime = System.currentTimeMillis();
				
				
				//create 10 synchronized threads and execute the quiz
				while (!Player.Players.isEmpty()) {

					p = Player.Players.dequeue();
					
					//overwrite the current contents of answers			
					Answers.answers = p.completeQuiz();

					//compute the result by popping out each answer and analyzing it
					int sampleBreedIndex = 0;

					//Question 1
					if (Answers.answers.get(0).get().equals("Summer")) {
						sampleBreedIndex++;
					}

					//Question 2
					if (Answers.answers.get(1).get().equals("Dogs")) {
						sampleBreedIndex = sampleBreedIndex + 2;
					}

					//Question 3
					if (Answers.answers.get(2).get().equals("Yes")) {
						sampleBreedIndex = sampleBreedIndex + 3;
					}

					//Question 4
					if (Answers.answers.get(3).get().equals("Yes")) {
						sampleBreedIndex = sampleBreedIndex + 4;
					}

					//Question 5
					if (Answers.answers.get(4).get().equals("Country")) {
						sampleBreedIndex = sampleBreedIndex + 5;
					}

					//Question 6
					if (Answers.answers.get(5).get().equals("Flying")) {
						sampleBreedIndex = sampleBreedIndex + 6;
					}

					//Question 7
					if (Answers.answers.get(6).get().equals("No")) {
						sampleBreedIndex = sampleBreedIndex + 7;
					}

					//compose the information and store it to be analyzed when finished.
					switch (SettingsConfiguration.breed) {
					case "Dog":
						int dogSampleResultDex = (sampleBreedIndex % Dog.getSize());
						String dogSampleResult = Dog.returnBreed(dogSampleResultDex);
						AnalyzeResults.storeResult(dogSampleResult, entryLock);
						break;

					case "Dinosaur":
						int dinoSampleResultDex = (sampleBreedIndex % Dinosaur.getSize());
						String dinoSampleResult = Dinosaur.returnBreed(dinoSampleResultDex);
						AnalyzeResults.storeResult(dinoSampleResult, entryLock);
						break;
					}

				}
				double endTime = System.currentTimeMillis();
				
				//print the sample results
				//System.out.println("---RESULTS---\n");
				runtime = (double) (endTime - startTime);
				AnalyzeResults.storeThreadTime(runtime, entryLock);
				//AnalyzeResults.printResults(entryLock);
				
			}
	}
}
