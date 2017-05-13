
public class Card implements Comparable<Card> {

	private final int num;
	private final Suit suit;

	public Card(String card) throws Exception {
		super();
		suit = Suit.getSuit(card.substring(0, 1));
		String number = card.substring(1);
		switch (number) {
		case "J":
			num = 11;
			break;
		case "Q":
			num = 12;
			break;
		case "K":
			num = 13;
			break;
		case "A":
			num = 14;
			break;
		case "2":
			num = 15;
			break;
		default:
			num = Integer.parseInt(number);
		}
		if (num < 1 || num > 15)
			throw new Exception("Not a valid");
	}

	public Card(int num, Suit suit) throws Exception {
		super();
		if (num < 1 || num > 15)
			throw new Exception("Not a valid");
		this.num = num;
		this.suit = suit;
	}

	public int getNum() {
		return num;
	}

	public Suit getSuit() {
		return suit;
	}

	@Override
	public int compareTo(Card o) {
		if(num==o.num)return suit.compareTo(o.suit);
		return num - o.num;
	}

	@Override
	public String toString() {
		switch (num) {
		case 11:
			return suit.getS() + "J";
		case 12:
			return suit.getS() + "Q";
		case 13:
			return suit.getS() + "K";
		case 14:
			return suit.getS() + "A";
		case 15:
			return suit.getS() + "2";
		default:
			return suit.getS() + num;
		}
	}

	public static Card[] getCards(String... s) throws Exception {
		if (s == null)
			throw new Exception("Not a valid");
		Card[] cards = new Card[s.length];
		for (int i = 0; i < s.length; i++) {
			cards[i] = new Card(s[i]);
		}
		return cards;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + num;
		result = prime * result + ((suit == null) ? 0 : suit.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		if (num != other.num)
			return false;
		if (suit != other.suit)
			return false;
		return true;
	}
}
