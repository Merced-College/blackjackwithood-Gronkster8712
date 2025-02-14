package cardGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class CardGame {
    private static ArrayList<Card> deckOfCards = new ArrayList<Card>();
    private static ArrayList<Card> playerCards = new ArrayList<Card>();

    public static void main(String[] args) {
        Scanner input = null;
        try {
            input = new Scanner(new File("cards.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("error");
            e.printStackTrace();
        }

        while(input.hasNext()) {
            String line = input.nextLine().trim();
            if (line.isEmpty()) continue;  // Skip empty lines

            String[] fields = line.split(",");
            Card newCard = new Card(fields[0], fields[1].trim(),
                    Integer.parseInt(fields[2].trim()), fields[3]);
            deckOfCards.add(newCard);    
        }

        shuffle();

        // Deal 5 cards
        for(int i = 0; i < 5; i++) {
            playerCards.add(deckOfCards.remove(0));
        }

        System.out.println("Player's cards:");
        for(Card c: playerCards)
            System.out.println(c);

        // Check for various poker hands
        System.out.println("\nPoker Hand Analysis:");
        if (checkForRoyalFlush()) {
            System.out.println("Royal Flush!");
        } else if (checkForStraightFlush()) {
            System.out.println("Straight Flush!");
        } else if (checkFor4Kind()) {
            System.out.println("Four of a Kind!");
        } else if (checkForFullHouse()) {
            System.out.println("Full House!");
        } else if (checkForFlush()) {
            System.out.println("Flush!");
        } else if (checkForStraight()) {
            System.out.println("Straight!");
        } else if (checkFor3Kind()) {
            System.out.println("Three of a Kind!");
        } else if (checkFor2Pair()) {
            System.out.println("Two Pair!");
        } else if (checkFor2Kind()) {
            System.out.println("One Pair!");
        } else {
            System.out.println("High Card!");
        }
    }

    public static void shuffle() {
        for (int i = 0; i < deckOfCards.size(); i++) {
            int index = (int) (Math.random()*deckOfCards.size());
            Card c = deckOfCards.remove(index);
            deckOfCards.add(c);
        }
    }

    public static boolean checkFor2Kind() {
        for(int i = 0; i < playerCards.size() - 1; i++) {
            Card current = playerCards.get(i);
            for(int j = i+1; j < playerCards.size(); j++) {
                Card next = playerCards.get(j);
                if(current.getCardValue() == next.getCardValue())
                    return true;
            }
        }
        return false;
    }

    public static boolean checkFor2Pair() {
        int pairCount = 0;
        ArrayList<Integer> pairedValues = new ArrayList<>();

        for(int i = 0; i < playerCards.size() - 1; i++) {
            if (pairedValues.contains(playerCards.get(i).getCardValue())) 
                continue;

            for(int j = i+1; j < playerCards.size(); j++) {
                if (playerCards.get(i).getCardValue() == playerCards.get(j).getCardValue()) {
                    pairCount++;
                    pairedValues.add(playerCards.get(i).getCardValue());
                    break;
                }
            }
        }
        return pairCount == 2;
    }

    public static boolean checkFor3Kind() {
        for(int i = 0; i < playerCards.size() - 2; i++) {
            int count = 1;
            for(int j = i+1; j < playerCards.size(); j++) {
                if(playerCards.get(i).getCardValue() == playerCards.get(j).getCardValue())
                    count++;
            }
            if(count == 3) return true;
        }
        return false;
    }

    public static boolean checkFor4Kind() {
        for(int i = 0; i < playerCards.size() - 3; i++) {
            int count = 1;
            for(int j = i+1; j < playerCards.size(); j++) {
                if(playerCards.get(i).getCardValue() == playerCards.get(j).getCardValue())
                    count++;
            }
            if(count == 4) return true;
        }
        return false;
    }

    public static boolean checkForFullHouse() {
        return checkFor3Kind() && checkFor2Kind();
    }

    public static boolean checkForFlush() {
        String suit = playerCards.get(0).getCardSuit();
        for(int i = 1; i < playerCards.size(); i++) {
            if(!playerCards.get(i).getCardSuit().equals(suit))
                return false;
        }
        return true;
    }

    public static boolean checkForStraight() {
        ArrayList<Integer> values = new ArrayList<>();
        for(Card c : playerCards) {
            values.add(c.getCardValue());
        }
        Collections.sort(values);

        // Check for Ace-low straight
        if(values.get(0) == 1 && values.get(1) == 10 && values.get(2) == 11 
           && values.get(3) == 12 && values.get(4) == 13) {
            return true;
        }

        // Check for normal straight
        for(int i = 0; i < values.size() - 1; i++) {
            if(values.get(i + 1) - values.get(i) != 1)
                return false;
        }
        return true;
    }

    public static boolean checkForStraightFlush() {
        return checkForStraight() && checkForFlush();
    }

    public static boolean checkForRoyalFlush() {
        if (!checkForStraightFlush()) return false;
        ArrayList<Integer> values = new ArrayList<>();
        for(Card c : playerCards) {
            values.add(c.getCardValue());
        }
        Collections.sort(values);
        return values.get(0) == 1 && values.get(4) == 13;
    }
}
