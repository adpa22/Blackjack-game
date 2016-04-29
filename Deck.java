// This class represents the deck of cards from which cards are dealt to players.
import java.util.Random;
public class Deck
{

	private Card deck[];
	private int cardsLeft = 312, cardsUsed = 0;
	private int cardCount = 312;
	private int probability = 0;
	private int valuecount = 0;

	public Deck(){
		deck = new Card[312];
		
		int count = 0; //number of cards created so far
		for (int c = 0; c < 6; c++){ //makes 6 decks
			for (int i = 0; i <=3; i++){ //for the suit
				for (int k = 1; k <= 13; k++){ //for the face
					deck[count] = new Card (i,k);
					count++;
				}
			}
		}
	}

	/*public SixDecks(){ //use above for loops, but add a for loop that runs 6 times

	}*/

	
	// This method takes the top card off the deck and returns it.
	public Card deal()
	{
		cardsLeft--;//this variable is for card counting
		cardsUsed++;
		cardCount--; //might have to make cardCount after return somehow (try to fixit)
		if (cardCount == 0){ 
			System.out.println("Ran out of cards, resetting deck");
			cardCount = 312;
			probability = 0;
		}
		//counts cards as they're dealt
		if (deck[cardCount].getValue() >= 2 && deck[cardCount].getValue() <= 6){
			probability +=1;
		}
		if (deck[cardCount].getValue() >= 7 && deck[cardCount].getValue() <= 9){
			probability += 0;
		}
		if (deck[cardCount].getValue() >= 10 && deck[cardCount].getValue() <= 11){
			probability -= 1;
		}
		return deck[cardCount];
		
	}

	public int getBetProbability(){ //running count
		return probability;
	}

	public void printHint(){
		if (probability > 0){
			System.out.println("The odds are in your favor");
		} else if (probability <= 0){
			System.out.println("You should probably bet low");
		}
	}
	
	
	// this method returns true if there are no more cards to deal, false otherwise
	public boolean isEmpty()
	{
		if (cardCount == 0){
			return true;
		}
		else
			return false;
	}
	
	//this method puts the deck int some random order
	public void shuffle(){
	    for (int i = deck.length-1; i > 0; i--){
				int index = (int)(Math.random()*(i+1));
				Card temp = deck[i];
				deck[i] = deck[index];
				deck[index] = temp;
	    }
	}

	public Card getCard(int x){
		return deck[x];
	}

	public int numberofCards(){ //can't use deck.numberofCards in for loop because it's always changing after deal
		return cardCount;
	}

	public String printDeck(){
		String out = "";
		for (int i = 0; i < cardCount; i++){
			Card k = deck[i];
			out +=  (k.toString() + "\n");
		}
		return out;
	}

	public void resetDeck(){
		int count = 0; //number of cards created so far
		for (int i = 0; i <=3; i++){ //for the suit
			for (int k = 1; k <= 13; k++){ //for the face
				deck[count] = new Card (i,k);
				count++;
			}
		}
	}

	public void reset(){
		cardCount = 312;
		System.out.println("Ran out of cards, resetting deck");
	}

	/*All of the probability methods count cards in the deck that are what the method name
		needs*/
	public void equalToTwentyOne(int x){ //probability they will get equal to 21
		int equalC = 0;
		for (int i = cardsUsed; i < deck.length; i++){
			if (deck[i].getValue() == x){
				equalC++;
			}
		}
		System.out.println("Probability you will get 21 on next hit is \t" + equalC + "/" + cardsLeft);
	}

	public void lessThanTwentyOne(int x){ //probability they will get less than 21
		int lessC = 0;
		for (int i = cardsUsed; i < deck.length; i++){
			if (deck[i].getValue()<= x){
				lessC++;
			}
		}
		System.out.println("Probability you will not bust is \t" + lessC + "/" + cardsLeft);
	}

	public void moreThanTwentyOne(int x){ //probability they will bust
		int moreC = 0;
		for (int i = cardsUsed; i < deck.length; i++){
			if (deck[i].getValue() > x){
				moreC++;
			}
		}
		System.out.println("Probability you will bust is \t" + moreC + "/" + cardsLeft);
	}


}
