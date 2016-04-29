public class Blackjack{
	public static void main (String [] args){
		System.out.println("Welcome to the game of blackjack!" + "\n Remember, 11, 12, and 13 card values are all 10.");
		int numPlayers = 0;
		System.out.println("How many players do you have? (max is 6)");
		numPlayers = IO.readInt();
		while (numPlayers <= 0 || numPlayers > 6){
			IO.reportBadInput();
			numPlayers = IO.readInt();
		}
		Player players [] = new Player[numPlayers];
		Player splithand [] = new Player [numPlayers];
		for (int i = 0; i < numPlayers; i++){
			players[i] = new Player();
			splithand[i] = new Player();
		}
		Player dealer = new Player();
		Deck deck = new Deck();
		deck.shuffle();

		for (int i = 0; i <numPlayers; i++){
			players[i].hit(deck.deal()); //start off with 2 cards
			players[i].hit(deck.deal());
		}

		dealer.hit(deck.deal());

		//actual game start
		boolean keepPlaying = true;
		int insureCount = 0;
		//int doublebet = 0;
		int bet = 0;
		boolean doubled = false;
		boolean splitted = false;
		while (keepPlaying){

			for (int i = 0; i < numPlayers; i++){ //all players go
				splitted = false;
				doubled = false; //resets  booleans for every player
				//intial bets placed
				deck.printHint();
				System.out.println("Player " + i + " balance is: " + players[i].getBalance()); //start with 100
				System.out.println("How much do you want to bet player?" + i);
					bet = IO.readInt();
					while (bet > 100 || bet > players[i].getBalance() || bet <= 0){ //bet cant be over 100 or more than player's current balance
						IO.reportBadInput();
						bet = IO.readInt();
					}
				//players[i].bet(bet);
				//bet = 0; //reset bet after

				//print cards and value
				System.out.println("Player " + i + ", your cards are \n" + players[i].printHand());
				System.out.println("Player " + i + ", your value of hand is " + players[i].getValue());
				System.out.println("Dealer card: " + "\n" + dealer.printHand());
				//if dealer card is ace, player has option to insure
				if (dealer.insuranceCheck() == true && players[i].getValue() <= 21){
					System.out.println("You have the option to insure your bet, do you want to? (1 for yes, 0 for no) \n P.S Insurance is usually a bad idea");
					int insureOption = IO.readInt();
					while (insureOption != 1 && insureOption != 0){
						IO.reportBadInput();
						insureOption = IO.readInt();
					}
					if (insureOption == 1){
						System.out.println("How much do you want to insure?");
						int insureBet = IO.readInt();
						while (insureBet > bet /2){
							IO.reportBadInput();
							System.out.println("Insurance bet has to be less than or equal than your original double down bet or regular bet");
							insureBet = IO.readInt();
						}
						players[i].insuranceBet(insureBet);
					}
				}

				if (players[i].isSplitable()){
					//players[i].split(splithand);
					System.out.println("You have the option to split your hand, do you want to? (1 for yes, 0 for no");
					int splitoption = IO.readInt();
					while (splitoption != 1 && splitoption != 0){
						IO.reportBadInput();
						splitoption = IO.readInt();
					}
					if (splitoption == 1){ //choose to split
						splitted = true;
						players[i].split();
						players[i].hitSplit1(deck.deal());
						players[i].hitSplit2(deck.deal()); //splits the original hand and then deals a card to each hand
						while (bet * 2 > players[i].getBalance() || bet <= 0){ //checks error conditions for double the bet
							IO.reportBadInput();
							System.out.println("You cannot split with this amount, please enter a different number to split that is less than or equal to half your balance");
							bet = IO.readInt();
						}
						System.out.println("new bet is " + (bet/2) + " for each hand");
						players[i].betHand1(bet/2);
						players[i].betHand2(bet/2);
						//start hitting for first split hand
						System.out.println("Player " + i + ", your first hand is \n" + players[i].printSplitHand1() + "\n Value is " + players[i].getSplit1Value() + "\n Do you want to hit (1) or stand (0)");
						int split1choice = IO.readInt();
						while (split1choice != 1 && split1choice != 0){
							IO.reportBadInput();
							split1choice = IO.readInt();
						}
						while (split1choice == 1){ //choose to hit
							players[i].hitSplit1(deck.deal());
							if (players[i].getSplit1Value() > 21){ //check if they busted
								System.out.println(players[i].printSplitHand1() + "\n You busted \n Value of your first split hand was " + players[i].getSplit1Value());
								players[i].lostHand1();
								break; //can't hit anymore if they lose
							}

							System.out.println(players[i].printSplitHand1() + "\n Value of hand is " + players[i].getSplit1Value() + "\n Hit or stand again? (0 or 1) ");
							split1choice = IO.readInt();
						}

						//start hitting for second split hand
						System.out.println("Player " + i + ", your second hand is \n" + players[i].printSplitHand2() + "\n Value is " + players[i].getSplit2Value() + "\n Do you want to hit (1) or stand (0)");
						int split2choice = IO.readInt();
						while (split2choice != 1 && split2choice != 0){
							IO.reportBadInput();
							split2choice = IO.readInt();
						}
						while (split2choice == 1){ //choose to hit
							players[i].hitSplit2(deck.deal());
							if (players[i].getSplit2Value() > 21){ //check if they busted
								System.out.println(players[i].printSplitHand2() + "\n You busted \n Value of your second split hand was " + players[i].getSplit2Value());
								players[i].lostHand2();
								break; //can't hit anymore if they lose
							}

							System.out.println(players[i].printSplitHand2() + "\n Value of hand is " + players[i].getSplit2Value() + "\n Hit or stand again? (0 or 1) ");
							split2choice = IO.readInt();
						}


					}
				}

				if (splitted == false){ // if they didnt split, they can double
					System.out.println("Do you want to know if you should double down? (1 for yes, 0 for no");
					int doubleoption = IO.readInt();
					while (doubleoption!= 1 && doubleoption != 0){
						IO.reportBadInput();
						doubleoption = IO.readInt();
					}
					if (doubleoption == 1){
						if (players[i].getValue() == 9 || players[i].getValue() == 10 || players[i].getValue() == 11){
							System.out.println("You should double down");
						} else{
							System.out.println("You should not double down");
						}	
					}
					//first ask them if they want to double down
					System.out.println("Do you want to double down player " + i + " ?(1 for yes, 0 for no)");
					int ddoption = IO.readInt();
					while (ddoption != 1 && ddoption != 0){
						IO.reportBadInput();
						ddoption = IO.readInt();
					}
					if (ddoption == 1){ //choose to double
						while (bet * 2 > players[i].getBalance() || bet <= 0){ //checks error conditions for double the bet
							IO.reportBadInput();
							System.out.println("You cannot double down with this amount, please enter a different number to double down that is less than or equal to half your balance");
							bet = IO.readInt();
						}
						System.out.println("New bet is: " + bet * 2);
						players[i].bet((2 * bet)); //doubles bet
						players[i].hit(deck.deal());
						doubled = true;
						System.out.println("\n" + "Your cards are: \n" + players[i].printHand());
						System.out.println("Value of hand is:" + players[i].getValue());
						if (players[i].getValue() > 21){ //check if they busted
									System.out.println(players[i].printHand() + "\n You busted \n Value of hand after you busted is " + players[i].getValue());
									players[i].lost();
						}

					} 
					if (doubled == false && splitted == false){
						players[i].bet(bet);
					}

					System.out.println("Player " + i + ", your cards are \n" + players[i].printHand());
					System.out.println("Player " + i + ", your value of hand is " + players[i].getValue());
					if (!(doubled)){
						//if they didn't split or double, they can hit regularly
						//hinting system begins
						System.out.println("Do you want a hint? (1 for yes, 0 for no");
						int optionHint = IO.readInt();
						while (optionHint != 1 && optionHint != 0){
							IO.reportBadInput();
							optionHint = IO.readInt();
						}
						if (optionHint == 1){ //asks which one they want, and appropiately calls the right method from the deck class, with the parameter being the difference betweeen 21 and the player's hand value
							System.out.println("Do you want to know probability to get 21 on next hit(1), probability of not busting(2), or probability of busting(3)? You can only pick one, be careful");
							int opHint = IO.readInt();
							while (opHint != 1 && opHint != 2 && opHint != 3){
								IO.reportBadInput();
								opHint = IO.readInt();
							}
							if (opHint == 1){
								deck.equalToTwentyOne(players[i].hint());
							}
							if (opHint == 2){
								deck.lessThanTwentyOne(players[i].hint());
							}
							if (opHint == 3){
								deck.moreThanTwentyOne(players[i].hint());
							}
						}

						System.out.println("Do you want to hit or stand? (0 to stand, 1 to hit)");
						int choice = IO.readInt();
						while (choice != 0 && choice != 1){
							IO.reportBadInput();
							choice = IO.readInt();
						}
						while (choice == 1){ //choose to hit
							players[i].hit(deck.deal());
							if (players[i].getValue() > 21){ //check if they busted
								System.out.println(players[i].printHand() + "\n You busted \n Value of hand was " + players[i].getValue());
								players[i].lost();
								break; //can't hit anymore if they lose
							}

							System.out.println(players[i].printHand() + "\n Value of hand is " + players[i].getValue());
							
							System.out.println("Another hint? (1 for yes, 0 for no)"); //hint for every single hit
							int anothaone = IO.readInt();
							while (anothaone != 1 && anothaone != 0){
								IO.reportBadInput();
								anothaone = IO.readInt();
							}
							if (anothaone == 1){ //basiclaly hint system all over again
								System.out.println("Do you want to know probability to get 21 on next hit(1), probability of not busting(2), or probability of busting(3)? You can only pick one, be careful");
								int anothaone2 = IO.readInt();
								while (anothaone2 != 1 && anothaone2 != 2 && anothaone2 != 3){
									IO.reportBadInput();
									anothaone2 = IO.readInt();
								}
								if (anothaone2 == 1){
									deck.equalToTwentyOne(players[i].hint());
								}
								if (anothaone2 == 2){
									deck.lessThanTwentyOne(players[i].hint());
								}
								if (anothaone2 == 3){
									deck.moreThanTwentyOne(players[i].hint());
								}
							}
							System.out.println("Hit or stand again? (0 or 1)");
							choice = IO.readInt();
						}	
					}
				}
			}


			System.out.println("Dealers turn!");
			System.out.println(dealer.printHand());
			System.out.println("Dealer value of hand is " + dealer.getValue());
			if (dealer.getValue() < 17){ //to keep hitting until value of cards is at least 17
				do {
					dealer.hit(deck.deal());
					insureCount++;
					System.out.println(dealer.printHand());
					System.out.println("Dealer Value of hand is " + dealer.getValue());
				} while (dealer.getValue() <=  16); //<=16 */
			}

			if (dealer.hasBlackJack()){ //if the dealer has a blackjack and the first card was an ace, everyone who insured win's their bet
				System.out.println("Dealer has blackjack! Won your insurance!");
				for (int z = 0; z < numPlayers; z++){
					players[z].wonInsurance();
				}
			} else if (!(dealer.hasBlackJack())){
				for (int az = 0; az < numPlayers; az++){
					players[az].lostInsurance();
				}
			}

			if (dealer.getValue()>21){ //checks for dealer busting
				System.out.println("Dealer busted!");
				for (int k = 0; k < numPlayers; k++){
					if (players[k].splitted()){ //whoever splitted and didnt bust win
						if (players[k].getSplit1Value() <= 21){
							players[k].wonHand1();
							System.out.println("You won your first hand since dealer busted");
						}
						if (players[k].getSplit2Value() <= 21){
							players[k].wonHand2();
							System.out.println("You won your second hand since dealer busted");							
						}
					}
					//for players who didnt split
					if (players[k].getValue()<=21){ //players that didnt bust win if dealer busts
						players[k].won();
						System.out.println("Player " + k + " is one of the winners");
					}
				}
			}
			// checks if players who havent busted won, lost, or tied

			for (int j = 0; j < numPlayers; j++){
				if (players[j].getValue()<=21 && dealer.getValue()<=21 && players[j].splitted() == false){ //players that didnt bust and did not split
					if (players[j].getValue()< dealer.getValue()){
						players[j].lost();
						System.out.println("Player " + j + ",you lost the round");
					}
					else if (players[j].getValue() > dealer.getValue()){
						players[j].won();
						System.out.println("Player " + j + " You won the round!");
					}
					else if (players[j].getValue() == dealer.getValue()){
						players[j].tied();
						System.out.println("Player " + j + " ,You tied");
					}
				}
			}
			//betting conlcusion for players that splitted
			//alot of copy paste was done here, player's hands and dealer's hand cant be over 21
			for (int x = 0; x < numPlayers; x++){
				if (players[x].splitted() == true ){//hands that didnt bust
					if (players[x].getSplit1Value() < dealer.getValue() && players[x].getSplit1Value() <= 21 && dealer.getValue() <= 21){
						players[x].lostHand1();
						System.out.println("Player " + x + ", your first hand lost");
					}
					if (players[x].getSplit1Value() > dealer.getValue() && players[x].getSplit1Value() <= 21 && dealer.getValue() <= 21){
						players[x].wonHand1();
						System.out.println("Player " + x + ", your first hand won");
					}
					if (players[x].getSplit1Value() == dealer.getValue() && players[x].getSplit1Value() <= 21 && dealer.getValue() <= 21){
						players[x].tiedHand1();
						System.out.println("Player " + x + ", your first hand pushed with dealer");
					}
					if (players[x].getSplit2Value() < dealer.getValue() && players[x].getSplit2Value() <= 21 && dealer.getValue() <= 21){
						players[x].lostHand2();
						System.out.println("Player " + x + ", your second hand lost");
					}
					if (players[x].getSplit2Value() > dealer.getValue() && players[x].getSplit2Value() <= 21 && dealer.getValue() <= 21){
						players[x].wonHand2();
						System.out.println("Player " + x + ", your second hand won");
					}
					if (players[x].getSplit2Value() == dealer.getValue() && players[x].getSplit2Value() <= 21 && dealer.getValue() <= 21){
						players[x].tiedHand2();
						System.out.println("Player " + x + ", your second hand pushed with dealer");
					}
				}
			}

			for (int a = 0; a < numPlayers; a++){ //prints out balances of players
				System.out.println("Player " + a + " balance is: " + players[a].getBalance());
			}


			//asking to play again
			System.out.println("Do you want to play another round? (1 for yes, 0 for no)");
			int redo = IO.readInt();
			while (redo != 1 && redo != 0){
				IO.reportBadInput();
				redo = IO.readInt();
			}

			if (redo == 0){
				keepPlaying = false;
			}else { //resets hand 
				//new deck and reshuffle deck so it doesn't run out of cards
				deck = new Deck();
				deck.shuffle();
				for (int b = 0; b < numPlayers; b++){
					//if they're broke, they enter a deposit less than 100
					if (players[b].getBalance() <= 0){
						System.out.println("Player " + b + ", You're broke. Deposit amount less than 100");
						int dep = IO.readInt();
						while (dep <= 0 || dep > 100){
							IO.reportBadInput();
							dep = IO.readInt();
						}
						players[b].deposit(dep);
					}
					players[b].clearHand(); //resets player's hand, acecount, if they splitted and value
					players[b].hit(deck.deal()); //2 new cards
					players[b].hit(deck.deal());

				}
				dealer.clearHand();
				dealer.hit(deck.deal());
				//System.out.println("Dealer card: " + "\n" + dealer.printHand()); //only show 1 card
				//dealer.hit(deck.deal());
				bet = 0;
			}
		}

	}
}