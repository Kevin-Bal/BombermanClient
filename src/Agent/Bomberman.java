package Agent;


import java.io.Serializable;
import java.util.ArrayList;



public class Bomberman extends Agent implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int range;
	private int numberOfBombs;
	public int score;
	
	//Variables Iterations
	int numberOfInvincibleTurns;
	int numberOfSickTurns;
	
	public Bomberman(int x, int y, AgentAction agentAction, ColorAgent color) {
		super(x, y, agentAction, 'B', color, false, false);
		this.setRange(1);
		numberOfBombs =1;
		numberOfInvincibleTurns=0;
		numberOfSickTurns=0;
		score=0;
	}
	


	//##########################################################
	//				COUNTDOWNS
	private void IterateInvincibleCountdown() {
		if(numberOfInvincibleTurns>4)
			setInvincible(false);
		else
			numberOfInvincibleTurns++;
	}

	private void IterateSickCountdown() {
		if(numberOfSickTurns>4)
			setSick(false);
		else
			numberOfSickTurns++;
	}
	//##########################################################
	//				GETTERS AND SETTERS
	public int getRange() {
		return range;
	}


	public void setRange(int range) {
		this.range = range;
	}

	public int getNumberOfBombs() {
		return numberOfBombs;
	}
	public void setNumberOfBombs(int numberOfBombs) {
		this.numberOfBombs = numberOfBombs;
	}

	//##########################################################
}
