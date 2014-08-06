import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class Game {

	public int[] board;
	public boolean hasGameEnded = false;
	public static int MAX_POSITION = 100;
	public Player[] players;

	public Game(int numOfPlayers) throws IOException {
		MAX_POSITION = 100;
		createBoard("snakes.txt", "ladders.txt");
		players = new Player[numOfPlayers];
		for (int i = 0; i < numOfPlayers; i++)
			players[i] = new Player("Player" + (i + 1));
	}

	public static void main(String[] args) throws IOException {
		// int numOfPlayers = Integer.parseInt(args[0]);
		int numOfPlayers = 2;
		Game game = null;
		game = new Game(numOfPlayers);
		
		while (!game.hasGameEnded) {
			for (Player p : game.players) {
				int diceValue = Game.rollDice();
				int numOfSix = 0;
				if (diceValue == 6) {
					while (numOfSix < 3 && diceValue == 6) {
						numOfSix++;
						System.out.println(game.updatePosition(p, diceValue));
						diceValue = Game.rollDice();
					}
					if (numOfSix == 3) {
						p.moveTo1();
					} else
						System.out.println(game.updatePosition(p, diceValue));
				} else
					System.out.println(game.updatePosition(p, diceValue));
				if (p.position == game.MAX_POSITION) {
					game.hasGameEnded = true;
					System.out.println("GAME ENDED\nWinner:" + p.name);
					break;
				}
			}
			System.out.println();
		}
	}

	public String updatePosition(Player p, int diceValue) {
		if (!p.hasEntered) {
			if (diceValue == 1 || diceValue == 6) {
				p.hasEntered = true;
				return p.name + " enters the board with a "+ diceValue;
			}
		} else {
			if (p.getPosition() + diceValue < MAX_POSITION)
				if (board[p.getPosition() + diceValue] < p.getPosition() + diceValue){
					p.changePosition(board[p.getPosition() + diceValue]);
					return p.name + " rolls a " + diceValue + " gets bitten by a snake and moves to"
							+ (p.getPosition());
				}
				else if (board[p.getPosition() + diceValue] > p.getPosition() + diceValue){
					p.changePosition(board[p.getPosition() + diceValue]);
					return p.name + " rolls a " + diceValue + " takes the ladder and moves to "
							+ (p.getPosition());
				}
			if ((p.getPosition() + diceValue) <= MAX_POSITION) {
				p.changePosition(board[p.getPosition() + diceValue]);
				return p.name + " rolls " + diceValue + " and moves to "
						+ p.getPosition();
			}
		}
		if (diceValue != 1 && diceValue != 6 && (!players[0].hasEntered)) {
			return p.name + " rolled a "+diceValue+" did not move";
		}
		return "";
	}

	public static int rollDice() {
		Random rand = new Random();
		return rand.nextInt((6 - 1) + 1) + 1;
	}

	public void createBoard(String snakesFile, String laddersFile)
			throws IOException {
		board = new int[MAX_POSITION + 1];
		for(int i = 0; i < MAX_POSITION + 1; i++)
			board[i] = i; 
		loadSnakes(snakesFile);
		loadLadders(laddersFile);
	}

	private void loadSnakes(String filename) throws NumberFormatException,
			IOException {
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		String line = null;
		while ((line = reader.readLine()) != null) {
			int start = Integer.parseInt(line.split(" ")[0]);
			int end = Integer.parseInt(line.split(" ")[1]);
			// snakes.put(start, end);
			board[start] = end;

		}
		reader.close();
	}

	private void loadLadders(String filename) throws NumberFormatException,
			IOException {
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		String line = null;
		while ((line = reader.readLine()) != null) {
			int start = Integer.parseInt(line.split(" ")[0]);
			int end = Integer.parseInt(line.split(" ")[1]);
			// ladders.put(start, end);
			board[start] = end;
		}
		reader.close();
	}

}
