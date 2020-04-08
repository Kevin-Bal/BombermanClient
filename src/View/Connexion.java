package View;

import Client.ClientEmetteur;
import Client.ClientReceveur;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Connexion extends JFrame {

    private JPanel container = new JPanel();
    private JTextField champMail = new JTextField("");
    private JTextField champMdp = new JTextField("");
    private JButton connect = new JButton("Connexion");
    private JLabel labelMail = new JLabel("Email :");
    private JLabel labelMDP = new JLabel("Mot de passe :");
    private JLabel etatConnexion = new JLabel();


    public Connexion(ClientEmetteur ce){
        this.setTitle("Connexion");
        this.setSize(300, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        container.setBackground(Color.white);
        container.setLayout(new BorderLayout());
        champMail.setPreferredSize(new Dimension(100, 20));

        JPanel mid = new JPanel();
        mid.setLayout(new GridLayout(2,2));
        mid.add(labelMail);
        mid.add(champMail);
        mid.add(labelMDP);
        mid.add(champMdp);
        container.add(etatConnexion,BorderLayout.NORTH);
        container.add(mid, BorderLayout.CENTER);
        container.add(connect, BorderLayout.SOUTH);
        this.setContentPane(container);
        this.setVisible(true);

        connect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evenement) {

                String email = champMail.getText();
                String mdp = champMdp.getText();
                System.out.println("Test : "+email);
                ce.getSortie().println(email);
                ce.getSortie().println(mdp);
            }
        });
    }

    public void changeEtat (String message){
        etatConnexion.setText(message);
    }
}
