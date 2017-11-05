package RE2DFA;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * @author yinywf
 * Created on 2017/10/30
 */
public class ExpressionReader {
	private static Scanner s;
	private static String regular;

	private static ArrayList<String> expressions = new ArrayList<String>();

	private static NFA nfa;

	private static DFA dfa;

	public static void main(String[] args) {
		try {
			s = new Scanner(new File("RE.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		regular = s.next();

		while (s.hasNext()) {
			expressions.add(s.next());
		}

		setNfa(RegularExpression.generateNFA(regular));

		setDfa(RegularExpression.generateDFA(getNfa()));

		Set<Character> header  =  getDfa().getDfa().get(0).getNextState().keySet();
		System.out.print("I"+"   ");
		for (Character character : header) {
			System.out.print(character+"   ");
		}
		System.out.println();

		for (int i = 0; i < getDfa().getDfa().size(); i++) {
			System.out.print(getDfa().getDfa().get(i).getStateID() + "   ");
			Map<Character, ArrayList<State>> map  =  getDfa().getDfa().get(i).getNextState();
			for (Character character: map.keySet()){
				System.out.print(map.get(character).get(0).getStateID()+"   ");
			}
			System.out.println();
		}

	}

	public static NFA getNfa() {
		return nfa;
	}

	public static void setNfa(NFA nfa) {
		ExpressionReader.nfa = nfa;
	}

	public static DFA getDfa() {
		return dfa;
	}

	public static void setDfa(DFA dfa) {
		ExpressionReader.dfa = dfa;
	}
}