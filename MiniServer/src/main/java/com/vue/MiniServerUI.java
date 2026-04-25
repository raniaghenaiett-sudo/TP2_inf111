package com.vue;

import com.atoudeft.serveur.Serveur;
import com.gestionnaireLivraisons.GestionnaireLivraisons;
import com.controleur.EcouteurMenuApplication;

import javax.swing.*;

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
        JMenuItem itemAjouter = new JMenuItem("Ajouter Livraison");
        JMenuItem itemStatistiques = new JMenuItem("Statistiques");
        JMenuItem itemQuitter = new JMenuItem("Quitter");
        itemAjouter.setActionCommand("AJOUTER");
        itemStatistiques.setActionCommand("STATISTIQUES");
        // TODO : À compléter/modifier
        // se reporter à la documentation de JMenu et JMenuBar pour cette partie...
        EcouteurMenuApplication ecouteurMenu = new EcouteurMenuApplication(this);
        itemAjouter.addActionListener(ecouteurMenu);
        itemStatistiques.addActionListener(ecouteurMenu);

        //Quitter
        itemQuitter.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                quitter();
            }
        });

        menuApplication.add(itemAjouter);
        menuApplication.add(itemStatistiques);
        menuApplication.add(itemQuitter);
        menuBar.add(menuApplication);
        this.setJMenuBar(menuBar);

        //Panneaux
        PanneauLivreurs panneauLivreurs = new PanneauLivreurs(this, this.gestionnaireLivraisons);
        PanneauLivraisons panneauLivraisons = new PanneauLivraisons(this.gestionnaireLivraisons);
        PanneauConsole panneauConsole = new PanneauConsole(this.gestionnaireLivraisons);

        JPanel panneauHaut = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
        panneauHaut.add(panneauLivreurs);
        panneauHaut.add(panneauLivraisons);

        JPanel panneauPrincipal = new JPanel(new java.awt.BorderLayout());
        panneauPrincipal.add(panneauHaut, java.awt.BorderLayout.CENTER);
        panneauPrincipal.add(panneauConsole, java.awt.BorderLayout.SOUTH);

        this.add(panneauPrincipal);

        //Fermeture Fenetre
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
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
