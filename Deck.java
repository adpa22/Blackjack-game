import java.util.Random;

// This class represents the deck of cards from which cards are dealt to players.
public class Deck
{
	private Card deck [];	
	private int cardCount = 52;
	// This constructor builds a deck of 52 cards.
	public Deck()
	{
		deck = new Card[52];
		int count = 0;
		for (int suit = 0; suit <= 3; suit++){
			for (int face = 1; face <= 13; face++){
				deck[count] = new Card(suit,face);
				count++;
			}
		}
	}

	// This method shuffles the deck (randomizes the array of cards).
	// Hint: loop over the cards and swap each one with another card in a random position.
	public void shuffle()
	{
		for (int i = deck.length-1; i > 0; i--){
				int index = (int)(Math.random()*(i+1));
				Card temp = deck[i];
				deck[i] = deck[index];
				deck[index] = temp;
	    }
	}
	
	// This method takes the top card off the deck and returns it. //ask dan his draw card method
	public Card drawCard() 
	{
		cardCount--;
		return deck[cardCount];
	}
	
	// This method returns the number of cards left in the deck.
	public int getSize()
	{
		return cardCount; // replace this line with your code ASK DAN FOR THIS
	}
}

