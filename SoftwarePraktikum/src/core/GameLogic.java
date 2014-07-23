package core;

public interface GameLogic {

	/**
	 * 
	 * @param move
	 * @param graph
	 * @return Gibt "GameOver" aus wenn das Spiel vorbei ist, "Legal" wenn es
	 *         ein gueltiger Zug war und "Ilegal" wen es ein ungueltiger Zug ist
	 */

	/*
	 * Die Methode kontorliert ob der n�chste Zug lega, ilegal ist oder ob das
	 * Spiel dadurch beendet wird
	 */
	public String logic(Move move, City_Graph graph, PlayerAbs currentPlayer);

	/*
	 * Ich war mir nicht sicher was wir alles noch so brauchen f�r das interface
	 * brauchen (Also falls einem was besseres einf�llt :D
	 */
}
