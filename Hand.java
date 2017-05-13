import java.util.Arrays;

public class Hand {

	private final Card[] cards;

	public Hand(Card... cards) {
		super();
		this.cards = cards;
	}

	public Card[] getCards() {
		return cards;
	}

	public int getLen() {
		return cards.length;
	}

	public Card get(int i) {
		if (i > cards.length - 1) {
			return null;
		}
		return cards[i];
	}

	private boolean isAAABB() {
		return isAAABB(cards);
	}

	private boolean isABCDE() {
		return isABCDE(cards);
	}

	private boolean isSSSSS() {
		return isSSSSS(cards);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(cards);
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
		Hand other = (Hand) obj;
		if (!Arrays.equals(cards, other.cards))
			return false;
		return true;
	}

	public boolean canKO(Hand o) {
		if (o == null)
			return true;
		if (cards.length != o.getLen())
			return false;
		if (cards.length <= 3) {
			return cards[0].getNum() - o.get(0).getNum() > 0;
		}
		if (o.isAAABB()) {
			if (!this.isAAABB())
				return false;
			return cards[2].getNum() - o.get(2).getNum() > 0;
		}
		if (o.isABCDE()) {
			if (!this.isABCDE())
				return false;
			return cards[0].getNum() - o.get(0).getNum() > 0;
		}
		if (o.isSSSSS()) {
			if (!this.isSSSSS() || cards[0].getSuit() != o.get(0).getSuit())
				return false;
			return cards[0].getNum() - o.get(0).getNum() > 0;
		}
		return false;
	}

	public static boolean isValid(Card... cards) {
		if (cards == null || cards.length == 0)
			return false;
		Arrays.sort(cards);
		if (cards.length == 1)
			return true;
		else if (cards.length == 2) {
			if (cards[0].getNum() != cards[1].getNum())
				return false;
		} else if (cards.length == 3) {
			if (cards[0].getNum() != cards[1].getNum() || cards[1].getNum() != cards[2].getNum())
				return false;
		} else if (cards.length == 4) {
			return false;
		} else if (cards.length >= 5) {
			if (!(isAAABB(cards) || isABCDE(cards) || isSSSSS(cards)))
				return false;
		}
		return true;
	}

	public static boolean isAAABB(Card... cards) {
		if (cards.length != 5)
			return false;
		if (cards[0].getNum() != cards[1].getNum())
			return false;
		if (cards[4].getNum() != cards[3].getNum())
			return false;
		if (cards[2].getNum() != cards[1].getNum() && cards[2].getNum() != cards[3].getNum())
			return false;
		return true;
	}

	public static boolean isABCDE(Card... cards) {
		if (cards.length < 5)
			return false;
		for (int i = 0; i < cards.length - 1; i++) {
			if (cards[i].getNum() + 1 != cards[i + 1].getNum())
				return false;
		}
		return true;
	}

	public static boolean isSSSSS(Card... cards) {
		return false;
//		if (cards.length < 5)
//			return false;
//
//		for (int i = 1; i < cards.length; i++) {
//			if (cards[0].getSuit() != cards[i].getSuit())
//				return false;
//		}
//		return true;
	}

	@Override
	public String toString() {
		return Arrays.toString(cards);
	}

}
