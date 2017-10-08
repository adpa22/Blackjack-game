// This class represents one blackjack player (or the dealer)
public class Player
{
	Hand hand;
	String name;
	boolean dealer;
	// This constructor creates a player.
	// If isDealer is true, this Player object represents the dealer.
	public Player(String playerName, boolean isDealer)
	{
		hand = new Hand();
		name = playerName;
		dealer = isDealer;
	}

	// This method retrieves the player's name.
	public String getName()
	{
		if (dealer){
			return "Dealer";
		} else
			return name;
	}

	// This method retrieves the player's hand of cards.
	public Hand getHand()
	{
		return hand; // replace this line with your code
	}
	
	// This method deals two cards to the player (one face down if this is the dealer).
	// The window input should be used to redraw the window whenever a card is dealt.
	public void startRound(Deck deck, BlackjackWindow window)
	{
		if (dealer){
			//can do Card first = deck.deal, then turn that card down
			Card faceup = deck.drawCard();
			faceup.turnFaceUp();
			hand.addCard(faceup);
			window.redraw();
			Card facedown = deck.drawCard();
			facedown.turnFaceDown();
			hand.addCard(facedown);
			window.redraw();
		} else {
			Card first = deck.drawCard();
			first.turnFaceUp();
			hand.addCard(first);
			window.redraw();
			Card second = deck.drawCard();
			second.turnFaceUp();
			hand.addCard(second);
			window.redraw();
		}
	}

	// This method executes gameplay for one player.
	// If this player is the dealer:
	//	- hits until score is at least 17
	// If this is an ordinary player:
	//	- repeatedly asks the user if they want to hit (draw another card)
	//	  until either the player wants to stand (not take any more cards) or
	//	  his/her score exceeds 21 (busts).
	// The window input should be used to redraw the window whenever a card is dealt or turned over.
	public void playRound(Deck deck, BlackjackWindow window)
	{
		if (dealer){
			if (hand.getScore() < 17){
				do {
					Card x = deck.drawCard();
					x.turnFaceUp();
					hand.addCard(x);
					window.redraw();
				} while (hand.getScore() < 17);
			}
		} else {
			boolean choice = GIO.readBoolean(name + ": Do you want to hit?");
			while (choice == true){
				Card k = deck.drawCard();
				k.turnFaceUp();
				hand.addCard(k);
				window.redraw();
				if (hand.getScore() > 21){
					GIO.displayMessage(name + ": You busted!");
					break; //???
				}
				choice = GIO.readBoolean(name + ": Do you want to hit again?");
			}
		}
	}

	// This method informs the player about whether they won, lost, or pushed.
	// It also discards the player's cards to prepare for the next round.
	// The window input should be used to redraw the window after cards are discarded.
	public void finishRound(int dealerScore, BlackjackWindow window)
	{
		if (dealer == false){
			if (hand.getScore() > 21){
				GIO.displayMessage(name + ": You busted!");
			}//might not need this, it's in above method
			else if (dealerScore > 21 && hand.getScore() <= 21){
				GIO.displayMessage(name + ": You won! (Dealer busted, you didn't)");
			} else if (dealerScore <= 21 && hand.getScore() <= 21){
				if (hand.getScore() < dealerScore){
					GIO.displayMessage(name + ": You lost the round");
				} else if (hand.getScore() > dealerScore){
					GIO.displayMessage(name + ": You won the round!");
				} else if (hand.getScore() == dealerScore){
					GIO.displayMessage(name + ": You pushed with the dealer!");
				}
			}

		}
		hand.discardAll();
		window.redraw();
	}
}
