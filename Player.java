import java.util.ArrayList;
public class Player{
	
	static public int playerNumber = 0;
	private int total = 0;
	private int betAmount = 0;
	private int insuranceBet = 0;
	ArrayList <Card> hand = new ArrayList<Card>();
	ArrayList <Card> splithand1 = new ArrayList <Card>();
	ArrayList <Card> splithand2 = new ArrayList <Card>();
	private int aceCount = 0;
	private int hand1bet = 0, hand2bet = 0;
	public boolean splitted = false;
	
	public Player(){
		playerNumber++;
		total = 100;
	}

	public void bet(int x){ //check if bet is amount greater in main method; bet <= 0 = quit game
		betAmount += x;

	}

	public int getBalance(){
		return total;
	}

	public void hit(Card i){
		hand.add(i);
		if (i.getFace() == 1){
			aceCount++;
		}
	}

	public int getValue(){
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

	public String printHand(){
		String out = "";
		for (int i = 0; i < hand.size(); i++){
			Card k = hand.get(i);
			out += (k.toString() + "\n");
		}
		return out;
	}

	public void lost(){
		total -= betAmount;
		betAmount = 0;
	}

	public void won(){
		total += betAmount;
		betAmount = 0;
	}

	public void tied(){
		total += 0;
		betAmount = 0;
	}

	public void clearHand(){
		hand.clear();
		aceCount = 0;
		splithand1acecount = 0;
		splithand2acecount = 0;
		splitted = false;
	}


	public void deposit(int x){
		total += x;
	}

	public boolean insuranceCheck(){
		if (hand.get(0).getFace() == 1){
			return true;
		} else
			return false;
	}

	public void insuranceBet(int x){
		insuranceBet += x;
	}

	public boolean hasBlackJack(){
		if ((hand.get(0).getFace() ==1 && hand.get(1).getValue() == 10)){
			return true;
		} else
			return false;
	}

	public void wonInsurance(){
		total += 2 * insuranceBet;
		insuranceBet = 0;
	}

	public void lostInsurance(){
		total -= insuranceBet;
		insuranceBet = 0;
	}

	public int hint(){ //get's integer parameter needed for deck's probability methods
		int equal =  21 - getValue();
		return equal;
	}

	/*All the split methods, i have duplicates for hand 1 and for hand 2 because you need 
		2 different methods.
		Also included betting systems seperate for each hand*/
	public boolean isSplitable(){
		if (hand.get(0).getValue() == hand.get(1).getValue()){
			return true;
		} else{
			return false;
		}
	}

	public void split(){ //split hands in the main
		splitted = true;
		Card hand1 = hand.remove(0);
		Card hand2 = hand.remove(0);
		splithand1.add(hand1);
		splithand2.add(hand2);
	}

	private int splithand1acecount = 0;
	public void hitSplit1(Card i){
		splithand1.add(i);
		if (i.getFace() == 1){
			splithand1acecount++;
		}
	}

	public int getSplit1Value(){
		int sumx=0;
		int aces1=splithand1acecount;
		for(int i=0;i<splithand1.size();i++){
			sumx+=splithand1.get(i).getValue();
		}		
		while(aces1 > 0 && sumx>21){ //change acecount to minus if needed //MIGHT NEED TO TAKE OUT = SIGN
			sumx -=10;
			splithand1acecount--;
		}
		return sumx;
	}

	private int splithand2acecount = 0;
	public void hitSplit2(Card i){
		splithand2.add(i);
		if (i.getFace() == 1){
			splithand2acecount++;
		}
	}

	public int getSplit2Value(){
		int sumc=0;
		int aces2=splithand2acecount;
		for(int i=0;i<splithand2.size();i++){
			sumc+=splithand2.get(i).getValue();
		}		
		while(aces2 > 0 && sumc>21){ //change acecount to minus if needed //MIGHT NEED TO TAKE OUT = SIGN
			sumc -=10;
			splithand2acecount--;
		}
		return sumc;
	}

	

	public String printSplitHand1(){
		String out = "";
		for (int i = 0; i < splithand1.size(); i++){
			Card k = splithand1.get(i);
			out += (k.toString() + "\n");
		}
		return out;
	}

	public String printSplitHand2(){
		String out = "";
		for (int i = 0; i < splithand2.size(); i++){
			Card k = splithand2.get(i);
			out += (k.toString() + "\n");
		}
		return out;
	}

	public void betHand1(int x){
		hand1bet += x;
	}

	public void lostHand1(){
		total -= hand1bet;
		hand1bet = 0;
	}

	public void wonHand1(){
		total += hand1bet;
		hand1bet = 0;
	}

	public void tiedHand1(){
		total += 0;
		hand1bet = 0;
	}

	public void betHand2(int x){
		hand2bet += x;
	}

	public void lostHand2(){
		total -= hand2bet;
		hand2bet = 0;
	}

	public void wonHand2(){
		total += hand2bet;
		hand2bet = 0;
	}

	public void tiedHand2(){
		total += 0;
		hand2bet = 0;
	}

	public boolean splitted(){
		return splitted;
	}


}