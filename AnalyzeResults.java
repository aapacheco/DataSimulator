package quizGame;

import quizGame.Dog;

import java.text.MessageFormat;
import java.util.concurrent.locks.ReentrantLock;

import quizGame.Dinosaur;

/*
 * This class defines the analytics behind conducting a survey using a parameterized sample size
 */
public class AnalyzeResults{
	public static int[] DogSampleResults;
	public static int[] DinoSampleResults;
	public static int numSamples; //number of survey samples as defined by the user in main()
	public static int numThreads; //number of threads as defined by the user in main()
	
	/* initializes the DogSampleResults array and DinoSampleResults array dynamically using parameterized sizes */	
	public static synchronized void initArray(int dogSize, int dinoSize) {
		DogSampleResults = new int[dogSize];
		DinoSampleResults = new int[dinoSize];
	}
	
	/*
	 * initializes the array which will store all of the runtimes for each survey execution for each thread
	 */
	public static void initRunTimes(int threadSize) {QuizPlayer.SystemRunTimes = new double[threadSize];}
	
	/*
	 * setter method for setting numThreads through the main()
	 */
	public static void setNumThreads(int threads) {
		numThreads = threads;
	}

	/*
	 * a setter method designed around using the given threadID (mod) the number of threads to get the
	 * array index for SystemRunTimes. 
	 * 
	 * The runtime is then given in the parameters and stored at the appropriate index for computing later
	 */
	public static synchronized void storeThreadTime(double runTime, ReentrantLock lock) {
		synchronized (lock) {
			switch (((int) Thread.currentThread().getId() % AnalyzeResults.numThreads)) {
			case 0:
				QuizPlayer.SystemRunTimes[0] = runTime;
				break;
			case 1:
				QuizPlayer.SystemRunTimes[1] = runTime;
				break;
			case 2:
				QuizPlayer.SystemRunTimes[2] = runTime;
				break;
			case 3:
				QuizPlayer.SystemRunTimes[3] = runTime;
				break;
			case 4:
				QuizPlayer.SystemRunTimes[4] = runTime;
				break;
			case 5:
				QuizPlayer.SystemRunTimes[5] = runTime;
				break;
			case 6:
				QuizPlayer.SystemRunTimes[6] = runTime;
				break;
			case 7:
				QuizPlayer.SystemRunTimes[7] = runTime;
				break;
			case 8:
				QuizPlayer.SystemRunTimes[8] = runTime;
				break;
			case 9:
				QuizPlayer.SystemRunTimes[9] = runTime;
				break;

			}
		}
	}
	
	/*
	 * this function reads through the SystemRunTimes array and sums up the total values contained within each
	 * cell of the array
	 * 
	 * it then divides that by the total number of threads (as defined by the user in main()) to 
	 * calculate the average run time per thread
	 */
	public static double calcAvgRunTime() {
		double avgTime = 0;
		for(int i = 0; i < QuizPlayer.SystemRunTimes.length; i++) {
			avgTime = avgTime + QuizPlayer.SystemRunTimes[i];
		}
		System.out.println("Total Run Time: " + avgTime);
		System.out.println("Avg Run Time Per Thread: " + (avgTime/QuizPlayer.SystemRunTimes.length));
		return (avgTime / QuizPlayer.SystemRunTimes.length);
	}
	
	/*
	 * increases the count for each breed of selected creature throughout the survey to analyze the occurrence of each
	 * type
	 */
	public static synchronized void storeResult(String result, ReentrantLock entryLock) {
		synchronized (entryLock) {
			switch (SettingsConfiguration.breed) {
			case "Dog":
				int i = 0;
				while (i < DogSampleResults.length) {
					if (result.equals(Dog.returnBreed(i))) {
						DogSampleResults[i]++;
					}
					i++;
				}
				break;
			case "Dinosaur":
				int j = 0;
				while (j < DinoSampleResults.length) {
					if (result.equals(Dinosaur.returnBreed(j))) {
						DinoSampleResults[j]++;
					}
					j++;
				}
				break;
			}
		}
	}
	
	/*
	 * prints the statistics of the survey with the count of each type of breed against the survey size and the percentage of total samples
	 */
	public static synchronized void printResults(ReentrantLock entryLock) {
		synchronized (entryLock) {
			switch (SettingsConfiguration.breed) {
			case "Dog":
				System.out.println("\nCreature: Dog\n");
				double dogpercent;
				String dogusagePct;
				int i = 0;
				while (i < Dog.getSize()) {
					if (DogSampleResults[i] == 0) {
						dogpercent = 0;
					} else {
						dogpercent = (((double) DogSampleResults[i] / numSamples) * 100);
					}
					dogusagePct = MessageFormat.format("{0,number,#.##}", dogpercent);
					System.out.println("Type: " + Dog.returnBreed(i));
					System.out.println(
							"Results: " + DogSampleResults[i] + "/" + numSamples + " ( " + dogusagePct + "% )\n");
					i++;
				}
				break;
			case "Dinosaur":
				System.out.println("\nCreature: Dinosaur");
				double dinopercent;
				String dinousagePct;
				int j = 0;
				while (j < Dinosaur.getSize()) {
					if (DinoSampleResults[j] == 0) {
						dinopercent = 0;
					} else {
						dinopercent = (((double) DinoSampleResults[j] / numSamples) * 100);
					}
					dinousagePct = MessageFormat.format("{0,number,#.##}", dinopercent);
					System.out.println("Type: " + Dinosaur.returnBreed(j));
					System.out.println(
							"Results: " + DinoSampleResults[j] + "/" + numSamples + " ( " + dinousagePct + "% )\n");
					j++;
				}
				break;

			}
		}
	}

}
