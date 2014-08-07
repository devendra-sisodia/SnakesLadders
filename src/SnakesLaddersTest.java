import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 
 * @author Devendra-Akhila-Krupa-Abirami
 *
 */
public class SnakesLaddersTest {

	int[] boardPos;
	Game game;
	Player[] players;
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		int numOfPlayers=2;
		players = new Player[numOfPlayers];
		for (int i = 0; i < numOfPlayers; i++)
			players[i] = new Player("Player" + (i + 1));
		game = new Game(1);
		Game.MAX_POSITION = 16;
		boardPos = new int[Game.MAX_POSITION + 1];

		BufferedReader reader = new BufferedReader(new FileReader("testCreateboard.txt"));
		String line = null;
		line = reader.readLine();
		String[] positions = line.split(" ");
		for (int i = 0; i < positions.length; i++) {
			boardPos[i] = Integer.parseInt(positions[i]);
		}

		reader.close();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link game.snl.Game#createBoard(java.lang.String, java.lang.String)}.
	 * @throws IOException 
	 */

	@Test
	public void testCreateBoard() throws IOException {

		game.createBoard("snakesTest.txt", "laddersTest.txt");
		for(int i = 0; i <= (Game.MAX_POSITION); i++) {
			assertEquals("Board not created correctly at position "+i, boardPos[i], game.board[i]);
		}
	}


	@Test
	public void testUpdatePosition() throws IOException {
		String move=new String();
		int diceValue;
		BufferedReader reader = new BufferedReader(new FileReader("diceRollTest.txt"));
		BufferedReader outputrd = new BufferedReader(new FileReader("requiredOutput.txt"));
		
		while((move = reader.readLine())!=null)
		{
			String output= new String();
			diceValue = Integer.valueOf(move);
			
			output = game.updatePosition(players[0],diceValue);
						
			String outputline=outputrd.readLine();
				assertEquals("Update Position not working correctly for "+"\nREQ: "+outputline+"\nACT: "+output, outputline.trim(), output.trim());
		}

	}

}
