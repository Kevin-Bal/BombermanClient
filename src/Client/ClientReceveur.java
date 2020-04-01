package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import bean.ReceivedObject;


//ECOUTE

public class ClientReceveur implements Runnable{
	private String username;
	private Socket connection;
	
	public ClientReceveur(String string, Socket connection) {
		this.username = string;
		this.connection = connection;
	}

	@Override
	public void run() {
		try {
			//Connexion au serveur
			ObjectInputStream tamponLecture = new ObjectInputStream(connection.getInputStream());
			System.out.println("Client lancé");
			
			System.out.println(tamponLecture.toString());
			
			//Récuperation du message serveur
			ReceivedObject  message_lu = new ReceivedObject("Patrick","Balkany");	
			while(!connection.isClosed()) {
				message_lu = (ReceivedObject) tamponLecture.readObject();
				System.out.println(message_lu.getNom()+"  "+message_lu.getPrenom());
			}
			
			connection.close();
			
		} catch (IOException | ClassNotFoundException e) {e.printStackTrace();}
	}

}
