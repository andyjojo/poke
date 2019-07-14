package org.onezero;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

public class ForcePlay {

	private static final int PRINT_DEPTH = 250;
	private static final OutputStream OS;
	private static final byte[] LINE_END="\n".getBytes();
	
	static {
		File f = new File("result.txt");
		OutputStream os = null;
		try {
			os = new FileOutputStream(f);
		} catch (FileNotFoundException e) {
		}
		OS = os;
	}

	public static void main(String[] args) throws Exception {
		Player p1 = new Player("A", new String[] { "♣3", "♦4", "♣4", "♦5", "♠5", "♠6", "♥6", "♥7", "♦7", "♥9", "♣9",
				"♦Q", "♣Q", "♥Q", "♣K", "♦K", "♠K", "♣2" });
		Player p2 = new Player("B", new String[] { "♥A", "♥A", "♥J", "♥J" });

		StringBuilder sb = new StringBuilder();
		battle(p1, p2, sb);
	}

	private static boolean battle(Player p1, Player p2, StringBuilder sb) {
		//Hand hand1 = p1.getHand(null, null);
		Hand hand1 = new Hand(p1.getCard("♥6"));
		while (hand1 != null) {
			// 玩家A打红桃6
			p1.out(hand1.getCards());
			sb.append("|").append(p1.getName()).append(":");
			sb.append(hand1);
			if (battle(p2, p1, hand1, sb, 1)) {
				// 玩家A输了，换悔一手牌
				p1.back(hand1.getCards());
				sb.delete(sb.lastIndexOf("|"), sb.length());
				hand1 = p1.getHand(hand1, null);
			} else {
				// 玩家A赢了，返回true
				output(sb.toString());
				output("===============WIN===============");
				return true;
			}
		}
		output("No Way to Win");
		return false;
	}

	private static boolean battle(Player p1, Player p2, Hand hand2, StringBuilder sb, int depth) {
		// p1找出一手牌比hand2大
		Hand hand1 = p1.getHand(null, hand2);
		while (hand1 != null) {
			//找到了比hand2大的牌，出这一手牌 
			p1.out(hand1.getCards());
			sb.append("|").append(p1.getName()).append(":");
			sb.append(hand1);
			if (p1.getLeft() == 0) {
				// 玩家p1 出完了，赢了，返回true
				sb.append(" WIN");
				if (depth < PRINT_DEPTH && p1.getName().equals("A")) {
					output(sb.toString());
				}
				p1.back(hand1.getCards());
				sb.delete(sb.lastIndexOf("|"), sb.length());
				return true;
			}
			if (battle(p2, p1, hand1, sb, depth + 1)) {
				// 玩家p2赢了，p1就悔一手牌
				p1.back(hand1.getCards());
				sb.delete(sb.lastIndexOf("|"), sb.length());
				hand1 = p1.getHand(hand1, hand2);
			} else {
				// 玩家p1赢了，返回true
				p1.back(hand1.getCards());
				sb.delete(sb.lastIndexOf("|"), sb.length());
				return true;
			}
		}
		if (hand1 == null) {
			// 所有比hand2大的牌都试过还是输 或者没有比hand2大的牌
			if (hand2 == null) {
				// hand2是pass，但是玩家p1后面还是赢不了， 返回false
				sb.delete(sb.lastIndexOf("|"), sb.length());
				return false;
			}
			sb.append("|").append(p1.getName()).append(":PASS");
			// pass
			if (battle(p2, p1, null, sb, depth + 1)) {
				// 玩家p2赢了，p1就悔一手牌，因为是pass，所以赢不了了，返回false
				sb.delete(sb.lastIndexOf("|"), sb.length());
				return false;
			} else {
				// 玩家p1赢了，返回true
				return true;
			}
		}
		return true;
	}
	
	private static void output(String content) {
		System.out.println(content);
		try {
			OS.write(content.getBytes("UTF-8"));
			OS.write(LINE_END);
			OS.flush();
		} catch (UnsupportedEncodingException e) {
		} catch (IOException e) {
		}
	}

}
