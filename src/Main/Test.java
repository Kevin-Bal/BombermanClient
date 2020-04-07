package Main;
import java.net.Socket;

import Client.ClientEmetteur;
import Client.ClientReceveur;

public class Test {
	private static int serveurPort = 12000;
	private static String serveurIp = "127.0.0.1";

	public static void main(String[] args) throws Exception {	
		try {
			Socket connection = new Socket(serveurIp, serveurPort);
			ClientEmetteur emetteur = new ClientEmetteur("Test", connection);
			new Thread(emetteur).start();
			ClientReceveur receveur = new ClientReceveur("Test", connection,emetteur);
			new Thread(receveur).start();
		}catch(Exception e) {System.out.println("il manque un argument à GenClient");System.out.println("java GenClient Username");	}
		
		//Menu menu = new Menu();
	}
}
