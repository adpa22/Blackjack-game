// This is the main program for the blackjack game.
public class Blackjack
{
	// This method should:
	//	- Ask the user how many people want to play (up to 3, not including the dealer).
	//	- Create an array of players.
	//	- Create a Blackjack window.
	// 	- Play rounds until the players want to quit the game.
	//	- Close the window.
	public static void main(String[] args)
	{
		System.out.println("How many players do you have (up to 3)?");
		int numPlayers = IO.readInt();
		while (numPlayers<=0 || numPlayers > 3){
			IO.reportBadInput();
			numPlayers = IO.readInt();
		}

		Player players [] = new Player [numPlayers+1]; //indew 0 should be dealer
		players[0] = new Player ("dealer", true);
		
		for (int i = 1; i <= numPlayers; i++){ //for the actual players
			System.out.println("Player " + (i) + " name?");
			String name = IO.readString();
			players[i] = new Player(name, false);
		}
		
		BlackjackWindow window = new BlackjackWindow(players);
		playRound(players, window);
		
		boolean playAgain = true;
		while (playAgain){
			
			System.out.println("Do you want to keep playing? (1 for yes, 0 for no");
			int anothaone = IO.readInt();
			while (anothaone != 1 && anothaone!= 0){
				IO.reportBadInput();
				anothaone = IO.readInt();
			}
			if (anothaone == 0){
				playAgain = false;
				window.close();
			} else {
				playRound (players, window);
			}
		}

	}

	// This method executes an single round of play (for all players).  It should:
	//	- Create and shuffle a deck of cards.
	//	- Start the round (deal cards) for each player, then the dealer.
	//	- Allow each player to play, then the dealer.
	//	- Finish the round (announce results) for each player.
	public static void playRound(Player[] players, BlackjackWindow window)
	{
		Deck deck = new Deck();
		deck.shuffle();
		for (int i = 1; i < players.length; i++){
			players[i].startRound(deck,window);
		}
		players[0].startRound(deck,window);
		for (int k = 1; k < players.length; k++){
			players[k].playRound(deck,window);
		}

		players[0].getHand().getCard(1).turnFaceUp();
		window.redraw();
		players[0].playRound(deck,window);
		window.redraw();
		for (int m = 1; m <players.length; m++){
			players[m].finishRound(players[0].getHand().getScore(), window );
		}
		players[0].getHand().discardAll();
		
	}
}
