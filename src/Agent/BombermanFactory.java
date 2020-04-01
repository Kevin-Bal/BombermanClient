package Agent;

public class BombermanFactory implements AgentFactory {

	@Override
	public Agent createAgent(int x, int y, char type, AgentAction agentAction, ColorAgent col) {
    	Agent agent = new Bomberman(x, y,agentAction, col);
    	return agent;
	}

}
