import java.io.IOException;
import java.text.NumberFormat;
import java.util.*;

/*Class that runs the main simulation. For x amount of points,
 * the class will make it possible to list the way to attain
 * a cetain number of points for a team via wins, draws and losses, as
 * well as other basic statistics. It will also make it possible to list
 * a randomization of a league season's results based on the permutations
 * of the points results.
 * Created by Kaushik Koirala*/
public class Simulation {
	
	//ArrayList of all Points object for a given simulation
	//of x games
	private ArrayList<Points> ptList;
	
	//number of games that the user chooses
	private int gamesGiven;
	
	//allows user interaction/input
	private Scanner input;
	
	//counts all possible permutations in a season with
	//gamesGiven games
	private int counter;
	
	//keeps an array of the points possible to make it easy
	//to calculate basic statistical operations
	private int[] pointsArr;
	
	//constant's declared for menu options so that
	//they don't confuse with other variables in use
	private static final int MENU_OPTION_ONE=1;
	private static final int MENU_OPTION_TWO=2;
	private static final int MENU_OPTION_THREE=3;
	private static final int MENU_OPTION_FOUR=4;
	private static final int MENU_OPTION_FIVE=5;
	private static final int MENU_OPTION_SIX=6;

	/*Constructor for the Simulation class 
	 *that takes in a number of games the user would like
	 *to simulate possible results for.
	 *parameters: numGames, that defines the number of games
	 *the user initially wants possible results for
	 *input, the keyboard linking user input from the program
	 *runner file */
	public Simulation(int numGames, Scanner input){
		gamesGiven=numGames;
		this.input=input;
		counter=0;
		runAlgorithm(numGames);
		//all possible results with gamesGiven games in a single season
		//now created
		pointsArr=new int[counter];
		fillArray();
		doRestOfProgram();
		
	}
	
	/*Core algorithm that allows the program to store
	 * all possible results (in points) given a 
	 * numGames-game season. 
	 * pre: numGames the number of games the user has set
	 * post: ptList updated with all possible points from the 
	 * number of games as well as the ways in which to attain them
	 * */
	private void runAlgorithm(int numGames){
		ptList=new ArrayList<>();
		// core algorithm starts here, will probably
		// be moved to other class
		// nested loop starting with 0 wins, basically testing all
		// permutations of results in the number of matches
		for (int numWins = 0; numWins <= numGames; numWins++) {
			int gamesLeft = numGames - numWins;
			for (int numDraws = 0; numDraws <= gamesLeft; numDraws++) {
				int numPts = (numWins * 3) + numDraws;
				// win is worth 3 points, draw is worth 1 point
				int numLosses = numGames - numWins - numDraws;
				// construct objects based on the points attained
				// according to this permutation in the loop
				Points pt = new Points(numPts);
				if (ptList.indexOf(pt) >= 0) {
					// if number of points already exists, we simply add
					// win-draw-loss-combo to the point object
					pt = ptList.get(ptList.indexOf(pt));
				} else {
					// add new constructed object to the arraylist
					ptList.add(pt);
				}
				//adds permutation to the point object
				pt.addCombo(numWins, numDraws, numLosses);
				counter++;
			}
		}
	}
	
	/*Prints out the menu options and facilitates the 
	 * program based on the user's response. Called upon
	 * by the constructor or redirected to from a deeper level
	 * within the program.
	 * */
	private void doRestOfProgram(){
		doInitialMenuPrints();
		int choice=input.nextInt();
		runMenuOptions(choice);	
	}
	
	/**
	 * essential to the program. Redirects the program to 
	 * the proper method based on the user input.
	 * @param choice: the choice the user has selected
	 */
	private void runMenuOptions(int choice){
		//switch case based on user's choice that results
		//in the execution of other methods
		switch (choice){
		case MENU_OPTION_ONE:
			printWaysToObtainPoints();
			break;
		case MENU_OPTION_TWO:
			printRankings();
			break;
		case MENU_OPTION_THREE:
			printPermutationStatistics();
			break;
		case MENU_OPTION_FOUR: 
			doSimulations();
			break;
		case MENU_OPTION_FIVE:
			{
				System.out.println("Enter a new number of games: ");
				int newNumGames=input.nextInt();
				new Simulation(newNumGames, input);
			}
		case MENU_OPTION_SIX:
			break;//exit
		default:
			{
				System.out.println("You entered an invalid option.");
				doRestOfProgram();
			}
		}
	}
	
	/**
	 * Method for MENU_OPTION_FOUR. Takes a 
	 * given number of teams from the user and 
	 * simulates a league table with the number of teams
	 * based on the likelihood of the points as predetermined
	 * by the class listing all ptential permutations, given
	 * numGiven games, Also makes it possible to see stats for
	 * the new table 
	 */
	private void doSimulations() {
		//sort to put into bins
		Arrays.sort(pointsArr);
		//method takes in number of teams given by user
		int numTeams= getNumberOfTeamsInput();
		//new points array is made by the method by separating
		//all combinations, based on points, into numTeams bins
		//and randomizing a points result from each bin 
		int [] newPointsArr= getSimulatedTableArray(numTeams);
		//prints out the array
		printTable(newPointsArr);
		System.out.println("Do you want to see stats for the "
				+ "randomized data? Enter Y or N.");
		String inputStr=input.next();
		char resp = inputStr.charAt(0);
		if(resp=='y'||resp=='Y')
		printSimulatedStats(newPointsArr);
		System.out.println("\nEnter 1 if you want to do simulations with a "
				+ "different number of teams.");
		menuRedirection(MENU_OPTION_FOUR);
	}

	/**
	 * gets the stats of the simulation that is based on the 
	 * number of teams that the user of the program inputs
	 * pre/@param newPointsArr: array of the points totals 
	 * that is of numTeams length from the doSimulations()
	 * method
	 * post: prints the mean, median, and standard deviation
	 * for the league table for comparison with the larger 
	 * data set 
	 */
	private void printSimulatedStats(int[] newPointsArr) {
		System.out.println("\nThe mean of the randomized data is "+ 
				getMean(newPointsArr)+ " points.");
		System.out.println("The median of the randomized data is "+
				getMedian(newPointsArr)+ " points.");
		System.out.println("The standard deviation of the randomized "
				+ "data is "+ getStDev(newPointsArr)+ " points.");
		
	}

	/**
	 * actually prints a table with the league points result 
	 * of a user given number of teams. Helper method for the 
	 * doSimulations() method. 
	 * @param newPointsArr array that holds the points value of
	 * the teams in the new table
	 * post: League table is printed with randomized/simulated 
	 * results based on the likelihood of the points totals 
	 * as determined by initial permutations
	 */
	private void printTable(int[] newPointsArr) {
		System.out.println();
		for(int i=0;i<newPointsArr.length;i++){
			System.out.println("TEAM POSITION #"+ (i+1)
					+" Points: "+
					newPointsArr[newPointsArr.length-(i+1)]);
			//indexing weird because highest points are ranked first
		}
		
	}

	/**
	 * returns an array of numTeams length by "binning" 
	 * the permutations of points based on their frequency 
	 * of occurring in the permutation. Array is in sorted order.
	 * Helper method for MENU_OPTION_FOUR
	 * @param numTeams user input number of teams to build 
	 * simulated table off of
	 * @return 1d static array that has ascending ints
	 * representing points attained by numTeams different 
	 * teams. Ints were used by binning entire set of possible 
	 * points in accordance with their frequency
	 */
	private int[] getSimulatedTableArray(int numTeams) {
		int lower=0;
		int increment=pointsArr.length/numTeams;
		int[] randomizedPoints = new int[numTeams];
		for(int i=0;i<numTeams;i++){
			//indexing larger array in bins and randomizing
			//the index value within the bin
			int index = (int) (lower + (Math.random()*increment));
			randomizedPoints[i]=pointsArr[index];
			lower+=increment;//bin changed
		}
		return randomizedPoints;
	}

	/**
	 * helper method for MENU_OPTION_FOUR
	 * gets and returns the number of teams
	 * the user would like to create a league table out of
	 * @return an int representing the number of teams a 
	 * user would like to create a simulated league table out of
	 */
	private int getNumberOfTeamsInput() {
		System.out.println("Enter a number of teams to create a"
				+ " league table: ");
		int numTeams = 0;
		while(numTeams<=0||numTeams>=100){
			numTeams=input.nextInt();
			if(numTeams<=0||numTeams>=100){
				System.out.println("Please enter a more reasonable "
						+ "number of games.");
			}	
		}
		return numTeams;
	}

	
	/**
	 * Method for MENU_OPTION_THREE
	 * Gives stats based on all possible points and  
	 * all possible ways to attain them. Uses helper methods
	 */
	private void printPermutationStatistics() {
		System.out.println("There are "+counter+
				" ways (permutations) any one given team"
				+ " can finish the league.");
		System.out.println("The average (mean) number of points a team"
				+ " can attain is "+ getMean(pointsArr)+ " points.");
		System.out.println("The median number of points a team "
				+ "can attain is "+getMedian(pointsArr)+" points.");
		System.out.println("The standard deviation for the collection"
				+ " of all potential results is "+ getStDev(pointsArr)+ 
				" points.");
		menuRedirection(MENU_OPTION_THREE);
	}

	/**
	 * returns the standard deviation of a data set
	 * useful for both the entire set of permutations
	 * of results with gamesGiven games (MENU_OPTION_THREE)
	 * as well as for the simulated table given a user-
	 * input the of teams (MENU_OPTION_FOUR)
	 * @param stDevToCompute array with data for the 
	 * standard deviation we need to compute
	 * @return a double that represents a standard deviation
	 * of the values in the array
	 */
	private double getStDev(int[] stDevToCompute) {
		double mean = getMean(stDevToCompute);
		double sum=0.0;
		for(int i=0;i<stDevToCompute.length;i++){
			double val_i = stDevToCompute[i];
			double result = val_i-mean;
			//sum of squares for each value in array
			sum+=Math.pow(result, 2);
		}
		//divide by n-1
		sum/=(stDevToCompute.length-1);
		//take the square-root and return formatted double
		return Double.parseDouble(String.format
				("%.3f", Math.sqrt(sum)));
	}

	/**
	 * gets the median (middle value) of a sorted data set
	 * Used for both the entire set of permutations of results
	 * based on gamesGiven number of games (MENU_OPTION_THREE)
	 * as well as the data set with the simulated league table 
	 * based on an user-input  number of teams (MENU_OPTION_FOUR)
	 * @param medToCompute array that contains the values which needs
	 * the median to be computed
	 * @return the median, correctly formatted
	 */
	private double getMedian(int[] medToCompute) {
		Arrays.sort(medToCompute);
		if(medToCompute.length%2==1){
			//middle value possible with odd number of values
			return medToCompute[medToCompute.length/2];
		}
		double lower=medToCompute[(medToCompute.length/2)-1];
		double upper=medToCompute[(medToCompute.length/2)];
		//not possible when even, return average of two inner most
		//formatted correctly
		return Double.parseDouble(String.format("%.3f",(lower+upper)/2));
	}

	/**
	 * gets the mean (statistical average) of a data set
	 * Used to get mean for the entire set of permutations
	 * (Menu_OPTION_THREE) as well as for the league table
	 * based on an user-input number of teams (MENU_OPTION_FOUR)
	 * @param meanToCompute: array that has the values for 
	 * which the mean should be computed
	 * @return the mean of the values in the array
	 */
	private double getMean(int[] meanToCompute) {
		int sum=0;
		for(int i=0;i<meanToCompute.length;i++){
			sum+=meanToCompute[i];
			//summed up values
		}
		//divided by number of values
		double result=((double)sum/meanToCompute.length);
		//formatted and returned results
		return Double.parseDouble(String.format("%.3f", result));
	}

	/**
	 * fills a static array with possible points given a
	 * gamesGiven number of games as determined in the ptList
	 * arrayList through the runAlgorithm method
	 */
	private void fillArray() {
		int index=0;
		for(int i=0;i<ptList.size();i++){
			int numToFill=ptList.get(i).getNumWays();
			for(int k=0;k<numToFill;k++){
				//fills the array of number of times based on the
				//number of ways the points amount can be attained.
				pointsArr[index]=ptList.get(i).getNumPoints();
				index++;
			}
		}	
	}

	/**
	 * Method for MENU_OPTION_TWO that allows you to see 
	 * a ranking of the points based on the number of ways 
	 * it can be attained. Example: 0 and 114 pts can each 
	 * only be attained one way (0W 0D 38L) or (38W 0D 0L)
	 * and 51 points can be attained 11 different ways
	 *Works because Points has been implemented as part of 
	 *the Comparable interface, and sorted based on which 
	 *points object has the most permutations to obtain that 
	 *number of points
	 */
	private void printRankings() {
		Collections.sort(ptList);
		System.out.println("The following is a ranking of points possible "
				+ "in a league season based on the ways to get them.\n\n");
		for(int i=0;i<ptList.size();i++){
			ptList.get(i).printOnceConstructed();
		}
		menuRedirection(MENU_OPTION_TWO);
	}
	
	/**
	 * Method for MENU_OPTION_ONE
	 * User will input a number of points for which
	 * the program will print out all possible ways to attain
	 * the number of points.
	 * Ex: 4 points can be obtained 2 ways, either with 1 win
	 * and 1 draw or 4 draws
	 */
	private void printWaysToObtainPoints() {
		System.out.println("Enter a number of points that you want to obtain"
				+ " the possible results for: ");
		int points=input.nextInt();
		if(points<0||points>(gamesGiven*3)){
			//you can't obtain negative points or a more-than-perfect
			//amount of points
			System.out.println("Please enter a valid number of points.");
		}
		else{
			Points ptsObj=new Points(points);
			//indexOf only works because of overriden equals method
			int index = ptList.indexOf(ptsObj);
			if(index<0)
				//points like 113 are impossible in a 38 game season,
				//so there is no points object for 113 in ptList
				System.out.println("There are 0 ways to obtain "+points
						+ " points." );
			else{
			//ptsObject exists in ptList, will print ways to attain
			//# of points
			ptsObj = ptList.get(index);
			ptsObj.printOnceConstructed();
			ptsObj.printWays();
			}
		}
		System.out.println("\nEnter 1 if you want to enter a new number "
				+ "of points");
		menuRedirection(1);
		}
	
	
	/**
	 * redirects the user to the main menu so that they can continue
	 * using the program once they have selected a particular option.
	 * Used as a helper method by the four main options.
	 * @param num will ideally be a MENU_OPTION as the output of the 
	 * redirection will depend on what menu option they have just 
	 * exhausted.
	 */
	private void menuRedirection(int num){
		System.out.println("\nEnter 'm' to go back to the main menu.");
		System.out.println("Enter 'e' or any other key to exit");
		String newInputStr=input.next();
		char newInput= newInputStr.charAt(0);
		if(newInput=='1'){
			//MENU OPTION ONE AND FOUR allow you to 
			//redo the same method with a different user input
			//so rather than go back to the main menu, the users can do that
			if(num==MENU_OPTION_ONE)
				printWaysToObtainPoints();
			if(num==MENU_OPTION_FOUR)
				doSimulations();
		}
		else if(newInput=='m'||newInput=='M')
			doRestOfProgram();
		else;

	}

	/**
	 * prints the menu options so the user can know what they are
	 */
	private void doInitialMenuPrints() {
		System.out.println("Permutations have been simulated for "+ gamesGiven
				+" games.");
		System.out.println();
		System.out.println("Enter 1 to print all ways to obtain a particular "
				+ "number of points. ");
		System.out.println("Enter 2 to list a ranking of points based on the"
				+ " number of ways that it can be attained.");
		System.out.println("Enter 3 to get statistics on the permutations.");
		System.out.println("Enter 4 to simulate a league table based on"
				+ " an input number of teams.");
		System.out.println("Enter 5 to simulate a new number of games.");
		System.out.println("Enter 6 to exit.");
	}


}

