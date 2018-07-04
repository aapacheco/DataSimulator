package quizGame_test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

import quizGame.AnalyzeResults;
import quizGame.Answer;
import quizGame.Answers;
import quizGame.DBManagement;
import quizGame.Dinosaur;
import quizGame.Dog;
import quizGame.QuizPlayer;
import quizGame.SettingsConfiguration;

/*
 * This class contains the Main() executable program. 
 * Within Quiz class are 
 * >>the questions associated with the quiz (a total of 7 questions)
 * >>the askQuestion(int qIndex) method
 * and
 * >>the compose() method
 * 
 */


public class Quiz {
	
	/*
	 * ResultSet from the Query Execution with the Quiz Results
	 */
	private static ResultSet quizResults;
	
	//Getter & Setter Methods for the ResultSet quizResults
	public static ResultSet getQuizResults() {return quizResults;}
	public static void setQuizResults(ResultSet quizResults) {
		Quiz.quizResults = quizResults; 
		try {quizResults.next();} catch (SQLException e) {e.printStackTrace();}
		}
	
	/*Contains the questions brought about in the quiz*/
	public static String[] questions = {
			"Winter or Summer?",
			"Dogs or Cats?",
			"Do you like pizza?",
			"Are an organized individual?",
			"Hip Hop or Country?",
			"Driving or Flying?",
			"Is a HotDog a Sandwich?"};
	
	/*Prints the question at index questionIndex*/
	public static void askQuestion(int questionIndex) {
		if (questionIndex < questions.length)
			System.out.println(questions[questionIndex]);
	}
	
//	public static void loading() {
//		Integer loadingPercentage = 0;
//		String loadingChar = ".";
//		
//		while (loadingPercentage < 50) {
//			System.out.append(loadingChar);
//			loadingPercentage++;
//		}
//		System.out.println();
//	}
	
	/*
	 * Pops each answer out of the array holding the users answers
	 * the answer is then analyzed through a course of if statements
	 * based on the conditions fulfilled, the users breedIndex (score) is manipulated.
	 * 
	 * The Users' final result is calculated by taking the modulo (%) of the 
	 * breedIndex by the length of the array of breeds
	 * of the associated creature. 
	 * 
	 * The user is then prompted to inquire furthermore about their result, based on their answer
	 * the user will either be redirected back to the main menu or presented with a short excerpt of 
	 * information before being redirected.
	 *   
	 */
	public static void compose(Scanner input, Connection conn) throws FileNotFoundException, IOException {
		//String result = "";
		int breedIndex = 0;
		
		int answerIndex = 0;
		while (answerIndex < questions.length) {
			String answer = Answers.answers.get(answerIndex).get().toString();
			//Question 1
			if(answerIndex == 0) {
				if(answer.contains("summer") || answer.contains("Summer")) {
					breedIndex++;
				}
			}
			
			//Question 2
			if(answerIndex == 1) {
				if(answer.contains("dog") || answer.contains("Dog")) {
					breedIndex = breedIndex+2;
				}
			}
			
			//Question 3
			if(answerIndex == 2) {
				if(answer.contains("yes") || answer.contains("Yes") 
						|| answer.contains("yeah") || answer.contains("Yeah")) {
					breedIndex = breedIndex+3;
				}
			}
			
			//Question 4
			if(answerIndex == 3) {
				if(answer.contains("yes") || answer.contains("Yes")  || answer.contains("sort")
						 || answer.contains("kind") || answer.contains("yeah") || answer.contains("Yeah")) {
					breedIndex = breedIndex+4;
				}
			}
			
			//Question 5
			if(answerIndex == 4) {
				if(answer.contains("country") || answer.contains("Country")) {
					breedIndex = breedIndex+5;
				}
			}
			
			//Question 6
			if(answerIndex == 5) {
				if(answer.contains("flying") || answer.contains("Flying")) {
					breedIndex = breedIndex+6;
				}
			}
			
			
			//Question 7
			if(answerIndex == 6) {
				if(answer.contains("no") || answer.contains("No")) {
					breedIndex = breedIndex+7;
				}
			}
			

			answerIndex++;
		}
		
		//compose the information
		switch(SettingsConfiguration.breed) {
		case "Dog":
			
			//set the index of the breed to be retrieved
			DBManagement.setIndex(breedIndex % Dog.getSize());

			//prepare the query and send the results to quizResults
			PreparedStatement selectQuery = DBManagement.get(conn, SettingsConfiguration.breed, DBManagement.getIndex());
			try {	setQuizResults(selectQuery.executeQuery());		} catch (SQLException e1) {e1.printStackTrace();}
			
			//Print the initial result
			System.out.println("\n*---------------------------------------------------*\n");
			System.out.println("Your Result: ");

			//retrieve the result from the query
			try {
				System.out.println(quizResults.getString("BREED"));
			} catch (SQLException e) {e.printStackTrace();}
			
			
			
			//View More Info
			boolean h = false;
			while (!h) {
				System.out.println("\n\nWould You Like To View More Information About Your Result?");
				switch(input.nextLine()) {
				case "yes": case "Yes":
				case "y": case "Y":
					Dog.printMore(quizResults);
					System.out.println("\nSending You Back To The Main Menu...\n\n\n\n");
					//loading();
					h = true;
					break;
				case "no": case "No":
				case "n": case "N":
					System.out.println("\nSending You Back To The Main Menu...\n\n\n\n");
					//loading();
					h = true;
					break;
				default:
					System.out.println("\n\nWARNING: \nThat input was invalid, please try again.\n\n");
					break;
				}
			} 
			break;
			
		case "Dinosaur":
			DBManagement.setIndex(breedIndex % Dinosaur.getSize());
			
			//prepare the query and send the results to quizResults
			PreparedStatement selectQuery2 = DBManagement.get(conn, SettingsConfiguration.breed, DBManagement.getIndex());
			try {	setQuizResults(selectQuery2.executeQuery());		} catch (SQLException e1) {e1.printStackTrace();}
			
			//Print the initial result
			System.out.println("\n*---------------------------------------------------*\n");
			System.out.println("Your Result: ");
			
			//retrieve the result from the query
			try {
				System.out.println(quizResults.getString(2));
			} catch (SQLException e) {e.printStackTrace();}
			
			//View More Info
			boolean s = false;
			while (!s) {
				System.out.println("\n\nWould You Like To View More Information About Your Result?");
				switch(input.nextLine()) {
				case "yes": case "Yes":
				case "y": case "Y":
					Dinosaur.printMore(quizResults);
					System.out.println("\nSending You Back To The Main Menu...\n\n\n\n");
					//loading();
					s = true;
					break;
				case "no": case "No":
				case "n": case "N":
					System.out.println("\nSending You Back To The Main Menu...\n\n\n\n");
					//loading();
					s = true;
					break;
				default:
					System.out.println("\n\nWARNING: \nThat input was invalid, please try again.\n\n");
					break;
				}
			}
			break;
		}
		
	}
	
	
	/*
	 * The Main Executable. 
	 * Presents the user with the main menu and analyzes their responses and 
	 * how they navigate through the different menus of the game. 
	 * 
	 * The users can play the game, load from a previously saved game, configure the settings
	 * or conduct a survey. 
	 */
	public static void main(String args[]) throws FileNotFoundException, IOException, SQLException {
		Connection conn = DBManagement.dbConnect();
		
		//Load Current Configuration
		System.out.println("Loading User Configuration Preferences...");
		//loading();
		SettingsConfiguration.getConfiguration();
		System.out.println("Completed.\n\n\n\n");
		
		
		Scanner in = new Scanner(System.in);
		boolean flag = false;
		while(flag == false) {
			
			int caseValue = 0;
			boolean g = false;
			while (!g) {
				//Main Menu
				System.out.println("\n\n");
				System.out.println("Welcome to CreatureQuizGame! \n\n");
				System.out.print("Main Menu: \n\n"
		                + " > Play \n"
		                + " > Exit \n"
		                + " > Settings \n"
		                + " > Load Previous Save \n"
		                + " > Analyze Samples \n\n"
		                + "What Would You Like To Do? ");
			
		        switch(in.nextLine()) {
		        
		        //evaluate possible user inputs to navigate menu
		        case "Play": case"play":
		        	caseValue = 1;
		        	g = true;
		        	switch(SettingsConfiguration.breed) {
					case "Dog":
						Dog.updateDB(conn);
						//System.out.println("Done.");
						break;
					case "Dinosaur":
						//loading();
						Dinosaur.updateDB(conn);
						//System.out.println("Done.");
						break;
					}
		        	break;
		        case "Exit": case "exit": 
		        case "Quit": case "quit":
		        	caseValue = 2;
		        	g = true;
			        break;
		        case"Settings": case "settings": 
		        case "Config": case "config":
			        caseValue = 3;
			        g = true;
			        break;
		        case"Load": case "load":
		        case"Load Previous": case "load previous":
		        case "Load Previous Save": case "load previous save":
			        caseValue = 4;
			        SettingsConfiguration.cleanup();
			        g = true;
			        break;
		        case "Analyze Samples":
		        case "analyze": case "samples":
		        	caseValue = 5;
		        	g = true;
		        	break;
			    default:
			    	System.out.println("\n\nWARNING: \nThat input was invalid, please try again.\n\n");
			    	break;
		        }
			}

	        	
			try {
				switch(caseValue) {
				
				//Play the game
				case 1:
					
					//build the empty array
					System.out.println("\n...\n");
					if(Answers.answers.isEmpty()) {
						for(int i = 0; i < 7; i++) {
							
							Answers.answers.add(i ,new Answer("empty"));
						}
					} else {
						for(int i = 0; i < 7; i++) {
							Answers.answers.set(i ,new Answer("empty"));
						}
					}
					
					
					// build confirmation
					System.out.println("Build Complete\n\n");
					
					//Begin Quiz
					int questionNumber = 0;
					String inputString = "";
					while (questionNumber < 7) {
						System.out.println("QuestionNumber: " + (questionNumber + 1));
						askQuestion(questionNumber);
						inputString = in.nextLine();
						if(inputString.equals("quit") || inputString.equals("Quit") || inputString.equals("exit")
								|| inputString.equals("Exit") || inputString.equals("stop")|| inputString.equals("Stop")) {
							System.out.println("Would You Like To Save Before Exiting?");
							if(in.nextLine().contains("y") || in.nextLine().contains("Y")) {
								SettingsConfiguration.saveGame(questionNumber);
							} else {
								break;
							}
							break;
						}
						Answer response = new Answer(inputString);

						
						response.store(response);
						questionNumber++;

						}
					if(inputString.equals("quit") || inputString.equals("Quit") || inputString.equals("exit")
							|| inputString.equals("Exit") || inputString.equals("stop")|| inputString.equals("Stop")) {
						System.out.println("\n\n");
						break;
					}
					System.out.println("The Results Are In!!!");
					compose(in, conn);
					break;
					
				//Exit (Quit)
				case 2:
					System.out.print("Thanks For Playing!");
					in.close();
					flag = true;
					System.exit(0);
					
				//Settings Menu
				case 3:
					boolean settingsExit = false;
					while(settingsExit != true) {
						int cvalue = 0;
						System.out.print("\n\nSettings: \n\n"
					            + "> Change Creature Type \n"
					            + "> Add New Breed \n"
					            + "> Display All Breeds\n"
					            + "> Display All Creatures\n"
					            + "> Save & Exit \n"
					            + "> Exit Without Saving \n"
					            + "> Convert TXT To DAT\n\n"
					            + "What Would You Like To Do? ");
						 Dog.updateDB(conn);
						 Dinosaur.updateDB(conn);
						 SettingsConfiguration.getConfiguration();
						 System.out.println("\n\n");
						 
						 boolean f = false;
						 while(!f) {
							 switch(in.nextLine()) {
							 
							 case "change":
							 case "Change":
							 case "creature":
							 case "Creature":
							 case "type":
							 case "Type":
								 cvalue = 1;
								 f=true;
								 break;
								 
							 case "add":
							 case "Add":
							 case "new":
							 case "New":
							 case "breed":
							 case "Breed":
								 cvalue = 2;
								 f=true;
								 break;
								 
							 case "display all breeds":
							 case "Display All Breeds":
								 cvalue = 3;
								 f=true;
								 break;

							 case "display all creatures":
							 case "Display All Creatures":
								 cvalue = 4;
								 f=true;
								 break;
								 
							 case "save":
							 case "Save":
							 case "save&exit":
							 case "Save&Exit":
							 case "save & exit":
							 case "Save & Exit":
								 cvalue = 5;
								 f=true;
								 break;
								 
							 case "back":
							 case "Back":
							 case "cancel":
							 case "Cancel":
							 case "exit":
							 case "Exit":
							 case "quit":
							 case "Quit":
								 cvalue = 6;
								 f=true;
								 break;
								 
							 case "Convert":
							 case "convert":
							 case "Convert TXT To DAT":
							 case "convert txt to dat":
								 cvalue = 7;
								 f = true;
								 break;
								 
							default:
								System.out.println("\n\nWARNING: \nThat input was invalid, please try again.\n\n");
								break;
							 }
						 }
					        //evaluate possible user inputs to navigate menu					        
					        try {
					        	SettingsConfiguration.getConfiguration();
					        	switch(cvalue) {
					        	case 1:
					        		SettingsConfiguration.changeCreature(in);
					        		break;
					        	case 2:
					        		SettingsConfiguration.addCreatureBreed(in);
					        		break;
					        	case 3:
					        		switch(SettingsConfiguration.breed) {
					    			case "Dog":
					    				Dog.updateDB(conn);
					    				Dog.displayAll();
					    				break;
					    			case "Dinosaur":
					    				Dinosaur.updateDB(conn);
					    				Dinosaur.displayAll();
					    				break;
					    			}
					        		break;
					        	case 4:
				    				Dog.updateDB(conn);
				    				Dinosaur.updateDB(conn);
				    				SettingsConfiguration.displayAllCreatureBreeds(conn);
				    				break;
					        	case 5:
					        		SettingsConfiguration.getConfiguration();
					        		SettingsConfiguration.saveConfiguration();
					        		settingsExit = true;
					        		break;
					        	case 6:
					        		settingsExit = true;
					        		break;
					        	case 7:
					        		System.out.print("What File Would You Like To Convert?\n");
					        		System.out.print("Note: Leave Out The File Extension. ie: (.txt)\n\n");
					        		SettingsConfiguration.TXT_To_DAT(in.nextLine());
					        		System.out.print("File Converted.\n");
					        	}
					        }
					        catch (Exception e) {e.printStackTrace();}
					}
					break;
				//Load From A Previous Save
				case 4:
					switch(SettingsConfiguration.breed) {
					case "Dog":
						Dog.updateDB(conn);
						//System.out.println("Done.");
						break;
					case "Dinosaur":
						//loading();
						Dinosaur.updateDB(conn);
						//System.out.println("Done.");
						break;
					}
					System.out.println("\n");
					SettingsConfiguration.getSavedGames();
					if(SettingsConfiguration.gamecount == 0){
						System.out.println("No Current Saved Games Available.");
						System.out.println("Sending You Back To The Main Menu.");
						break;
					} else {
						System.out.println("Which Save File Version Would You Like To Load?\nNote: Enter Only The Version Number.\n");
						String loadVersion = in.nextLine();
						System.out.println("Loading " + "SaveFile_" + loadVersion + "...");
						SettingsConfiguration.loadGame(loadVersion);
						System.out.println("Resuming Game");
						
						
						int resumeQuestionNumber = SettingsConfiguration.questionNumber;
						String resumeInputString = "";
						Answer response = new Answer();
						while (resumeQuestionNumber < 7) {
							
							System.out.println("QuestionNumber: " + (resumeQuestionNumber + 1));
							askQuestion(resumeQuestionNumber);
							resumeInputString = in.nextLine();
							
							if(resumeInputString.equals("quit") || resumeInputString.equals("Quit") || resumeInputString.equals("exit")
									|| resumeInputString.equals("Exit") || resumeInputString.equals("stop")|| resumeInputString.equals("Stop")) {
								
								System.out.println("Would You Like To Save Before Exiting?");
								if(in.nextLine().contains("y") || in.nextLine().contains("Y")) {
									SettingsConfiguration.saveGame(resumeQuestionNumber);
								} else {
									break;
								}
								break;
							}
							response.setValue(resumeInputString);
							response.store(response);
							resumeQuestionNumber++;
	
							}
						if(resumeInputString.equals("quit") || resumeInputString.equals("Quit") || resumeInputString.equals("exit")
								|| resumeInputString.equals("Exit") || resumeInputString.equals("stop")|| resumeInputString.equals("Stop")) {
							System.out.println("\n\n");
							break;
						}
						System.out.println("The Results Are In!!!");
						compose(in, conn);
						break;
					}
				
				//sample size analytics
				case 5:
					
					//retrieving the sample size
					System.out.println("\nWhat Is Your Desired Sample Size?  (between 0 & 50,000,000");
					int sample = Integer.parseInt(in.nextLine());
					while(sample == 0 || sample >= 50000000) {
						System.out.println("Sorry, that input is invalid.\nPlease try again.");
						sample = Integer.parseInt(in.nextLine());
					}
					
					//receiving the redundant thread count
					System.out.println("\nHow Many Times Would You Like To Run This Sample Size? ");
					int threadPoolSize = Integer.parseInt(in.nextLine());
					Thread[] threads = new Thread[threadPoolSize];
					
					
					//create shared QuizPlayer Object
					QuizPlayer qp = new QuizPlayer(conn, sample);
					AnalyzeResults.setNumThreads(threadPoolSize);
					AnalyzeResults.initRunTimes(AnalyzeResults.numThreads);
					
					//fill threads with new QuizPlayers
					for(int i = 0; i < threads.length; i++) {
						threads[i] = new Thread(qp);
					}
					
					
					//execute the threads
					for(Thread t : threads) {
						t.start();
					}
					
					//join for managed concurrency
					for(Thread t : threads) {
						t.join();
					}
					System.out.println("\n\n----------STATISTICS----------\n\n");
					System.out.println("Total Number Of Threads: " + threads.length);
					AnalyzeResults.calcAvgRunTime();
					
					break;
				}
			} 
			catch (Exception e) {e.printStackTrace();}
		}
	conn.close();
	}
}
