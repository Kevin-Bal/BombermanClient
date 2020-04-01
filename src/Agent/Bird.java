package Agent;


public class Bird extends Agent {

	private boolean endormie = true;

	public Bird(int x, int y, AgentAction agentAction) {

		super(x, y, agentAction, 'V', ColorAgent.DEFAULT, false, false);
	}

	public boolean isEndormie() {
		return endormie;
	}

	public void setEndormie(boolean endormie) {
		this.endormie = endormie;
	}
}
