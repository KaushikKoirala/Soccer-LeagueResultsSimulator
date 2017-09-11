import java.io.IOException;
import java.util.*;

/**
 * class that provides the main method to get this project started
 * also takes in an initial number of games from the user input
 * to create permutations out of
 * Class created by Kaushik Koirala
 */
public class ProgramRunner {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		System.out.println("Welcome to the Soccer/Football"
				+ " league results simulator.\nPlease read "
				+ "the readme before you begin");
		System.out.println("Once ready, enter the number of games"
				+ " you'd like to simulate: ");
		Scanner input = new Scanner(System.in);
		int numGames=0;
		while(numGames<=0){
		numGames= input.nextInt();
		if(numGames<=0)
			System.out.println("Not a valid number of games. Try again.");
		}
		Simulation sim1 =new Simulation(numGames, input);
		System.out.println("Thank you for trying the program.");
	}

}
