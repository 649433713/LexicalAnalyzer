package RE2DFA;
import java.util.LinkedList;
/**
 * @author yinywf
 * Created on 2017/10/30
 */
public class DFA {
	private LinkedList<State> dfa;
	
	public DFA () {
		this.setDfa(new LinkedList<State> ());
		this.getDfa().clear();
	}

	public LinkedList<State> getDfa() {
		return dfa;
	}

	public void setDfa(LinkedList<State> nfa) {
		this.dfa = nfa;
	}
}