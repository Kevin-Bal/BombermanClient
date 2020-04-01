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
			ClientReceveur receveur = new ClientReceveur("Test", connection);
			ClientEmetteur emetteur = new ClientEmetteur("Test", connection);
			new Thread(receveur).start();
			new Thread(emetteur).start();
		}catch(Exception e) {System.out.println("il manque un argument à GenClient");System.out.println("java GenClient Username");	}
		
		//Menu menu = new Menu();
	}
}
