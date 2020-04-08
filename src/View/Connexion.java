package View;

import Client.ClientEmetteur;
import Client.ClientReceveur;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Connexion extends JFrame {

    private JPanel container = new JPanel();
    private JTextField champMail = new JTextField("");
    private JTextField champMdp = new JTextField("");
    private JButton connect = new JButton("Connexion");
    private JLabel labelMail = new JLabel("Email :");
    private JLabel labelMDP = new JLabel("Mot de passe :");
    private JLabel etatConnexion = new JLabel();
    private JLabel inscription = new JLabel("Pas de compte ? Insccris toi");
    private JLabel lien = new JLabel("http://localhost:8080/SiteBomberman/subscribe");


    public Connexion(ClientEmetteur ce) {
        this.setTitle("Connexion");
        this.setSize(500, 300);
        this.setLocationRelativeTo(null);
        container.setBackground(Color.white);
        container.setLayout(new BorderLayout());
        champMail.setPreferredSize(new Dimension(100, 300));

        JPanel mid = new JPanel();
        mid.setLayout(new GridLayout(3, 2));
        mid.add(labelMail);
        mid.add(champMail);
        mid.add(labelMDP);
        mid.add(champMdp);
        mid.add(inscription);

        lien.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lien.setForeground(Color.blue);
        addListener(lien);
        mid.add(lien);


        container.add(etatConnexion, BorderLayout.NORTH);
        container.add(mid, BorderLayout.CENTER);
        container.add(connect, BorderLayout.SOUTH);
        this.setContentPane(container);
        this.setVisible(true);

        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                System.out.println("Closed");
                ce.getSortie().println("quitter");
                e.getWindow().dispose();
            }
        });

        connect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evenement) {
                String email = champMail.getText();
                String mdp = champMdp.getText();
                System.out.println("Test : " + email);
                ce.getSortie().println(email);
                ce.getSortie().println(mdp);


            }

        });
    }

    private void addListener(JLabel lb_url) {
        lb_url.addMouseListener(new MouseAdapter() {
            //Click sur le lien
            public void mouseClicked(MouseEvent e) {
                JLabel label=(JLabel)e.getSource();
                String plainText = label.getText().replaceAll("<.*?>", "");
                try {
                    Desktop.getDesktop().browse(new URI(plainText));
                } catch (URISyntaxException ex) {
                    Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            //Survol sur le lien
            public void mouseEntered(MouseEvent e) {
                JLabel label=(JLabel)e.getSource();
                String plainText = label.getText().replaceAll("<.*?>", "");
                //Sousligner le texte
                String urlText="<html><u>"+plainText+"</u></html>";
                label.setText(urlText);
            }

            //Quitte la zone du lien
            public void mouseExited(MouseEvent e) {
                JLabel label=(JLabel)e.getSource();
                String plainText = label.getText().replaceAll("<.*?>", "");
                //Texte sans souslignage
                String urlText="<html>"+plainText+"</html>";
                label.setText(urlText);
            }
        });
    }


    public void changeEtat (String message){
        etatConnexion.setText(message);
    }
}
