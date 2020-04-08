package Client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

//ENVOI

public class ClientEmetteur implements Runnable {
	private Socket connection;
	
	private PrintWriter sortie = null;
	private Scanner sc = new Scanner(System.in);
	
	
	public ClientEmetteur(Socket connection) {
		this.connection = connection;
	}

	@Override
	public void run() {		
		try {
			//Connexion au serveur
			sortie = new PrintWriter(connection.getOutputStream(), true);
			
			//Envoie de messages tant que l'utilisateur n'a pas écrit "exit"
			String message = "";
			do {
				message = sc.nextLine();		
				sortie.format("%s\n",message);
			}while(!message.equals("exit"));
		} catch (IOException e) {}
		
		
		//Fermeture de la connexion
		try {
			connection.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	
	public PrintWriter getSortie() {
		return sortie;
	}

	public void setSortie(PrintWriter sortie) {
		this.sortie = sortie;
	}
	
	

}
