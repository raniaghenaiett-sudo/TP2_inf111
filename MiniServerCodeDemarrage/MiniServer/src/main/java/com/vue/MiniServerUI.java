package com.vue;

import com.atoudeft.serveur.Serveur;
import com.controleur.EcouteurMenuApplication;
import com.gestionnaireLivraisons.GestionnaireLivraisons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * La classe principale de l'interface utilisateur de l'application MiniServer
 *
 */
public class MiniServerUI extends JFrame {
    final private Serveur serveur;
    final private GestionnaireLivraisons gestionnaireLivraisons;

    /**
     * Constructeur pour l'interface graphique
     *
     * @param serveur La référence du serveur utilisé.
     * @param gestionnaireLivraisons  Le gestionnaire de livraisons.
     */
    public MiniServerUI(Serveur serveur, GestionnaireLivraisons gestionnaireLivraisons) {
        this.serveur = serveur;
        this.gestionnaireLivraisons = gestionnaireLivraisons;
        this.initialiserComposants();
        this.configurerFenetrePrincipale();
    }

    /**
     * Getter pour le gestionnaire de livraisons.
     *
     * @return Le gestionnaire de livraisons.
     */
    public GestionnaireLivraisons getGestionnaireLivraisons() {
        return gestionnaireLivraisons;
    }

    /**
     * Configure la fenêtre graphique.
     *
     */
    private void configurerFenetrePrincipale() {
        // Configuration de la fenêtre
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setTitle("Gestionnaire de livraisons");
        this.setSize(980, 450);
        this.setLocation(100, 100);
    }

    /**
     * Création et placement des composants de la fenêtre principale :
     *   - Création du menu Application est de ses items
     *   - Création des trois panneaux de la fenêtre
     */
    private void initialiserComposants() {
        // Le menu
        JMenuBar menuBar = new JMenuBar();
        JMenu menuApplication = new JMenu("Application");
        JMenuItem itemAjout = new JMenuItem("Ajouter une livraison");
        JMenuItem itemStats = new JMenuItem("Statistiques");
        JMenuItem itemQuitter = new JMenuItem("Quitter");

        itemAjout.setActionCommand("AJOUT_LIVRAISON");
        itemStats.setActionCommand("STATISTIQUES");

        EcouteurMenuApplication ecouteurMenu = new EcouteurMenuApplication(this);
        itemAjout.addActionListener(ecouteurMenu);
        itemStats.addActionListener(ecouteurMenu);
        itemQuitter.addActionListener(e -> quitter());

        menuApplication.add(itemAjout);
        menuApplication.add(itemStats);
        menuApplication.addSeparator();
        menuApplication.add(itemQuitter);
        menuBar.add(menuApplication);
        this.setJMenuBar(menuBar);


        //  Préparer le contenu de la fenêtre.
        this.setLayout(new BorderLayout(8, 8));
        JPanel centre = new JPanel(new GridLayout(1, 2, 8, 8));
        centre.add(new PanneauLivreurs(this, this.gestionnaireLivraisons));
        centre.add(new PanneauLivraisons(this.gestionnaireLivraisons));
        this.add(centre, BorderLayout.CENTER);
        this.add(new PanneauConsole(this.gestionnaireLivraisons), BorderLayout.SOUTH);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                quitter();
            }
        });

        this.setVisible(true);


    }

    /**
     * Quitter l'application :
     * - arrêter le serveur
     * - quitter le gestionnaire de livraisons
     * - libérer la fenêtre
     * - quitter l'application
     */
    public void quitter() {
        this.serveur.arreter();
        this.gestionnaireLivraisons.quitter();
        this.dispose();
        System.exit(0);
    }
}
