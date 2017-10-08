// This class represents the set of cards held by one player (or the dealer).
import java.util.ArrayList;
public class Hand
{
	ArrayList <Card> hand;
	private int aceCount = 0;
	// This constructor builds a hand (with no cards, initially).
	public Hand()
	{
		hand = new ArrayList<Card>();
	}
	
	// This method retrieves the size of this hand.
	public int getNumberOfCards()
	{
		return hand.size();
	}

	// This method retrieves a particular card in this hand.  The card number is zero-based.
	public Card getCard(int index)
	{
		return hand.get(index);
	}

	// This method takes a card and places it into this hand.
	public void addCard(Card newcard)
	{
		hand.add(newcard);
		if (newcard.getFace() == 1){
			aceCount++;
		}
	}

	// This method computes the score of this hand.
	public int getScore()
	{
		int sum=0;
		int aces=aceCount;
		for(int i=0;i<hand.size();i++){
			sum+=hand.get(i).getValue();
		}		
		while(aces > 0 && sum>21){ 
			sum -=10;
			aces--;
		}
		return sum;
	}

	// This methods discards all cards in this hand.
	public void discardAll()
	{
		hand.clear();
		aceCount = 0;
	}
}
