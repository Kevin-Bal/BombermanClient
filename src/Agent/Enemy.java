package Agent;


public class Enemy extends Agent {

	public Enemy(int x, int y, AgentAction agentAction) {
		super(x, y, agentAction, 'E', ColorAgent.DEFAULT, false, false);
	}

}
