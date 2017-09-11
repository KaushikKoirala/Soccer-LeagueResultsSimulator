import java.util.ArrayList;

/*
 * Points class does a lot of the dirty work for this project, keeps track
 * of the number of points the object in question is representing, and the
 * number of ways in which the points can be attained. */

public class Points implements Comparable {
	// number of points this points object represents
	private int numPts;

	// number and list of ways that allow numPts
	private ArrayList<WDCombo> wdList;

	/**
	 * prints the number of ways to obtain the number of points
	 * represented by this Points object
	 */
	public void printOnceConstructed() {
		if (wdList.size() == (int) 1) {
			if (numPts == 1) {
				System.out.println("There is one way to attain 1 " +
			"point.");
			} else {
				System.out.println("There is one way to attain " + 
			numPts + " points.");
			}
		} else {
			System.out.println("There are " + wdList.size() + " ways to attain " +
		numPts + " points.");
		}
	}

	/**
	 * overrides equals method so that indexOf 
	 * can work properly without knowing all 
	 * the parameters stored in a particular points object
	 * but rather only the number of points that it represents
	 */
	public boolean equals(Object obj) {
		if (obj instanceof Points) {
			Points ptObj = (Points) obj;
			if (ptObj.getNumPoints() == this.getNumPoints())
				return true;
			return false;
		}
		return false;
	}

	/**
	 * adds a given combo of wins draws and losses to the points object
	 * to the arrayList of WDCombo objects
	 * @param wins number of wins to attain the number of points
	 * @param draws number of draws to attain the number of points
	 * @param losses number of losses to attain the number of points 
	 */
	public void addCombo(int wins, int draws, int losses) {
		wdList.add(new WDCombo(wins, draws, losses));
	}

	/**
	 * constructor for the points object
	 * @param numPoints-number of points this points object
	 * represents
	 */
	public Points(int numPoints) {
		numPts = numPoints;
		wdList = new ArrayList<>();
	}

	/**
	 * @return the number of points this points object represents
	 */
	public int getNumPoints() {
		return numPts;
	}
	
	/**
	 * @return the number of ways this number of points can be attained
	 */
	public int getNumWays(){
		return wdList.size();
	}

	/**
	 * used to sort points object for MENU_OPTION_TWO
	 * as points objects are sorted by the number of ways
	 * you can obtain that number of points
	 */
	public int compareTo(Object obj) {
		if (obj instanceof Points) {
			Points otrPt = (Points) obj;
			if (this.wdList.size() - otrPt.wdList.size() != 0)
				return this.wdList.size() - otrPt.wdList.size();
			return this.numPts - otrPt.numPts;
		}
		return 0;
	}

	/**
	 * prints all the ways a given team can obtain this number of points
	 * ex: 51 points can be obtained by 17 wins 0 draws and 21 losses 
	 * or 16 wins 3 draws and 19 losses in a 38 game season
	 */
	public void printWays() {
		System.out.println("The ways are: \n");
		for (int i = 0; i < wdList.size(); i++) {
			wdList.get(i).printCombo();
		}
	}
	
	/**
	 * nested class that represents just one way in which a 
	 * particular number of points can be attained 
	 * Ex: 4 pts can be atttained with 1W 1D 36L in
	 * a 38 game season, this stores that way
	 *Created by Kaushik Koirala
	 */
	private class WDCombo {
		private int numWins;
		private int numDraws;
		private int numLosses;

		public WDCombo(int w, int d, int l) {
			numWins = w;
			numDraws = d;
			numLosses = l;
		}

		/**
		 * helper to print ways in outer class,
		 * prints this particular win draw combo based 
		 * on the number of wins, draws, and losses passes
		 * in by the outer class.
		 */
		public void printCombo() {
			System.out.println(numWins
					+ " WINS " + numDraws
					+ " DRAWS " + numLosses + " LOSSES");
		}

	}
}
