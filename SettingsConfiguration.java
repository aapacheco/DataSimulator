package quizGame;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Stream;

import quizGame.Dog;
import quizGame.Dinosaur;


/*
 * Settings Configuration Class
 * This class contains all of the functions for Behind-The-Scenes Management of the Game
 * 
 * Manipulating the Configuration file to represent the users preferences for the current operating state
 * as well as the next time the game is played. 
 */
public class SettingsConfiguration {
	
	
	
	//Define configuration file fields
	public static String breed;
	public static String newBreedConfiguration;
	public static String[] creatureTypes = new String[2];
	public static Integer questionNumber;
	public static Integer gamecount;
	public static Answer[] loadedAnswers = new Answer[7]; 
	
	/*
	 * change the current creature that is being quizzed-for
	 */
	public static void changeCreature(Scanner input) throws FileNotFoundException, IOException {
		getConfiguration();
		System.out.println("Current Selection: " + breed);
		try {
			input = new Scanner(System.in);
			System.out.println("Current Options: \n");
			for(int i = 0; i < creatureTypes.length; i++) {
				System.out.println("> " + creatureTypes[i]);
			}
			System.out.println("Choose A New Option: ");
			boolean d = false;
			while (!d) {
				switch(input.nextLine()) {
				case "dog": case "Dog":
					System.out.println("Your Choice: Dog \nGreat Choice!");
					newBreedConfiguration = "Dog";
					d = true;
					break;
				case "dinosaur": case "Dinosaur":
					System.out.println("Your Choice: Dinosaur \nGreat Choice!");
					newBreedConfiguration = "Dinosaur";
					d = true;
					break;
				default:
					System.out.println("\n\nWARNING: \nThat input was invalid, please try again.\n\n");
					break;
				}
			}
			breed = newBreedConfiguration;
			System.out.println("Note: Please Save For Any Changes To Take Effect.");
		}
		catch (Exception e) {System.out.println(e);}
	}
	
	/*
	 * saves any changes that have been made to the user preferences
	 */
	public static void saveConfiguration() {
		DataOutputStream outStream = null;
		
		try {
			getConfiguration();
			outStream = new DataOutputStream(new FileOutputStream("SettingsConfiguration.dat"));
			
			System.out.println("\n*---------------------------------------------------*\n");
			System.out.println("New Breed Configuration: " + newBreedConfiguration);
			
			
			outStream.writeUTF(newBreedConfiguration); //write new breed configuration
			int i = 0;
			while(i < creatureTypes.length) {
				outStream.writeUTF(creatureTypes[i]); //write creature types		
			}
			
			
			//loading(); <--implemented later
			System.out.println("Configuration Saved. \nExiting To Main Menu.\n");
			System.out.println("\n*---------------------------------------------------*\n\n");
			outStream.close();
			
			//load newly written configuration into the local program variables
			getConfiguration();
		}
		catch (FileNotFoundException fnf_e) {System.out.println(fnf_e);} 
		catch (IOException e) {e.printStackTrace();}
		
		
	}
	
	/*
	 * reads the current SettingsConfiguration file to get the user saved preferences.
	 */
	public static void getConfiguration() throws FileNotFoundException, IOException{
		try {
			DataInputStream inStream = new DataInputStream(new FileInputStream("SettingsConfiguration.dat"));
			
			//get the current breed configuration
			breed = inStream.readUTF();
			
			//get the available creature types to choose from
			creatureTypes[0] = inStream.readUTF(); 
			creatureTypes[1] = inStream.readUTF();
			
			inStream.close();
		}
		catch (FileNotFoundException fnf_e) {System.out.println(fnf_e);}
		catch (IOException io_e) {System.out.println(io_e);}
	}
		
	/*
	 * allows the gamer to add their favorite breed to the available creatures
	 * if they notice that it is not already available.
	 */
	public static void addCreatureBreed(Scanner input){		
		
		try {
			
			//display current creature types
			System.out.println("Current Creatures: \n");
			for(int i = 0; i < creatureTypes.length; i++) {
				System.out.println("> " + creatureTypes[i]);
			}
			
			//define what creature type the new breed will be
			System.out.println("What Creature Type Is This New Breed Going To Be Added To?");
			String typeSelect = input.nextLine();
			
			//predefined number of fields 0 = breed name, 1 = personality, 2 = likes (positives), 3 = dislikes (negatives)
			Integer CreatureFields = 4;
			String[] tokens = new String[CreatureFields]; //new array to hold information to create new creature breed
			
			System.out.println("\nName Of New Breed: ");
			tokens[0] = input.nextLine();
			
			System.out.println("\n\nList Two 'Characteristics' (Personality Traits) Separated By 'and':  ");
			tokens[1] = input.nextLine();
			
			System.out.println("\n\nList Two 'Likes' Separated By 'and': ");
			tokens[2] = input.nextLine();
			
			System.out.println("\n\nList Two 'Dislikes' Separated By 'and': ");
			tokens[3] = input.nextLine();
			
			//add the new breed to the requested type (above)
			switch(typeSelect) {
			
			case "Dog": case "dog":
				Dog.add(tokens);
				System.out.println("Your Dog Has Been Added Successfully!");
				break;
				
			case "Dinosaur": case "dinosaur":
				Dinosaur.add(tokens);
				System.out.println("Your Dinosaur Has Been Added Successfully!");
				break;
			default:
				System.out.println("\n\nWARNING: \nThat input was invalid, please try again.\n\n");
				break;
			}
			
		}
		catch (IllegalArgumentException iae) {System.out.println(iae);}
		
	}

	/*
	 * writes the current running games progress to the save file for the user to load from when they
	 * play the game next time
	 */
	public static void saveGame(int questionIndex) {

		//create the saved game
		try {
			int fileVersion = 0;
			String save = "SaveFile_";
			String filename = save + Integer.toString(fileVersion);
			File f = new File(filename + ".dat");
			while(f.exists()) {
				filename =save + Integer.toString(fileVersion++);
				f = new File(filename + ".dat");
			}
			
			
			System.out.println("Saving File: " + f.toString());
			DataOutputStream outStream = new DataOutputStream(new FileOutputStream(filename+".dat"));
			outStream.writeUTF(breed); //write current breed configuration
			
			for (int p = 0; p < creatureTypes.length; p++)
				outStream.writeUTF(creatureTypes[p]); //write available breeds
			
			outStream.writeInt(questionIndex); //write current question number
			
			//Stream<Answer> stream = Answers.answers.stream().filter(ans -> (!ans.isEmpty()));
			Stream<Answer> stream = Answers.answers.stream();
			stream.forEach(ans ->  {
				try {
					outStream.writeUTF(ans.get());
				} catch (IOException e) {e.printStackTrace();}
			});
//			for(int k = 1; k < Answers.answers.length; k++) {
//				if(!Answers.answers[k].isEmpty()) {
//					outStream.writeUTF(Answers.answers[k].get()); //write current saved answers
//				}
//			}
			System.out.println("Save Complete.\n\n");
			outStream.close();
		}
		catch (FileNotFoundException fnf_e) {System.out.println(fnf_e);}
		catch (IOException io_e) {System.out.println(io_e);}
		
	}
	
	/*reads the selected saved game taken by parameter and uses the information within to resume progress*/
	public static void loadGame(String fileVersion) throws EOFException, FileNotFoundException, IOException{
		try {
			String saveFile = "SaveFile_" + fileVersion + ".dat";
			DataInputStream fin =  null;
			try {
				fin =new DataInputStream(new FileInputStream(saveFile));
				
				
				//get the current breed configuration
				breed = fin.readUTF();
				//get the available creature types to choose from
				int i = 0;
				while(i < creatureTypes.length) {
					creatureTypes[i] = fin.readUTF();
					i++;
				}
				
				//load last question number
				questionNumber = fin.readInt();
				
				//load previously saved answers
				for(int r = 0; r< loadedAnswers.length; r++) {
					loadedAnswers[r] = new Answer("empty");
				}
				for(int q = 0; q< questionNumber; q++) {
					loadedAnswers[q] = new Answer(fin.readUTF());
				}
				
				//fill Answers.answers with the loaded answers from loadedAnswers[]
				if(Answers.answers.isEmpty()) {
					for(int j = 0; j < 7; j++) {
						Answers.answers.add(j ,loadedAnswers[j]);
					}
				}
				
//				//print to make sure Answers.answers was correctly filled
//				for(int a = 0; a < 7; a++) {
//					System.out.println("Answer " + a + "\tValue: " + Answers.answers.get(a).get() );
//				}
				fin.close();
				} 
			catch (EOFException e) { System.out.println("Loading Complete.");}
			}
		catch (FileNotFoundException fnf_e) {System.out.println(fnf_e);}
		catch (IOException io_e) {System.out.println(io_e);}
	}
	
	/*prints the available saved games that the user can choose from to resume playing*/
	public static void getSavedGames() throws IOException {
		//Retrieve current file directory
		String dir = new java.io.File(".").getCanonicalPath();
		
		//Load the files in the current directory
		File folder = new File(dir);
		
		//store the files in an array
		File[] files = folder.listFiles();
		
		//check against the name to see if it is a saved game. if true, print it out.
		gamecount = 0;
		for(int i = 0; i < files.length; i++){
			if(files[i].getName().contains("SaveFile_")){
				System.out.println(files[i].getName());
				gamecount++;
			}
		}
	}
	
	
	/*
	 * reads through the current file directory and analyzes the available saved games
	 * the function then stores the version numbers of the 3 most recent games. The rest of the saved games are deleted
	 * and then the remaining 3 games are renamed to version 0, 1 and 2.
	 */
	public static void cleanup() throws IOException{
		
		//get current directory
		String dir = new java.io.File(".").getCanonicalPath();
		
		///create new file folder and list all of the files in the current working directory
		File folder = new File(dir);
		File[] files = folder.listFiles();
		
		//create a new array to store all of the saved games 
		//this is to make the computations for finding the 3 most recent games and their
		//respective versions more efficient
		File[] savedGames;
		
		//an index used to store the saved games when found
		int gameCount = 0;
		
		//used to store the 3 most recent saves file1Version = oldest of the 3, file3Version = newest of the 3
		int file0Version = 0;
		int file1Version = 0;
		int file2Version = 0;
		
		//used to store the index of the 3 most recent saved files in savedGames
		int file0Index = 0;
		int file1Index = 0;
		int file2Index = 0;
		
		//new files used to rename the games
		File game0 = new File("SaveFile_0.dat");
		File game1 = new File("SaveFile_1.dat");
		File game2 = new File("SaveFile_2.dat");
		
		//count the amount of games in the working directory to reserve the appropriate amount of memory for savedGames
		for(int i = 0; i < files.length; i++) {
			if(files[i].getName().contains("SaveFile_")){
				gameCount++;
			}
		}
		savedGames = new File[gameCount];
		
		//fill savedGames
		int gameIndex = 0;
		for(int h = 0; h < files.length; h++) {
			if(files[h].getName().contains("SaveFile_")){
				savedGames[gameIndex] = files[h];
				gameIndex++;
			}
		}
		
//		//printing information about the saved games
//		for(int h = 0; h < savedGames.length; h++) {
//			System.out.println("Saved Game: " + savedGames[h].getName());
//		}
		
		
		//computations to find the 3 most recent file versions
		for(int g = 0; g < savedGames.length; g++) {
			//look at saved file version (split string between char 9 (SaveGame_ is 9 characters) and the '.' 
			//in .txt to get the version number
			String file = savedGames[g].getName();
			int fileVersion = Integer.parseInt(file.substring(9, file.indexOf('.')));
			
			//get 3 most recent saves by compating the versions agains the max (file3Version) a.k.a. 'most recent'
			if(fileVersion > file0Version) {
				if(fileVersion > file1Version) {
					if(fileVersion > file2Version) {
						file0Version = file1Version;
						file0Index = file1Index;
						
						file1Version = file2Version;
						file1Index = file2Index;
						
						file2Version = fileVersion;
						file2Index = g;
					} else {
						file0Version = file1Version;
						file0Index = file1Index;
						
						file1Version = fileVersion;
						file1Index = g;
					}
				} else {
					file0Version = fileVersion;
					file0Index = g;
				}
			}
		}
		
		
			//delete all saved games besides the most recent 3
			//cycles through all files in the current directory
			//if the file is a saved game ".contains("SaveFile_")"  check the version 
			//if the version is anything other than the 3 most recent versions, delete them
			//rename the remaining saved games 0-2 accordingly	
		
			int game0index = 0;
			int game1index = 0;
			int game2index = 0;
			boolean game0found = false;
			boolean game1found = false;
			boolean game2found = false;
			
			for(int dIndex = 0; dIndex < files.length; dIndex++) {
				if(files[dIndex].getName().contains("SaveFile_")) {
					
					//delete old games
					if(!files[dIndex].getName().equals(savedGames[file0Index].getName())
							&& !files[dIndex].getName().equals(savedGames[file1Index].getName()) 
							&& !files[dIndex].getName().equals(savedGames[file2Index].getName())) {
						System.out.println("File: " + files[dIndex].getName() + " Has Been Deleted");
						files[dIndex].delete();
					}
				}
			}
			
			for(int rIndex = 0; rIndex < files.length; rIndex++) {
				//System.out.println("\nCurrent File: " + files[rIndex].getName());

				if(!(gameCount < 4)) {
					
					//find game2
					if(files[rIndex].getName().contains(savedGames[file2Index].getName())) {
						System.out.println("\n" + savedGames[file2Index].getName() + " Was Found At Index: " + rIndex
								+ "\n\n");
						game2index = rIndex;
						game2found = true;
					}
				
				
					//find game1
					if(files[rIndex].getName().contains(savedGames[file1Index].getName())) {
						System.out.println("\n" + savedGames[file1Index].getName() + " Was Found At Index: " + rIndex
								+ "\n\n");
						game1index = rIndex;
						game1found = true;
					}
				
				
					//find game0
					if(files[rIndex].getName().contains(savedGames[file0Index].getName())) {
						System.out.println("\n" + savedGames[file0Index].getName() + " Was Found At Index: " + rIndex
								+ "\n\n");
						game0index = rIndex;
						game0found = true;
					}
					
				}
			}
			
			//rename the files
			//rename game0
			if(game0found) {
				System.out.println("\nFile: " + files[game0index].getName());
				files[game0index].renameTo(game0);
				System.out.println("Has Been Renamed To: " + game0.getName());
			}
			
			//rename game1
			if(game1found) {
				System.out.println("\nFile: " + files[game1index].getName());
				files[game1index].renameTo(game1);
				System.out.println("Has Been Renamed To: " + game1.getName());
			}
			
			//rename game2
			if(game2found) {
				System.out.println("\nFile: " + files[game2index].getName());
				files[game2index].renameTo(game2);
				System.out.println("Has Been Renamed To: " + game2.getName());
			}
	}
	
	
	/*
	 * Creating a .dat file using the existing configuration.txt file for data accuracy conversion.
	 * only needs to be run once to create the file.
	 * all further read/writes shall be converted to Binary I/O
	 * 
	 * Sequence:
	 * Read current TXT configuration file
	 * Gather information
	 * use DataOutputStream writeUTF to transport the information from TXT to Binary.
	 */
	public static void TXT_To_DAT(String filename) {
		FileReader fr = null;
		Scanner sc = null;
		//initialize file reader reading by delimiters : and ,
		try {
			fr = new FileReader(filename+".txt");
			BufferedReader br = new BufferedReader(fr);
			sc = new Scanner(br);
			sc.useDelimiter(":|\\,");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
		//initialize output stream to new DAT file
		DataOutputStream out = null;
		try {
			out = new DataOutputStream(new FileOutputStream(filename+".dat"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		//write word by word to the new file.
		while(sc.hasNext()) {
			try {
				out.writeUTF(sc.next());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		//confirmation print by reading the new file
		DataInputStream in = null;
		try {
			in = new DataInputStream(new FileInputStream(filename+".dat"));
			System.out.println("Reading UTF: \n");
			
			//read and print the file until EOF Occurs
			try {
				while(true) {System.out.printf("%s%n", in.readUTF());}
			}
			catch (EOFException e) {System.out.printf("EOF Found");}
			catch (Exception e) {System.out.printf("Exception Caught"); e.printStackTrace();}
			
			
		} 
		catch (FileNotFoundException e) {e.printStackTrace();} 
		
	}

	/*
	 * executes a query on both tables to return all information and print it out. 
	 */
	public static void displayAllCreatureBreeds(Connection conn) {
		PreparedStatement getAll = null;
		try {
			getAll = conn.prepareStatement("select * from Dog,Dinosaur where Dog.ID = Dinosaur.ID");
			
			ResultSet rs = getAll.executeQuery();

				while(rs.next()) {
					System.out.println("\n\n-------------Dog-----------------");
					System.out.println("Breed: " + rs.getString(2));
					System.out.println("Personality: " + rs.getString(3));
					System.out.println("Likes: " + rs.getString(4));
					System.out.println("Dislikes: " + rs.getString(5));
					System.out.println("\n\n-------------Dinosaur-----------------");
					System.out.println("Breed: " + rs.getString(7));
					System.out.println("Personality: " + rs.getString(8));
					System.out.println("Likes: " + rs.getString(9));
					System.out.println("Dislikes: " + rs.getString(10));
				}

		} catch (SQLException e) {e.printStackTrace();}
	}


}

