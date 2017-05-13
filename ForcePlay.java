
public class ForcePlay {

	private static final int PRINT_DEPTH = 4;

	public static void main(String[] args) throws Exception {
		Player p1 = new Player("A", new String[] { "♣3", "♦4", "♣4", "♦5", "♠5", "♠6", "♥6", "♥7", "♦7", "♥9", "♣9",
				"♦Q", "♣Q", "♥Q", "♣K", "♦K", "♠K", "♣2" });
		Player p2 = new Player("B", new String[] { "♥A", "♦A", "♥J", "♦J" });

		StringBuilder sb = new StringBuilder();
		battle(p1, p2, sb);
	}

	private static boolean battle(Player p1, Player p2, StringBuilder sb) {
		Hand hand2 = p1.getHand(null, null);
		while (hand2 != null) {
			p1.out(hand2.getCards());
			sb.append("|").append(p1.getName()).append(":");
			sb.append(hand2);
			if (battle(p2, p1, hand2, sb, 1)) {
				p1.back(hand2.getCards());
				sb.delete(sb.lastIndexOf("|"), sb.length());
				hand2 = p1.getHand(hand2, null);
			} else {
				System.out.println(sb.toString());
				System.out.println("===============WIN===============\r\n");
				return true;
			}
		}
		System.out.println("No Way to Win");
		return false;
	}

	private static boolean battle(Player p1, Player p2, Hand hand1, StringBuilder sb, int depth) {
		Hand hand2 = p1.getHand(null, hand1);
		while (hand2 != null) {
			p1.out(hand2.getCards());
			sb.append("|").append(p1.getName()).append(":");
			sb.append(hand2);
			if (p1.getLeft() == 0) {
				sb.append("WIN");
				p1.back(hand2.getCards());
				sb.delete(sb.lastIndexOf("|"), sb.length());
				return true;
			}
			if (battle(p2, p1, hand2, sb, depth + 1)) {
				p1.back(hand2.getCards());
				sb.delete(sb.lastIndexOf("|"), sb.length());
				hand2 = p1.getHand(hand2, hand1);
			} else {
				if (depth < PRINT_DEPTH && p1.getName().equals("A"))
					System.out.println(sb.toString());
				p1.back(hand2.getCards());
				sb.delete(sb.lastIndexOf("|"), sb.length());
				return true;
			}
		}
		if (hand2 == null) {
			if (hand1 == null) {
				sb.delete(sb.lastIndexOf("|"), sb.length());
				return false;
			}
			if (p1.getLeft() == 0) {
				sb.append("WIN");
				// System.out.println(sb);
				p1.back(hand1.getCards());
				sb.delete(sb.lastIndexOf("|"), sb.length());
				return true;
			}
			sb.append("|").append(p1.getName()).append(":PASS");
			// pass
			if (battle(p2, p1, null, sb, depth + 1)) {
				sb.delete(sb.lastIndexOf("|"), sb.length());
				return false;
			} else {
				if (depth < PRINT_DEPTH && p1.getName().equals("A"))
					System.out.println(sb.toString());
				return true;
			}
		}
		return true;
	}

}
