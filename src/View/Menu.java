package View;

import Controler.Map;
import Model.BombermanGame;
import Strategies.Strategy;
import Strategies.StrategyBomberman;
import Strategies.StrategyBombermanInteractif;
import Strategies.StrategyBombermanRandom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

public class Menu extends JFrame {
    //choix du stage
    private JComboBox<File> liste_lay;
    private String content = null;

    //Différent paneldu menu
    private JPanel top = null;
    private JPanel reviewMap = null;
    private JPanel choix_strategie = null;

    private BombermanGame game = null;
    private PanelCommande pc = null;
    private JButton jouer = null;

    private String[] names_strategies = {"Bomberman IA 1","Bomberman Aléatoire","Bomberman Interactif"};
    private ArrayList<Strategy> objets_strategies = new ArrayList<>();
    private ArrayList<JComboBox<String>> j_strategies = new ArrayList<>();

    public Menu() throws HeadlessException {
        this.liste_lay = liste_lay;
        this.setTitle("Menu Jeu Bomberman");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        game = new BombermanGame();

        top = new JPanel();
        top.setLayout(new GridLayout(2,1));

        //Construction du panneau du choix de niveau + jouer
        File repertoire = new File("./layouts/");
        File[] files=repertoire.listFiles();

        liste_lay = new JComboBox<File>(files);
        jouer = new JButton("Jouer");
        content = liste_lay.getSelectedItem().toString();

        Map map = null;
        try {
            map = new Map(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        reviewMap = new PanelBomberman(map);
        game.setMap(map);
        game.initializeGame();

        //Construction du panneau des stratégies
        int nombre_bbm = game.getEtatJeu().getBombermans().size();
        choix_strategie = new JPanel();
        choix_strategie.setLayout(new GridLayout( nombre_bbm, 2));

        for(int i = 0; i < nombre_bbm; i++) {
            JComboBox<String> liste_strat = new JComboBox<String>(names_strategies);
            j_strategies.add(liste_strat);
            choix_strategie.add(new JLabel("Joueur " + i));
            choix_strategie.add(liste_strat);
        }

        top.add(liste_lay);
        top.add(jouer);
        this.add("North",top);
        this.add("Center",reviewMap);
        this.add("South", choix_strategie);

        try {
            pc = new PanelCommande(game);
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.setSize(map.getSizeX()*50, map.getSizeY()*40+nombre_bbm*25+110);
        this.revalidate();
        this.setLocationRelativeTo(null);
        creer_button(this);
        this.setVisible(true);

    }

    public void creer_button(final Menu menu) {

        liste_lay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                content = liste_lay.getSelectedItem().toString();
                menu.remove(reviewMap);
                menu.remove(choix_strategie);
                try {
                    Map map = new Map(content);
                    reviewMap = new PanelBomberman(map);
                    game.setMap(map);
                    game.initializeGame();
                    int nombre_bbm = game.getEtatJeu().getBombermans().size();

                    menu.setSize(map.getSizeX()*40, map.getSizeY()*40+nombre_bbm*25+100);

                    //Construction du panneau des stratégies
                    j_strategies.clear();
                    choix_strategie = new JPanel();
                    choix_strategie.setLayout(new GridLayout(nombre_bbm, 2));

                    for(int i = 0; i < nombre_bbm; i++) {
                        JComboBox<String> liste_strat = new JComboBox<String>(names_strategies);
                        j_strategies.add(liste_strat);
                        choix_strategie.add(new JLabel("Joueur " + i));
                        choix_strategie.add(liste_strat);
                    }

                    menu.add("Center",reviewMap);
                    menu.add("South",choix_strategie);
                    menu.setLocationRelativeTo(null);
                    menu.revalidate();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        jouer.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evenement) {
                for(int i = 0; i<j_strategies.size();i++) {
                    switch(j_strategies.get(i).getSelectedItem().toString()) {
                        case "Bomberman IA 1":
                            objets_strategies.add(new StrategyBomberman());
                            break;
                        case "Bomberman Aléatoire":
                            objets_strategies.add(new StrategyBombermanRandom());
                            break;
                        case "Bomberman Interactif":
                            objets_strategies.add(new StrategyBombermanInteractif());
                            break;
                    }
                }
                content = liste_lay.getSelectedItem().toString();
                try {
                    ViewGame vc = new ViewGame(content,game, pc);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                game.init();
                game.getEtatJeu().setStrategies_bombermans(objets_strategies);
                menu.dispose();
            }
        });
    }

}

