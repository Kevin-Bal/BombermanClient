package View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

import Agent.Agent;
import Agent.Bomberman;
import Controler.Map;
import Model.BombermanGame;
import Model.GameMode;

public class ViewGame extends JFrame implements Observer{
	private PanelBomberman jeu_bomberman;
	private PanelCommande jeu_commande;
	private JButton back_to_menu;
	private BombermanGame game;
	
	public ViewGame(String map, BombermanGame game, PanelCommande vc) throws Exception {
		super();
		
		this.game = game;
		this.game.addObserver(this);
		
		//Interface pour choisir la map
		this.game.setMap(new Map(map));

		back_to_menu = new JButton("Retour Menu");
		
		this.jeu_bomberman = new PanelBomberman(this.game.getMap());;
		this.jeu_commande = vc;

		creerBouton(this);
		
		this.setTitle("Game");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(new Dimension(10, 5));
		this.setSize(new Dimension(this.game.getMap().getSizeX()*50, this.game.getMap().getSizeY()*40+250));
		Dimension windowSize = jeu_bomberman.getSize();
		this.setLocationRelativeTo(null);
		this.setLayout(new BorderLayout());
        this.add("North",jeu_commande.getjPanelView());
        this.add("Center",jeu_bomberman);
		this.setVisible(true);

	}

	public void creerBouton(ViewGame view){
		//bouton retour menu
		back_to_menu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evenement) {
				System.out.println("Retour Menu");
				game.stop();
				Menu menu = new Menu();
				view.dispose();
			}
		});
		jeu_commande.getjPanelButtons().add(back_to_menu);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		jeu_bomberman.setInfoGame(game.etatJeu.getBrokable_walls(), game.etatJeu.getAgents(), game.etatJeu.getItems(), game.etatJeu.getBombs());
		this.jeu_bomberman.repaint();
		checkEndGame();
	}
	
	
	/*
	 * Affiche un popup avec les scores des bombermans quand la partie se termine
	 */
	public void checkEndGame(){
		if(game.isEndgame()) {
			orderByScore(game.getEtatJeu().getBombermans());
			orderByScore(game.getEtatJeu().getDeadBombermans());
			
			//GAME MODE = PVE
			if(game.getGameMode()==GameMode.PVE) {	
				ArrayList<String> text = new ArrayList<String>();
				String title ="";
				if(game.getEtatJeu().getBombermans().size()<1)
					title += "Perdu ! \n";
				else
					title += "Gagné ! \n";
				
				for(Agent b : game.getEtatJeu().getBombermans()) {
					Bomberman temp = (Bomberman) b;
					text.add("Le Bomberman a fait un score de "+temp.score+"\n"); 	
				}
				for(Agent b : game.getEtatJeu().getDeadBombermans()) {
					Bomberman temp = (Bomberman) b;
					text.add("Le Bomberman a fait un score de "+temp.score+"\n");	
				}
				createPopupForPVE(title,text);
			}
			
			//GAME MODE = PVP
			if(game.getGameMode()==GameMode.PVP) {	
				ArrayList<String> text = new ArrayList<String>();
				String title ="";
				ArrayList<String> text2 = new ArrayList<String>();
				String title2 ="";
				title = "Gagnant(s) : \n";
				for(Agent b : game.getEtatJeu().getBombermans()) {
					Bomberman temp = (Bomberman) b;
					text.add("Le Bomberman "+ temp.getColor().toString().toLowerCase()  +" a fait un score de "+temp.score+"\n");
				}
				title2 = "Perdant(s) : \n";
				for(Agent b : game.getEtatJeu().getDeadBombermans()) {
					Bomberman temp = (Bomberman) b;
					text2.add("Le Bomberman "+ temp.getColor().toString().toLowerCase() +" a fait un score de "+temp.score+"\n");
				}
				
				createPopupForPVP(title,text,title2,text2);
			}
			
			
		}
	}
	
	
	/*
	 * Creates a popup with a title and a text FOR PVE
	 */
	public void createPopupForPVE(String title,ArrayList<String> text) {
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(text.size()+1,1));
		
		//Add title
		JLabel label = new JLabel();
		label.setText("<html><h2>"+title+"</h2></html>");
		panel.add(label);
		
		//Add texte
		for(String t : text) {
			JLabel labelText = new JLabel();
			labelText.setText(t);
			panel.add(labelText);
		}

		frame.add(panel);
	    frame.setSize(200, 40*text.size());
	    frame.setLocationRelativeTo(null);
	    frame.setVisible(true);
	}
	
	/*
	 * Creates a popup with a title and a text FOR PVP
	 */
	public void createPopupForPVP(String title,ArrayList<String> text,String title2,ArrayList<String> text2) {
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(text.size()+1+text2.size()+1,1));
		
		//Add title
		JLabel label = new JLabel();
		label.setText("<html><h2>"+title+"</h2></html>");
		panel.add(label);
		
		//Add texte
		for(String t : text) {
			JLabel labelText = new JLabel();
			String color = ColorOfBomberman(t);
			labelText.setText("<html><p style=\"color:"+color+";\">"+t+"</p></html>");
			panel.add(labelText);
		}
		
		//Add title
		JLabel label2 = new JLabel();
		label2.setText("<html><h2>"+title2+"</h2></html>");
		panel.add(label2);
		
		//Add texte
		for(String t : text2) {
			JLabel labelText2 = new JLabel();
			String color = ColorOfBomberman(t);
			labelText2.setText("<html><p style=\"color:"+color+";\">"+t+"</p></html>");
			panel.add(labelText2);
		}


		frame.add(panel);
		frame.setSize(300, 100*text.size()+1+text2.size()+1);
	    frame.setLocationRelativeTo(null);
	    frame.setVisible(true);
	}
	
	
	/*
	 * To order a tab of bombermans by score
	 */
	public void orderByScore(ArrayList<Agent> bombermans) {
		for(int i=bombermans.size(); i>1; --i) {
			for(int j=1; j<i; ++j) {
				Bomberman temp = (Bomberman) bombermans.get(j);
				Bomberman temp2 = (Bomberman) bombermans.get(j-1);
				if(temp.score > temp2.score) {
					bombermans.set(j, temp2);
					bombermans.set(j-1, temp);
				}			
			}
		}
	}
	
	/*
	 * Get the color of the bomberman from the text "Le Bomberman bleu a fait un score de "
	 */
	public String ColorOfBomberman(String t) {
		String color="";
		
		String[] splitText = t.split(" ");
		
		switch(splitText[2]){
			case "bleu" :
				color = "blue";
			break;
			case "rouge" :
				color = "red";
			break;
			case "vert" :
				color = "green";
			break;
			case "jaune" :
				color = "yellow";
			break;
			case "blanc" :
				color = "white";
			break;
			default :
				color = "black";
		}
		
		return color;
	}
}
