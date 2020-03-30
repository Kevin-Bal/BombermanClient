package View;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Controler.ControleurBombermanGame;
import Model.BombermanGame;


public class PanelCommande implements Observer{

	
///////////----Attributs de la classe----///////////
	private ControleurBombermanGame ControleurBombermanGame;
	private JPanel jPanelView;


	public JButton restart;
	private JPanel jPanelButtons;
	private JPanel jPanelSlider;
	private JLabel jLabel;
	int tours;
	BombermanGame game;
////////////////////////////////////////////////////
	
	
	//Constructeur + Ouvre la fenetre JFrame
	public PanelCommande(BombermanGame game) throws Exception{
		tours = 0;
		this.game=game;
		this.game.addObserver(this);
		 
			
		this.ControleurBombermanGame = new ControleurBombermanGame(game);
		
		//Instanciations des JPanels/JLabels
		jPanelView = new JPanel();
		jPanelButtons = new JPanel();
		jPanelSlider = new JPanel();
		jLabel = new JLabel();
				
		
		//Ajout des boutons et slider
		jPanelView.setLayout(new GridLayout(2,1));
		createView();
		
		//Ajouter tous les JPanels à la Frame
		jPanelView.add(jPanelButtons);
		jPanelView.add(jPanelSlider);
	}
	
	//afficher le compteur de tours à chaque fois qu'il est notifié
	@Override
	public void update(Observable o, Object arg) {
		tours = game.getTurn(); 
		jLabel.setText("Tours : "+Integer.toString(tours));
		jLabel.setHorizontalAlignment(JLabel.CENTER);
	}
	
	
	//Ajoute les boutons et le slider à la vue
	public void createView(){
		
	//Boutons
		jPanelButtons.setLayout(new GridLayout(1,5));
		
		//Bouton Restart
		Icon icon_restart = new ImageIcon("Icones/icon_restart.png");
		JButton jb_restart= new JButton(icon_restart);
		restart = jb_restart;
		//Bouton Run
		Icon icon_run = new ImageIcon("Icones/icon_run.png");
		JButton jb_run= new JButton(icon_run);
		//Bouton Step		
		Icon icon_step = new ImageIcon("Icones/icon_step.png");
		JButton jb_step = new JButton(icon_step);
		//Bouton Pause
		Icon icon_pause = new ImageIcon("Icones/icon_pause.png");
		JButton jb_pause = new JButton(icon_pause);
		
		
	//Listeners
		//Bouton Restart
		jb_restart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evenement) {
				System.out.println("Restart Game");
				ControleurBombermanGame.restart();
				jb_restart.setEnabled(false);
				jb_pause.setEnabled(true);
				jb_run.setEnabled(false);
				jb_step.setEnabled(true);
			}
		});
		jPanelButtons.add(jb_restart);

		//Bouton Run
		jb_run.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evenement) {
				System.out.println("Start");
				ControleurBombermanGame.start();
				jb_pause.setEnabled(true);
				jb_restart.setEnabled(false);
				jb_run.setEnabled(false);
			}
		});
		jPanelButtons.add(jb_run);

		//Bouton Step
		jb_step.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evenement) {
				System.out.println("Step");
				ControleurBombermanGame.step();
			}
		});
		jPanelButtons.add(jb_step);	
		
		//Bouton Pause
		jb_pause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evenement) {
				System.out.println("Pause Game");
				ControleurBombermanGame.stop();
				jb_pause.setEnabled(false);
				jb_restart.setEnabled(true);
				jb_run.setEnabled(true);
			}
		});
		jPanelButtons.add(jb_pause);


		
		//Default button state
		jb_restart.setEnabled(true);
		jb_pause.setEnabled(false);
		jb_step.setEnabled(false);
		jb_run.setEnabled(false);
		
		//SliderPanelButtons.setLayout(new GridLayout(1,4));
		JPanel jPanelHor = new JPanel();
		
		jPanelSlider.setLayout(new GridLayout(1,2));
		jPanelHor.setLayout(new GridLayout(2,1));
		
		JLabel jl=new JLabel();
		jl.setText("Number of turns per seconds");
		jl.setHorizontalAlignment(JLabel.CENTER);
		jPanelHor.add(jl);
		
		JSlider js = new JSlider(1,10,2);
		// paint the ticks and tracks 
		js.setMajorTickSpacing(1);
		js.setMinorTickSpacing(1);
		js.setPaintTicks(true); 
		js.setPaintLabels(true);
		js.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent event) {
				int time = js.getValue();
				ControleurBombermanGame.setTime(time);
			}
		});
		
		jPanelHor.add(js);
		jPanelSlider.add(jPanelHor);
		
	//Turn
		jPanelSlider.add(jLabel);
	}
	
	public JPanel getjPanelView() {
		return jPanelView;
	}

	public void setjPanelView(JPanel jPanelView) {
		this.jPanelView = jPanelView;
	}

	///////////////////////GETTER SETTER///////////////////////////
	public JPanel getjPanelButtons() {
		return jPanelButtons;
	}

	public void setjPanelButtons(JPanel jPanelButtons) {
		this.jPanelButtons = jPanelButtons;
	}

}
