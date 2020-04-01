package Agent;

public class EnemyFactory implements AgentFactory {
	private Agent agent;
	
	@Override
	public Agent createAgent(int x, int y, char type, AgentAction agentAction, ColorAgent col) {
		switch(type) {
			case 'E' : 
				agent = new Enemy(x, y, agentAction);
				break;
			case 'V' :
				agent = new Bird(x, y, agentAction);
				break;
			case 'R' :
				agent = new Rajion(x, y, agentAction);
				break;
			default :
				break;
		}
		
		return agent;
	}

}
