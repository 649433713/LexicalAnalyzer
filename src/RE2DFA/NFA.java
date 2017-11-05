package RE2DFA;
import java.util.LinkedList;
/**
 * @author yinywf
 * Created on 2017/10/30
 */
public class NFA {
	private LinkedList<State> nfa;
	
	public NFA () {
		this.setNfa(new LinkedList<State> ());
		this.getNfa().clear();
	}

	public LinkedList<State> getNfa() {
		return nfa;
	}

	public void setNfa(LinkedList<State> nfa) {
		this.nfa = nfa;
	}
}