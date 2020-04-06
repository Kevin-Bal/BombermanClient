package Item;

import java.io.Serializable;

import Agent.Bomberman;

public class InfoBomb implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int x;
	private int y;
	private int range;
		
	StateBomb stateBomb;

	public InfoBomb(int x, int y, int range, StateBomb stateBomb, Bomberman b) {
		this.x=x;
		this.y=y;
		this.range=range;
		this.stateBomb = stateBomb;
	}


	public InfoBomb(String parse) {
		String[] tab = parse.split(" ");
		this.x = Integer.parseInt(tab[0]);
		this.y = Integer.parseInt(tab[1]);
		this.range = Integer.parseInt(tab[2]);
		
		switch(tab[3]) {
		case "Step1" :
			this.stateBomb = StateBomb.Step1;
			break;
		case "Step2" :
			this.stateBomb = StateBomb.Step2;
			break;
		case "Step3" :
			this.stateBomb = StateBomb.Step3;
			break;
		case "Boom" :
			this.stateBomb = StateBomb.Boom;
			break;
		}
	}


	//#########################################################################
	//			GETTERS AND SETTERS
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public StateBomb getStateBomb() {
		return stateBomb;
	}

	public void setStateBomb(StateBomb stateBomb) {
		this.stateBomb = stateBomb;
	}

	public int getRange() {
		return range;
	}


	public void setRange(int range) {
		this.range = range;
	}
	//#########################################################################
	
}
	