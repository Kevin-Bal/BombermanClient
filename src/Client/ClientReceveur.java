package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

import Agent.Agent;
import Item.InfoBomb;
import Item.InfoItem;
import bean.ServerObject;


//ECOUTE

public class ClientReceveur implements Runnable{
	private String username;
	private Socket connection;
	protected ArrayList<InfoBomb> listInfoBombs;
	protected ArrayList<InfoItem> listInfoItems;
	protected ArrayList<Agent> listInfoAgents;
	
	public ClientReceveur(String string, Socket connection) {
		this.username = string;
		this.connection = connection;
	}

	@Override
	public void run() {
		listInfoAgents = new ArrayList<Agent>();
		listInfoBombs = new ArrayList<InfoBomb>();
		listInfoItems = new ArrayList<InfoItem>();
		
		try {
			//Connexion au serveur
			ObjectInputStream tamponLecture = new ObjectInputStream(connection.getInputStream());
			System.out.println("Client lancé");
			
			ServerObject message_lu = new ServerObject();
			while(!connection.isClosed()) {
				message_lu = (ServerObject) tamponLecture.readObject();

				//Recuperation BREAKABLE WALLS
				System.out.println(message_lu.getBreakable_walls()[5][5]);	

				//Recuperation INFO ITEM
				if(message_lu.getListInfoItems()!=null) {
					listInfoItems = message_lu.getListInfoItems();
					System.out.println(listInfoItems.get(0).getX());
				}

				//Recuperation INFO BOMB
				listInfoBombs.removeAll(listInfoBombs);
				if(message_lu.getListInfoBombs()!=null) {
					for(String s :  message_lu.getListInfoBombs()) {
						InfoBomb ib = new InfoBomb(message_lu.getListInfoBombs().get(0));
						listInfoBombs.add(ib);
						System.out.println(listInfoBombs.get(0).getStateBomb().toString());
					}
				}
					
			}
			
			connection.close();
			
		} catch (IOException | ClassNotFoundException e) {e.printStackTrace();}
	}

}
