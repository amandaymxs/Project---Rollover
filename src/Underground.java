import java.util.Scanner;
import java.util.Random;

public class Underground {

	public static void main(String[] args) {

		System.out.println("Welcome to Underground");
		System.out.println("Tonight's game is called Rollover.");
		System.out.println(
				"The minimum to sit at this table is $500. The dealer will exchange your seating amount into chips.");
		System.out.println();
		System.out.println("How to play Rollover.\n"
				+ "Place an initial bet ( minimum of $20), once the dice are rolled no changes or withdrawals can be made.\n"
				+ "To start, the dealer will roll the two dice three times. The maximum of each roll will be used for reference for the first round."
				+ "You will roll two dice simutaneously.\n"
				+ "Winning hand: The minimum value of your roll dice must be greater or equal to the average of the maximum of the last 3 rolls.\n"
				+ "When the average of the sum of the maximum values of the last three roles be a decimal place value, we will round down to the closest integer number."
				+ "Losing hand: The sum of the face of the dice does not meet these requirements.\n");
		System.out.println();
		System.out.println(
				"When you win the round, you will win your initial bet multiplied by the number of successive wins in increments of 0.25.\n"
						+ "When you lose the round, you lose your initial bet.");
		System.out.println();
		System.out.println(
				"You can only re-buy your chips when you have 10% of your initial holdings upon seating is remaining.\n"
						+ "You may only rebuy up to the initial chips you started with."
						+ "You may replenish your chips as many times as you wish under these conditions.");
		System.out.println();
		System.out.println("Lets play.");
		System.out.println();

		Scanner playRollover = new Scanner(System.in);
		Random diceRoll = new Random();

		double initialAmount = 100; // to be removed
		int reBuy = 0;
		int bet; // to be removed

//		int initialAmount, reBuy, bet; // remove comment
		int rollOne, rollTwo, maximumRoll, minimumRoll;
		int m0 = 0, m1 = 0, m2 = 0;
		int totalMaximum = 0;
		int averageMaximum;

		int userRoll;
		double chipCount;
		double multiplier = 1.0;
		double winnings = 0;

		while (true) {
			System.out.println("Enter your initial seating amount.");
			initialAmount = playRollover.nextInt();
			if (initialAmount < 500) {
				System.out.println("Your initial amount must be equal or greater than $500");
				continue;
			}
			System.out.println("Your initial seating amount is $" + initialAmount + ".");
			System.out.println("Enter the amount again to confirm.");
			int confirmAmount = playRollover.nextInt();
			if (confirmAmount != initialAmount) {
				System.out.println("Error 1001: The amounts you entered are not the same.");
				continue;
			} break;
		}
		System.out.println();
		System.out.println("Rollover will begin.");
		System.out.println();
		chipCount = initialAmount;
		System.out.println("The first three rolls of the dealer are:");

		for (int i = 0; i < 3; i++) {
			rollOne = diceRoll.nextInt(6) + 1;
			rollTwo = diceRoll.nextInt(6) + 1;

			if (rollOne >= rollTwo) {
				maximumRoll = rollOne;
			} else {
				maximumRoll = rollTwo;
			}

			totalMaximum += maximumRoll;
			m0 = m1;
			m1 = m2;
			m2 = maximumRoll;
			System.out.println(rollOne + " , " + rollTwo);
		}
		averageMaximum = totalMaximum / 3;
		System.out.println("The maximums of each of the last three rolls are " + m0 + ", " + m1 + ", and " + m2 + ".");
		System.out.println("The total maximum of the three previous rolls is " + totalMaximum + ".");
		System.out.println("The average of the first three rolls is " + averageMaximum + ".");

		while (true) {
			System.out.println();
			System.out.println("Place your bet.");
			bet = playRollover.nextInt();
			if ((bet != 0) && (bet < 20) || (bet > chipCount)) {
				System.out.printf("Error 1002: Your bet must be more than $20 and less than $%.2f", chipCount);
				System.out.println(".");
				System.out.println();
				System.out.println("Place your bet.");
				bet = playRollover.nextInt();
				continue;
			}

			if (bet == 0) {
				System.out.println("Your final holding is $" + chipCount + ".");
				System.out.println("You have exited the game.");
				System.exit(0);
			}

			chipCount -= bet;
			System.out.println("Press 1 to roll.");
			userRoll = playRollover.nextInt();
			if (userRoll != 1) {
				System.out.println("Error 1004: Press 1 to roll.");
			} else {
				rollOne = diceRoll.nextInt(6) + 1;
				rollTwo = diceRoll.nextInt(6) + 1;
				System.out.println();
				System.out.println("You rolled a " + rollOne + " and " + rollTwo + ".");

				if (rollOne >= rollTwo) {
					maximumRoll = rollOne;
					minimumRoll = rollTwo;
				} else {
					maximumRoll = rollTwo;
					minimumRoll = rollOne;
				}

				m0 = m1;
				m1 = m2;
				m2 = maximumRoll;

				if (minimumRoll >= averageMaximum) {
					winnings = bet * multiplier;
					multiplier += 0.25;
					System.out.println();
					System.out.printf("You win! You won $%.2f", winnings);
					System.out.println(".");
					chipCount = chipCount + bet + winnings;
					System.out.printf("You are now holding $%.2f", chipCount);
					System.out.println(".");
				} else {
					System.out.println();
					System.out.println("You lost your bet.");
					System.out.printf("You are now holding $%.2f", chipCount);
					System.out.println(".");
				}

				if (chipCount <= (initialAmount * 0.1)) {
					while (true) {
						System.out.printf("Would you like to re-buy up to your initial amount of $%.2f", initialAmount);
						System.out.println("? (Y/N)");
						String tryAgain = playRollover.next();
						if (!tryAgain.equalsIgnoreCase("Y")) {
							break;
						}
						System.out.println("Enter the amount you would like to re-buy.");
						reBuy = playRollover.nextInt();
						if ((reBuy + chipCount) > initialAmount) {
							System.out.printf("Error: Your total on hand cannot exceed your initial amount of $%.2f",
									initialAmount);
							System.out.println(".");
						}
						System.out.println("Enter the amount again to confirm re-buy.");
						int confirmAmount = playRollover.nextInt();
						if (confirmAmount != reBuy) {
							System.out.println("Error: The amounts you entered are not the same.");
						} break;
					}
				}
				chipCount = chipCount + reBuy;

				System.out.println();
				System.out.println("The last three maximums of each roll are " + m0 + ", " + m1 + ", and " + m2 + ".");
				System.out
						.println("The average of the maximum of the last three rolls is " + ((m0 + m1 + m2) / 3) + ".");
				System.out.println();
				System.out.println("To play again you can enter your next bet.");
				System.out.println("To exit the game, enter 0");
				if (chipCount == 0) {
					System.out.println("Thank you for playing Rollover. You have no more chips to play.");
					System.exit(0);
				}

			}
		}
	}
}