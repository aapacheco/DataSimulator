package quizGame;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.*;
import java.util.Scanner;


/*
 * This class contains the root methods for creating/accessing the database containing creature/breed information
 * 
 * In this class the operator admin can 
 * --create a new table
 * --fill the table with data from a csv file
 * --compose a SELECT query based on breed and index
 * --create a connection to the database
 * --Variable 'Index' contains the rowID of the results of the quiz for passage through Quiz.java and other java files
 */
public class DBManagement {
	
	static String DBurl = "jdbc:derby:/Users/anthony/MyDB";
	private static int Index;
	
	//creates a table suitable for housing the information for each breed
	public static String createTable(Connection conn) {
		String sb = "Table creation: ";
		PreparedStatement ct = null;
		try {
			System.out.println("Preparing the Query");
			ct = conn.prepareStatement("create table Dinosaur ("
					+ "ID int not null, "
					+ "BREED varchar(100) not null, "
					+ "PERSONALITIES varchar(100) not null, "
					+ "LIKES varchar(100) not null, "
					+ "DISLIKES varchar(100) not null)");
			System.out.println("Executing the Query");
			ct.execute();
			sb += ("successful");
		} catch (SQLException e) {sb += ("failed"); e.printStackTrace();}
		
		return sb;
	}
	
	//reads the csv file and inserts the information into the database accordingly
	public static void buildTable(String tablename, Connection conn) throws FileNotFoundException {
		PreparedStatement bt = null;
	
		FileReader fr = null;
		String line = null;
		switch(tablename) {
		case "Dog":
			fr = new FileReader("DogDatabase.csv");
			BufferedReader br = new BufferedReader(fr);
			Scanner s = new Scanner(br);
		
			StringBuilder query = new StringBuilder();
			query.append("insert into Dog values ");
			int id = 0;
			String str = "";
			while(s.hasNextLine()) {
				
				/*
				 * read the csv file line by line
				 * for each line, split the values up by the comma delimiter
				 * load those values into a string that is formatted to SQL INSERT query format
				 */
				 line = s.nextLine();
				 String[] tokens = line.split(",");
				 str = String.format("(%d,'%s','%s','%s','%s'),",id, tokens[0],tokens[1],tokens[2],tokens[3]);
				 query.append(str);
				 id++;
			}
			
			
			try {
				query.replace(query.length()-1, query.length(), ""); //trim the final character (comma)
				bt = conn.prepareStatement(query.toString());
				bt.execute();
				
			} catch (SQLException e) {e.printStackTrace();}
			
			break;
			
			
		case "Dinosaur":
			fr = new FileReader("DinosaurDatabase.csv");
			BufferedReader br2 = new BufferedReader(fr);
			Scanner s2 = new Scanner(br2);
		
			StringBuilder query2 = new StringBuilder();
			query2.append("insert into Dinosaur values ");
			int id2 = 0;
			String str2 = "";
			while(s2.hasNextLine()) {
				
				/*
				 * read the csv file line by line
				 * for each line, split the values up by the comma delimiter
				 * load those values into a string that is formatted to SQL INSERT query format
				 */
				 line = s2.nextLine();
				 String[] tokens2 = line.split(",");
				 str2 = String.format("(%d,'%s','%s','%s','%s'),",id2, tokens2[0],tokens2[1],tokens2[2],tokens2[3]);
				 query2.append(str2);
				 id2++;
			}
			
			
			try {
				query2.replace(query2.length()-1, query2.length(), ""); //trim the final character (comma)
				bt = conn.prepareStatement(query2.toString());
				bt.execute();
				
			} catch (SQLException e) {e.printStackTrace();}
			
			break;
		}

	}
	
	//returns a select query used to retrieve information based on the users quiz results
	public static PreparedStatement get(Connection conn, String breed, int index) {
		PreparedStatement selectQuery = null;
		try {
			switch(breed) {
			case "Dog":
				selectQuery = conn.prepareStatement("select BREED, PERSONALITIES, LIKES, DISLIKES from Dog where ID = ?");
				break;
				
				
			case "Dinosaur":
				selectQuery = conn.prepareStatement("select BREED, PERSONALITIES, LIKES, DISLIKES from Dinosaur where ID = ?");
				break;
			}
			selectQuery.setInt(1, index);
			
		} catch (SQLException e) {e.printStackTrace();}
		return selectQuery;
	}
	
	//returns a valid connection object to the database
	public static Connection dbConnect() {
		Connection conn = null;
		try {
			
			//Create Connection to MyDB
			System.out.println("Connecting to MyDB...");
			
			conn = DriverManager.getConnection(DBurl,"admin","admin");
			System.out.println("Connected to MyDB");
			
		} catch (SQLException e) {e.printStackTrace();}
		return conn;
	}
	
	

	//Getter and Setter Methods for Index
	public static int getIndex() {return Index;}
	public static void setIndex(int index) {Index = index;}
	
	//used to create the database and table
//	public static void main(String[] args) {
//		Connection conn = dbConnect();
//		System.out.println("Creating the table...");
//		System.out.println(createTable(conn));
//		try {
//			PreparedStatement cleandog = conn.prepareStatement("delete from Dog where ID is not null");
//			PreparedStatement cleandino = conn.prepareStatement("delete from Dinosaur where ID is not null");
//			cleandog.execute();
//			cleandino.execute();
//		} catch (SQLException e1) {	e1.printStackTrace();	}
//		try {
//			System.out.println("Building Dog Table");
//			buildTable("Dog", conn);
//			System.out.println("Building Dinosaur Table");
//			buildTable("Dinosaur", conn);
//		} catch (FileNotFoundException e) {e.printStackTrace();}
//		
//		//close the connection
//		try {conn.close();} catch (SQLException e) {e.printStackTrace();}
//	}
}
