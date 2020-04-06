package Agent;

import Item.StateBomb;

public class Agent {
	private int x;
	private int y;
	private AgentAction agentAction;
	private ColorAgent color;
	private char type;
			
	private boolean isInvincible;
	private boolean isSick;
	private boolean isDead;
	//public int score;
	
	
	private int id;
	static int iter_id = 0;
	
	public Agent(int x, int y, AgentAction agentAction, char type, ColorAgent color, boolean isInvincible, boolean isSick, int score) {
		this.x=x;
		this.y=y;
		this.agentAction = agentAction;
		this.color = color;
		this.type = type;
		
		this.isInvincible = isInvincible;
		this.isSick = isSick;
		this.isDead=false;
		//this.score = score;
		
		this.id = iter_id;
		iter_id++;
	}

	
	public Agent(String parse) {
		String[] tab = parse.split(" ");
		this.x = Integer.parseInt(tab[0]);
		this.y = Integer.parseInt(tab[1]);		
		this.agentAction = ParseToAgentAction(tab[2]);
		this.color = ParseToColorAgent(tab[3]);
		this.type = tab[4].charAt(0);
		this.isInvincible = Boolean.parseBoolean(tab[5]);
		this.isSick = Boolean.parseBoolean(tab[6]);
		this.isDead = Boolean.parseBoolean(tab[7]);
	}


	public int getX() { return x;}
	public void setX(int x) {this.x = x;}

	public int getY() {return y;}
	public void setY(int y) {this.y = y;}
	
	public ColorAgent getColor() {return color;}
	public void setColor(ColorAgent color) {this.color = color;}
	
	
	public char getType() {return type;}
	public void setType(char type) {this.type = type;}


	public boolean isInvincible() {return isInvincible;}
	public void setInvincible(boolean isInvincible) {this.isInvincible = isInvincible;}


	public boolean isSick() {return isSick;}
	public void setSick(boolean isSick) {this.isSick = isSick;}


	public AgentAction getAgentAction() {return agentAction;}
	public void setAgentAction(AgentAction agentAction) {this.agentAction = agentAction;}


	public int getId() {return id;}
	public void setId(int id) {this.id = id;}

	public boolean isDead() {return isDead;}
	public void setDead(boolean isDead) {this.isDead = isDead;}


	//public int getScore() {return score;}
	//public void setScore(int score) {this.score = score;}
	
	
	//###############################################################################################
	//				PARSE
	public AgentAction ParseToAgentAction(String s) {
		switch(s) {
			case "MOVE_UP" :
				return AgentAction.MOVE_UP;
			case "MOVE_DOWN" :
				return AgentAction.MOVE_DOWN;
			case "MOVE_LEFT" :
				return AgentAction.MOVE_LEFT;
			case "MOVE_RIGHT" : 
				return AgentAction.MOVE_RIGHT;
			case "STOP" :
				return AgentAction.STOP;
			case "PUT_BOMB" : 
				return AgentAction.PUT_BOMB;
			default :
				return null;
		}
	}
	
	private ColorAgent ParseToColorAgent(String s) {
		switch(s) {
			case "BLANC" :
				return ColorAgent.BLANC;
			case "BLEU" :
				return ColorAgent.BLEU;
			case "JAUNE" :
				return ColorAgent.JAUNE;
			case "ROUGE" : 
				return ColorAgent.ROUGE;
			case "VERT" :
				return ColorAgent.VERT;
			case "DEFAULT" : 
				return ColorAgent.DEFAULT;
			default :
				return null;
		}
	}
	//###############################################################################################
}
	




