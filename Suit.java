
public enum Suit {
	HEART("♥"), SPADE("♠"), CLUB("♣"), DIAMOND("♦");
	private final String s;

	private Suit(String s) {
		this.s = s;
	}

	public String getS() {
		return s;
	}

	public static Suit getSuit(String s) {
		for (Suit suit : Suit.values()) {
			if (suit.s.equals(s)) {
				return suit;
			}
		}
		return null;
	}
}
