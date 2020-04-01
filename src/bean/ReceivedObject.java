package bean;

import java.io.Serializable;

public class ReceivedObject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nom;
	private String prenom;
	public ReceivedObject(String nom, String prenom) {
		super();
		this.nom = nom;
		this.prenom = prenom;
	}
	public ReceivedObject() {}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
}
