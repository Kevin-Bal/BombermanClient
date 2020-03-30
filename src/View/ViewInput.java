package View;

import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Agent.AgentAction;

public class ViewInput extends JFrame implements KeyListener{
	
	
	private AgentAction currentAction;

	public ViewInput() {
		this.currentAction = AgentAction.STOP;
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(8,1));

		
		ImageIcon icone = new ImageIcon("image/zqsd2.png");
		JLabel image = new JLabel(icone);
		panel.add(image);
		
		
		panel.add(new JLabel("<html><p> Z -> pour vous déplacer vers le haut</p></html>"));
		panel.add(new JLabel("<html><p> S -> pour vous déplacer vers le bas</p></html>"));
		panel.add(new JLabel("<html><p> Q -> pour vous déplacer vers la gauche</p></html>"));
		panel.add(new JLabel("<html><p> D -> pour vous déplacer vers la droite</p></html>"));
		panel.add(new JLabel("<html><p> F -> pour poser une bombe</p></html>"));
		panel.add(new JLabel(""));

		panel.add(new JLabel("<html><p style=\"color:gray;\">Cliquer sur cette fenêtre pour revenir en mode intéractif </p></html>"));
		
		frame.add(panel);
	    frame.setSize(200, 400);
	    frame.addKeyListener(this);
	    frame.setLocation(1600, 300);
	    frame.setVisible(true);
	    frame.requestFocus();
	}

	
	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println(e.getKeyChar());
		switch(e.getKeyChar()) {
		case 'd':
			currentAction = AgentAction.MOVE_RIGHT;
			break;
		case 'q':
			currentAction = AgentAction.MOVE_LEFT;
			break;
		case 's':
			currentAction = AgentAction.MOVE_DOWN;
			break;
		case 'z':
			currentAction = AgentAction.MOVE_UP;
			break;
		case 'f':
			currentAction = AgentAction.PUT_BOMB;
			break;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		currentAction = AgentAction.STOP;		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	//##############################################################
	//		GETTER
	public AgentAction getCurrentAction() {
		return currentAction;
	}	
	//##############################################################
}
