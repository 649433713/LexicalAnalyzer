package RE2DFA;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author yinywf
 * Created on 2017/11/3
 **/
public class ExpressionReader {
	private static Scanner s;
	private static String regular;

	// Reads all the expressions in this arrayList
	private static ArrayList<String> expressions = new ArrayList<String>();

	// stores the NFA
	private static NFA nfa;

	// stores the DFA
	private static DFA dfa;

	public static void main(String[] args) {
		// Create a Scanner object
		try {
			s = new Scanner(new File("RE.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Read the regular expression
		regular = s.next();

		// Read all the expressions to apply the regular expression
		while (s.hasNext()) {
			expressions.add(s.next());
		}

		// Generate NFA using thompson algorithms with the Regular Expression
		setNfa(RegularExpression.generateNFA(regular));

		// Generate DFA using the previous NFA and the Subset Construction
		// Algorithm
		setDfa(RegularExpression.generateDFA(getNfa()));
		for (int i = 0; i < getDfa().getDfa().size(); i++) {
			System.out.println(getDfa().getDfa().get(i).getStateID() + "   "
					+ getDfa().getDfa().get(i).getNextState().get('a').get(0).getStateID());
		}

	}

	// Getters and Setters
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