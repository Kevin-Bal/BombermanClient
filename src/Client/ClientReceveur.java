package Client;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import View.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import Agent.Agent;
import Item.InfoBomb;
import Item.InfoItem;
import bean.ServerObject;


//ECOUTE

public class ClientReceveur implements Runnable{
    //choix du stage
    private JComboBox<File> liste_lay;
    private String content = null;
    private Map map = null;
    
    //choix startegies
    private String[] names_strategies_joueur_1 = {"Bomberman IA 1","Bomberman Aléatoire","Bomberman Interactif"};
    private String[] names_strategies = {"Bomberman IA 1","Bomberman Aléatoire"};
    private ArrayList<JComboBox<String>> j_strategies = new ArrayList<>();
    private int nombre_bbm = 0;	
    
    //Différent panel du menu
	private JPanel top = null;
    private PanelBomberman viewMap = null;
    private PanelCommande viewCommande = null;
    private JPanel choix_strategie = null;
    private JFrame vue = null;
    
    
	//Connexion
	private Socket connection;
	protected ArrayList<InfoBomb> listInfoBombs;
	protected ArrayList<InfoItem> listInfoItems;
	protected ArrayList<Agent> listInfoAgents;
	private BufferedReader LectureString = null;
	ClientEmetteur emetteur;
	
	public ClientReceveur(Socket connection, ClientEmetteur em) {
		this.connection = connection;
		this.emetteur = em;
	}

	@Override
	public void run() {		
		try {
			System.out.println("Client lancé");
			//Connexion au serveur
			LectureString = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			Connexion co = new Connexion(emetteur);

			//Récuperation du message serveur
			String  message_lu = new String();	
			while(!message_lu.equals("[Serveur]: Bienvenue")) {
				message_lu = LectureString.readLine();
				System.out.println(message_lu);
				co.changeEtat(message_lu);
			}
			co.dispose();

			//Init
	        top = new JPanel();
	        top.setLayout(new GridLayout(2,1));
	        vue = new JFrame("Menu");
	        vue.setTitle("Menu Jeu Bomberman");
	        vue.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        vue.setLayout(new BorderLayout());
	        
	        
	        //Construction du panneau du choix de niveau
	        top = new JPanel();
	        top.setLayout(new GridLayout(2,1));
	        
	        File repertoire = new File("./layouts/");
	        File[] files=repertoire.listFiles();


	        liste_lay = new JComboBox<File>(files);
	        JButton suivant = new JButton("Suivant");
	        content = liste_lay.getSelectedItem().toString();
	        
	        
	        top.add(liste_lay);
	        top.add(suivant);
	        vue.add("North",top);

	        vue.setSize(500, 200);
	        vue.revalidate();
	        vue.setLocationRelativeTo(null);
	        vue.setVisible(true);


	        suivant.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent evenement) {    					            	
	            	//Envoi de la map
	                content = liste_lay.getSelectedItem().toString();
	    	        try {
		    	        map = new Map(content);
		    	        viewMap = new PanelBomberman(map);
	    				viewCommande = new PanelCommande(emetteur);
	    				emetteur.getSortie().format("%s\n",content);
	    			} catch (Exception e2) {e2.printStackTrace();}	

	            	vue.remove(top);
	            }
	        });

			
	        
	      //Construction du panneau du choix des strategies
			try {
				nombre_bbm = Integer.parseInt(LectureString.readLine());
			} catch (NumberFormatException | IOException e1) {e1.printStackTrace();}
			
	        choix_strategie = new JPanel();
	        choix_strategie.setLayout(new GridLayout( nombre_bbm, 2));
	        for(int i = 0; i < nombre_bbm; i++) {
	        	JComboBox<String> liste_strat = null;
	        	if(i==0) {
		            liste_strat = new JComboBox<String>(names_strategies_joueur_1);
	        	}
	        	else
	        		liste_strat = new JComboBox<String>(names_strategies);
	            j_strategies.add(liste_strat);
	            choix_strategie.add(new JLabel("Joueur " + i+1));
	            choix_strategie.add(liste_strat);
	        }

	        JPanel boutton = new JPanel();
	        JButton jouer = new JButton("Jouer");
	        boutton.add(jouer);
	        vue.add("Center",choix_strategie);     
	        vue.add("South",boutton);    
	        
	        vue.setSize(500, 200);
	        vue.revalidate();
	        vue.setLocationRelativeTo(null);
	        vue.setVisible(true);
	        
	        jouer.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					vue.remove(choix_strategie);
					
	    	        for(int i = 0; i<j_strategies.size();i++) {
	    	        	emetteur.getSortie().format("%s\n",j_strategies.get(i).getSelectedItem().toString());
	                }

	    	        vue.remove(choix_strategie);
	    	        
	    	        //Démarrage du jeu
	    	        vue.add("Center",viewMap);
	    	        vue.add("North",viewCommande.getjPanelView());
	    	        vue.setSize(map.getSizeX()*50, map.getSizeY()*40+nombre_bbm*25+110);
				}
	        	
	        });
	        
	        
	        
	        //RECUPERATION INFO JEU
	        String infoServer = "";
            while(!connection.isClosed()) {
                listInfoAgents = new ArrayList<Agent>();
                listInfoBombs = new ArrayList<InfoBomb>();
                listInfoItems = new ArrayList<InfoItem>();
                Gson gson=new Gson();

                infoServer = LectureString.readLine();
                JsonObject userJson = new JsonParser().parse(infoServer).getAsJsonObject();


                ServerObject objet_lu = gson.fromJson(userJson,ServerObject.class);
				listInfoAgents = new ArrayList<Agent>();
				listInfoBombs = new ArrayList<InfoBomb>();
				listInfoItems = new ArrayList<InfoItem>();
				boolean[][] breakable_walls = new boolean[1000][1000];
						
				
				//Recuperation BREAKABLE WALLS
				breakable_walls= objet_lu.getBreakable_walls();

				//Recuperation INFO ITEM
				if(objet_lu.getListInfoItems()!=null) {
					if(objet_lu.getListInfoItems().size()!=0) {
						listInfoItems = objet_lu.getListInfoItems();
					}
				}

				//Recuperation INFO BOMB
				listInfoBombs.removeAll(listInfoBombs);
				if(objet_lu.getListInfoBombs()!=null) {
					for(String s :  objet_lu.getListInfoBombs()) {
						InfoBomb ib = new InfoBomb(s);
						listInfoBombs.add(ib);
					}
				}
				
				//Recuperation INFO AGENT
				listInfoAgents.removeAll(listInfoAgents);
				if(objet_lu.getListInfoAgents()!=null) {
					for(String s :  objet_lu.getListInfoAgents()) {						
						Agent ag = new Agent(s);
						listInfoAgents.add(ag);
					}
				}
				
				
				
				viewMap.setInfoGame(breakable_walls,listInfoAgents,listInfoItems,listInfoBombs);
				viewMap.repaint();
				

			}
			
			connection.close();
			
		} catch (IOException e) {e.printStackTrace();}
	}
	
	
	
	

	
	


}
