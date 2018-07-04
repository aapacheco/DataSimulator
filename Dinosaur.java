package quizGame;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Dinosaur extends Creature{
	
	private static int dbRows;
	
	//an array containing possible breeds
	private static String[] breeds = null;
	
	//an array containing possible personalities
	private static String[] personalities = null;
	
	//an array containing possible likes
	private static String[] positives = null;
	
	//an array containing possible dislikes
	private static String[] negatives = null;		
	
	
	//implementation of abstract methods
	//Get Methods for viewing information defined for each Dog
	public String getBreed(int index) {return breeds[index];}
	public String getPersonality(int index) {return personalities[index];}
	public String getLikes(int index) {return positives[index];}
	public String getDislikes(int index) {return negatives[index];}
	
	//Set Methods for editing the information defined for each Dog
	public void setBreed(int index, String newBreed) {breeds[index] = newBreed;}
	public void setPersonality(int index, String newPersonality) {personalities[index] = newPersonality;}
	public void setLikes(int index, String newLikes) {positives[index] = newLikes;}
	public void setDislikes(int index, String newDislikes) {negatives[index] = newDislikes;}
	
	
	//new methods for implementation
	
	//static 'getter' methods to avoid abstract collision
	public static String returnBreed(int index) {return breeds[index];}
	public static String returnPersonalities(int index) {return personalities[index];}
	public static String returnLikes(int index) {return positives[index];}
	public static String returnDislikes(int index) {return negatives[index];}
	public static Integer getSize() {return dbRows;}
	
	//prints out the current breed associated at the index defined from the parameters	
	public static void printBreed(int index) {System.out.print(returnBreed(index));}
	
	//prints out more information about the current breed associated at the index defined from the parameters	
	public static void printMore(ResultSet rs) {
		Creature.printMore(rs);
		String more = null;
		try {
			
			more = (rs.getString("BREED") + ": \n"
					+ "Being a " + rs.getString("BREED") + " means that you are "
					+ rs.getString("PERSONALITIES") + " \nas well as someone who likes "+ rs.getString("LIKES")
					+ " \nbut does not enjoy " + rs.getString("DISLIKES") + ".\n\n");
		
		} catch (SQLException e) {e.printStackTrace();}
		
		System.out.println(more);
	}
	
	//adds a new breed to the database		
	public static void add(String[] tokens) {
		FileWriter fw = null;
		BufferedWriter bw = null;
		String newRow = "";
		try {
			fw = new FileWriter("DinosaurDatabase.csv", true);
			bw = new BufferedWriter(fw);
			newRow += "\nDinosaur";
			for(int i = 0; i < tokens.length; i++) {
				newRow = newRow + "," + tokens[i];
			}
			//System.out.println("Appending: " + newRow);
			bw.append(newRow); 
			bw.close();
			fw.close(); 
		} 
		catch (IOException e) {e.printStackTrace();}
	}

	//prints out all of the contents of the database CSV file	
	public static void displayAll() {
		for(int i =0; i < dbRows; i++) {
			System.out.println("\n\nBreed: " + returnBreed(i));
			System.out.println("Personality: " + returnPersonalities(i));
			System.out.println("Likes: " + returnLikes(i));
			System.out.println("Dislikes: " + returnDislikes(i));
		}
	}
	
	//executes the Build methods to read the database and rebuild the local arrays
	public static void updateDB(Connection conn){
		PreparedStatement getBreed = null;
		PreparedStatement getPersonalities = null;
		PreparedStatement getLikes = null;
		PreparedStatement getDislikes = null;
		PreparedStatement getRows = null;
		try {
			getBreed = conn.prepareStatement("select BREED from Dog where BREED is not null order by ID");
			getPersonalities = conn.prepareStatement("select PERSONALITIES from Dog where PERSONALITIES is not null order by ID");
			getLikes = conn.prepareStatement("select LIKES from Dog where LIKES is not null order by ID");
			getDislikes = conn.prepareStatement("select DISLIKES from Dog where DISLIKES is not null order by ID");
			getRows = conn.prepareStatement("select count(*) from Dinosaur");
		} catch (SQLException e1) {e1.printStackTrace();}
		ResultSet rows;
		int lines = 0;
		try {
			rows = getRows.executeQuery();
			rows.next();
			lines = rows.getInt(1);
		} catch (SQLException e1) {e1.printStackTrace();}
		
		buildBreeds(lines, getBreed);
		buildPersonalities(lines, getPersonalities);
		buildPositives(lines, getLikes);
		buildNegatives(lines, getDislikes);
		
		dbRows = lines;
	}
	

	//executes a Query on the Database and fills the array Breeds with the information
	public static void buildBreeds(int size, PreparedStatement query){
		ResultSet rs;
		String[] temp = new String[size];
		try {
			rs = query.executeQuery();
			int i = 0;
			while (i < size) {
				rs.next();
				temp[i] = rs.getString(1);
				i++;
			}
		}catch(SQLException e) {e.printStackTrace();}
			breeds = temp;
	}

	//executes a Query on the Database and fills the array Breeds with the information	
	public static void buildPersonalities(int size, PreparedStatement query){
		ResultSet rs;
		String[] temp = new String[size];
		try {
			rs = query.executeQuery();
			int i = 0;
			while (i < size) {
				rs.next();
				temp[i] = rs.getString(1);
				i++;
			}
		}catch(SQLException e) {e.printStackTrace();}
		 personalities = temp;
	}
	
	//executes a Query on the Database and fills the array Breeds with the information
	public static void buildPositives(int size, PreparedStatement query){
		ResultSet rs;
		String[] temp = new String[size];
		try {
			rs = query.executeQuery();
			int i = 0;
			while (i < size) {
				rs.next();
				temp[i] = rs.getString(1);
				i++;
			}
		 }catch(SQLException e) {e.printStackTrace();}
		 positives = temp;
	}
	
	//executes a Query on the Database and fills the array Breeds with the information
	public static void buildNegatives(int size, PreparedStatement query){
		ResultSet rs;
		String[] temp = new String[size];
		try {
			rs = query.executeQuery();
			int i = 0;
			while (i < size) {
				rs.next();
				temp[i] = rs.getString(1);
				i++;
			}
		 }catch(SQLException e) {e.printStackTrace();}
		 negatives = temp;
		 
	}
}
