package cardGame;

/**
 * Represents a playing card with suit, name, value and picture
 */
public class Card {
    private String cardSuit;
    private String cardName;
    private int cardValue;
    private String cardPicture;

    /**
     * Constructor to create a new card
     *  cardSuit The suit of the card (Hearts, Diamonds, etc)
     *  cardName The name of the card (Ace, King, etc)
     *  cardValue The numeric value of the card
     */
    public Card(String cardSuit, String cardName, int cardValue, String cardPicture) {
        this.cardSuit = validateSuit(cardSuit);
        this.cardName = validateName(cardName);
        this.cardValue = validateValue(cardValue);
        this.cardPicture = cardPicture;
    }

    // Accessor methods
    public String getCardSuit() {
        return cardSuit;
    }

    public String getCardName() {
        return cardName;
    }

    public int getCardValue() {
        return cardValue;
    }

    public String getCardPicture() {
        return cardPicture;
    }

    // Mutator methods
    public void setCardSuit(String cardSuit) {
        this.cardSuit = validateSuit(cardSuit);
    }

    public void setCardName(String cardName) {
        this.cardName = validateName(cardName);
    }

    public void setCardValue(int cardValue) {
        this.cardValue = validateValue(cardValue);
    }

    public void setCardPicture(String cardPicture) {
        this.cardPicture = cardPicture;
    }

    // Validation methods
    private String validateSuit(String suit) {
        if (suit == null || suit.trim().isEmpty()) {
            throw new IllegalArgumentException("Card suit cannot be null or empty");
        }
        suit = suit.trim().toUpperCase();
        if (!suit.equals("HEARTS") && !suit.equals("DIAMONDS") && 
            !suit.equals("CLUBS") && !suit.equals("SPADES")) {
            throw new IllegalArgumentException("Invalid card suit: " + suit);
        }
        return suit;
    }

    private String validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Card name cannot be null or empty");
        }
        return name.trim();
    }

    private int validateValue(int value) {
        if (value < 1 || value > 13) {
            throw new IllegalArgumentException("Card value must be between 1 and 13");
        }
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Card other = (Card) obj;
        return this.cardValue == other.cardValue &&
               this.cardSuit.equals(other.cardSuit);
    }

    @Override
    public String toString() {
        return String.format("%s of %s (%s) [Value: %d]", 
                           cardName, cardSuit, cardPicture, cardValue);
    }
}
