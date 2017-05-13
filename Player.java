import java.util.Arrays;

public class Player {
	final String name;
	final Card[] cards;
	final boolean[] status;

	public Player(String name, String[] cs) throws Exception {
		super();
		this.name = name;
		cards = Card.getCards(cs);
		Arrays.sort(cards);
		status = new boolean[cards.length];
	}
	
	public Card getCard(String card){
		for(Card c: cards){
			if(c.toString().equals(card)){
				return c;
			}
		}
		return null;
	}

	public String getName() {
		return name;
	}

	public int getLeft() {
		int left = 0;
		for (boolean b : status) {
			if (!b)
				left++;
		}
		return left;
	}

	public Card getNext(int offset) {
		for (int i = 0; i < status.length; i++) {
			if (!status[i]) {
				if (offset == 0) {
					status[i] = true;
					return cards[i];
				}
				offset--;
			}
		}
		return null;
	}

	public void back(Card[] cs) {
		int j = 0;
		for (int i = 0; i < cards.length; i++) {
			if (cards[i] == cs[j]) {
				status[i] = false;
				j++;
				if (j == cs.length)
					break;
			}
		}
	}

	public void out(Card[] cs) {
		int j = 0;
		for (int i = 0; i < cards.length; i++) {
			if (cards[i] == cs[j]) {
				status[i] = true;
				j++;
				if (j == cs.length)
					break;
			}
		}
	}

	public void reset() {
		for (int i = 0; i < status.length; i++) {
			status[i] = false;
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < status.length; i++) {
			if (!status[i]) {
				sb.append(cards[i]).append(" ");
			}
		}
		return sb.toString();
	}

	public Hand getHand(Hand selfh, Hand comph) {
		return comph == null ? getFirstHand(selfh) : getBiggerHand(selfh, comph);
	}

	private Hand getFirstHand(Hand h) {
		if (h == null) {
			return getHandA(null, true);
		}
		Hand h2 = getNextHand(h);
		int size = h.getLen();
		int left = this.getLeft();
		while (h2 == null && size < left) {
			size++;
			h2 = getFirstHand(size);

		}
		return h2;
	}

	private Hand getBiggerHand(Hand h, Hand comph) {
		Hand n = h == null ? comph : h;
		switch (comph.getLen()) {
		case 1:
			return getHandA(n, h != null);
		case 2:
			return getHandAA(n, h != null);
		case 3:
			return getHandAAA(n, h != null);
		case 4:
			return null;
		case 5:
			return getHand5(n, h != null);
		default:
			return null;
		}
	}

	private Hand getNextHand(Hand h) {
		if (h == null) {
			return getHandA(null, true);
		} else {
			switch (h.getLen()) {
			case 1:
				return getHandA(h, true);
			case 2:
				return getHandAA(h, true);
			case 3:
				return getHandAAA(h, true);
			case 4:
				return null;
			case 5:
				return getHand5(h, true);
			default:
				return null;
			}
		}
	}

	public Hand getFirstHand(int size) {
		switch (size) {
		case 1:
			return getHandA(null, true);
		case 2:
			return getHandAA(null, true);
		case 3:
			return getHandAAA(null, true);
		case 4:
			return null;
		case 5:
			return getHand5(null, true);
		default:
			return null;
		}
	}

	public Hand getHandA(Hand h, boolean self) {
		for (int i = 0; i < status.length; i++) {
			if (!status[i]) {
				if (h == null) {
					return new Hand(cards[i] );
				} else if (self) {
					if (cards[i] != h.get(0) && cards[i].compareTo(h.get(0)) > 0) {
						return new Hand(cards[i] );
					}
				} else {
					if (cards[i].getNum() > h.get(0).getNum()) {
						return new Hand(cards[i] );
					}
				}
			}
		}
		return null;
	}

	public Hand getHandAA(Hand h, boolean self) {
		for (int i = 0; i < status.length - 1; i++) {
			if (!status[i]) {
				for (int j = i + 1; j < status.length && j < i+4; j++) {
					if (!status[j] && cards[j].getNum() == cards[i].getNum()) {
						if (h == null) {
							return new Hand(cards[i], cards[j] );
						} else if (self) {
							if (cards[i] == h.get(0) && cards[j] == h.get(1)) {
								continue;
							} else if (cards[i].compareTo(h.get(0)) >= 0 && cards[j].compareTo(h.get(1)) >= 0) {
								return new Hand(cards[i], cards[j] );
							}
						} else {
							if (cards[i].getNum() > h.get(0).getNum()) {
								return new Hand( cards[i], cards[j] );
							}
						}
					}
				}
			}
		}
		return null;
	}

	public Hand getHandAAA(Hand h, boolean self) {
		for (int i = 0; i < status.length - 2; i++) {
			if (!status[i]) {
				for (int j = i + 1; j < status.length - 1 && j < i+4; j++) {
					if (!status[j] && cards[j].getNum() == cards[i].getNum()) {
						for (int k = j + 1; k < status.length && k < j+4; k++) {
							if (!status[k] && cards[k].getNum() == cards[i].getNum()) {
								if (h == null) {
									return new Hand(new Card[] { cards[i], cards[j], cards[k] });
								} else if (self) {
									if (cards[i] == h.get(0) && cards[j] == h.get(1) && cards[k] == h.get(2)) {
										continue;
									} else if (cards[i].compareTo(h.get(0)) >= 0 && cards[j].compareTo(h.get(1)) >= 0
											&& cards[k].compareTo(h.get(2)) >= 0) {
										return new Hand(new Card[] { cards[i], cards[j], cards[k] });
									}
								} else {
									if (cards[i].getNum() > h.get(0).getNum()) {
										return new Hand(new Card[] { cards[i], cards[j], cards[k] });
									}
								}
							}
						}
					}
				}
			}
		}
		return null;
	}

	public Hand getHand5(Hand h, boolean self) {
		for (int i = 0; i < status.length - 4; i++) {
			if (!status[i]) {
				for (int j = i + 1; j < status.length - 3; j++) {
					if (!status[j]) {
						for (int k = j + 1; k < status.length - 2; k++) {
							if (!status[k]) {
								for (int l = k + 1; l < status.length - 1; l++) {
									if (!status[l]) {
										for (int m = l + 1; m < status.length; m++) {
											if (!status[m]) {
												if (Hand.isValid(cards[i], cards[j], cards[k], cards[l], cards[m])) {
													if (h == null) {
														return new Hand(cards[i], cards[j], cards[k],
																cards[l], cards[m] );
													} else if (self) {
														if (cards[i] == h.get(0) && cards[j] == h.get(1)
																&& cards[k] == h.get(2) && cards[l] == h.get(3)
																&& cards[m] == h.get(4)) {
															continue;
														}
														if (Hand.isAAABB(h.getCards())) {
															if (Hand.isABCDE(cards[i], cards[j], cards[k], cards[l],
																	cards[m])
																	|| Hand.isSSSSS(cards[i], cards[j], cards[k],
																			cards[l], cards[m]))
																return new Hand( cards[i], cards[j],
																		cards[k], cards[l], cards[m] );
															else if (cards[i].compareTo(h.get(0)) >= 0
																	&& cards[j].compareTo(h.get(1)) >= 0
																	&& cards[k].compareTo(h.get(2)) >= 0
																	&& cards[l].compareTo(h.get(3)) >= 0
																	&& cards[m].compareTo(h.get(4)) >= 0) {
																return new Hand( cards[i], cards[j],
																		cards[k], cards[l], cards[m] );
															}
														} else if (Hand.isABCDE(h.getCards())) {
															if (Hand.isAAABB(cards[i], cards[j], cards[k], cards[l],
																	cards[m]))
																continue;
															if (Hand.isSSSSS(cards[i], cards[j], cards[k], cards[l],
																	cards[m]))
																return new Hand( cards[i], cards[j],
																		cards[k], cards[l], cards[m] );
															else if (cards[i].compareTo(h.get(0)) >= 0
																	&& cards[j].compareTo(h.get(1)) >= 0
																	&& cards[k].compareTo(h.get(2)) >= 0
																	&& cards[l].compareTo(h.get(3)) >= 0
																	&& cards[m].compareTo(h.get(4)) >= 0) {
																return new Hand( cards[i], cards[j],
																		cards[k], cards[l], cards[m] );
															}
														} else if (Hand.isSSSSS(h.getCards())) {
															if (Hand.isAAABB(cards[i], cards[j], cards[k], cards[l],
																	cards[m])
																	|| Hand.isABCDE(cards[i], cards[j], cards[k],
																			cards[l], cards[m]))
																continue;
															else if (cards[i].compareTo(h.get(0)) >= 0
																	&& cards[j].compareTo(h.get(1)) >= 0
																	&& cards[k].compareTo(h.get(2)) >= 0
																	&& cards[l].compareTo(h.get(3)) >= 0
																	&& cards[m].compareTo(h.get(4)) >= 0) {
																return new Hand( cards[i], cards[j],
																		cards[k], cards[l], cards[m] );
															}
														}
													} else {
														if (Hand.isAAABB(h.getCards())) {
															if (Hand.isABCDE(cards[i], cards[j], cards[k], cards[l],
																	cards[m])
																	|| Hand.isSSSSS(cards[i], cards[j], cards[k],
																			cards[l], cards[m]))
																continue;
															else if (cards[k].compareTo(h.get(2)) > 0) {
																return new Hand( cards[i], cards[j],
																		cards[k], cards[l], cards[m] );
															}
														} else if (Hand.isABCDE(h.getCards())) {
															if (Hand.isAAABB(cards[i], cards[j], cards[k], cards[l],
																	cards[m])
																	|| Hand.isSSSSS(cards[i], cards[j], cards[k],
																			cards[l], cards[m]))
																continue;
															else if (cards[i].compareTo(h.get(0)) > 0) {
																return new Hand( cards[i], cards[j],
																		cards[k], cards[l], cards[m] );
															}
														} else if (Hand.isSSSSS(h.getCards())) {
															if (Hand.isAAABB(cards[i], cards[j], cards[k], cards[l],
																	cards[m])
																	|| Hand.isABCDE(cards[i], cards[j], cards[k],
																			cards[l], cards[m]))
																continue;
															else if (cards[i].getSuit() == h.get(0).getSuit()
																	&& cards[i].compareTo(h.get(0)) > 0
																	&& cards[j].compareTo(h.get(1)) > 0
																	&& cards[k].compareTo(h.get(2)) > 0
																	&& cards[l].compareTo(h.get(3)) > 0
																	&& cards[m].compareTo(h.get(4)) > 0) {
																return new Hand( cards[i], cards[j],
																		cards[k], cards[l], cards[m] );
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return null;
	}
}
