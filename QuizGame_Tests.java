package JUnit_Tests;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import quizGame.Answer;
import quizGame.Player;

class QuizGame_Tests {

//	@Test
//	public void testAnswerIsEmpty() {
//		//create random array of answers
//		Answer[] testAnswer = new Answer[4];
//		
//		//properly fill one of the objects in the array
//		testAnswer[0] = new Answer("empty");
//		assertTrue(testAnswer[0].isEmpty());
//		
//		//try to confuse the method by including the proper character-string within the string value
//		testAnswer[1] = new Answer("not empty");
//		assertFalse(testAnswer[1].isEmpty());
//		
//		//improperly fill one of the objects in the array
//		testAnswer[2] = new Answer("value");
//		assertFalse(testAnswer[2].isEmpty());
//		
//		//properly fill one of the objects in the array using a String Object rather than a String Literal
//		String empty = "empty";
//		testAnswer[3] = new Answer(empty);
//		assertTrue(testAnswer[3].isEmpty());
//	}
//	
//
//	@Test 
//	public void testPlayerBuilder() {
//		
//		//create new player using the PlayerBuilder
//		Player p = new Player(0);
//		
//		//test the getQuestionNumber() function to confirm the player has been correctly built
//		assertEquals(p.getQuestionNumber(), 0);
//	}
//	
//	@Test
//	public void testCompleteQuiz() {
//		Player p = new Player(0);
//		List<Answer> list = new ArrayList<>(7);
//		list = p.completeQuiz();
//		assertEquals(list.size(), 7);
//
//	}
//	
//	@Test
//	public void testStringFormat() {
//		String s = String.format("(%d,'%s','%s','%s','%s')",1, "This" , "Is" , "A" , "Test");
//		System.out.println(s);
//		assertEquals("(1,'This','Is','A','Test')", s);
//	}
//	
//	@Test
//	public void testStringBuilderReplace() {
//		StringBuilder sb = new StringBuilder();
//		sb.append("hello,");
//		System.out.println(sb);
//		sb.replace(sb.length()-1,sb.length(),";");
//		System.out.println(sb);
//		assertEquals("hello;",sb.toString());
//	}

}
