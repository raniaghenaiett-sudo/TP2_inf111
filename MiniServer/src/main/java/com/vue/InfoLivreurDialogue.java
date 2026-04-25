package com.vue;

import com.gestionnaireLivraisons.*;
import com.observer.Observable;
import com.observer.Observateur;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

/**
 * Classe qui affiche la boite de dialogue pour les informations d'un livreur.
 *
 */
public class InfoLivreurDialogue extends JDialog implements Observateur {

    private Livreur livreur;

    private ComposantTable grilleLivraisonsEnCours;
    private ComposantTable grilleLivraisonsEffectuees;
    private final String[] colonnes = {"Id", "Lot", "Priorité", "Tentatives"};
    /**
     * Le constructeur de cette boite de dialogue.
     *
     * @param miniServerUI           La fenêtre contenant cette boite de dialogue.
     * @param livreur                Le livreur dont on veut afficher les données (infos personnelles et livraisons).
     */
    public InfoLivreurDialogue(MiniServerUI miniServerUI, Livreur livreur) {
        // TODO : À compléter/modifier
        super(miniServerUI, "Informations livreur", true);
        this.livreur = livreur;
        this.initialiserComposants();
        this.livreur.ajouterObservateur(this);
        System.err.println("Méthode InfoLivreurDialogue.InfoLivreurDialogue non implémentée.");
    }

    @Override
    public void seMettreAJour(Observable observable) {
        // rafraichir les deux tables
        this.grilleLivraisonsEnCours.mettreAJour(
                this.calculerDonnees(this.livreur.getLivraisonsEnCours()));
        this.grilleLivraisonsEffectuees.mettreAJour(
                this.calculerDonnees(this.livreur.getLivraisonsEffectuees()));
    }

    /**
     * Afficher la boite de dialogue contenant les infos du livreur.
     * Cette méthode est invoquée par le constructeur
     *
     */
    private void initialiserComposants() {
        this.setLayout(new BorderLayout());

        //Panel gauche
        JPanel panelInfos = new JPanel();
        panelInfos.setLayout(new BoxLayout(panelInfos, BoxLayout.Y_AXIS));
        panelInfos.add(new JLabel("Id : " + this.livreur.getId()));
        panelInfos.add(new JLabel("Nom : " + this.livreur.getNom()));
        panelInfos.add(new JLabel("Type : " + this.livreur.type()));
        panelInfos.add(new JLabel("Capacité : " + this.livreur.capaciteLivraison()));
        panelInfos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //Panel centre deux tables
        JPanel panelTables = new JPanel(new FlowLayout());

        this.grilleLivraisonsEnCours = new ComposantTable(
                "Livraisons en cours", 300, 200, colonnes);

        this.grilleLivraisonsEffectuees = new ComposantTable(
                "Livraisons effectuées", 300, 200, colonnes);

        this.grilleLivraisonsEnCours.mettreAJour(
                this.calculerDonnees(this.livreur.getLivraisonsEnCours()));

        this.grilleLivraisonsEffectuees.mettreAJour(
                this.calculerDonnees(this.livreur.getLivraisonsEffectuees()));

        panelTables.add(this.grilleLivraisonsEnCours);
        panelTables.add(this.grilleLivraisonsEffectuees);

        //Bouton Fermer avec classe anonyme
        JButton boutonFermer = new JButton("Fermer");
        boutonFermer.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                dispose();
            }
        });

        JPanel panelBouton = new JPanel();
        panelBouton.add(boutonFermer);

        //Assemblage
        this.add(panelInfos, BorderLayout.WEST);
        this.add(panelTables, BorderLayout.CENTER);
        this.add(panelBouton, BorderLayout.SOUTH);

        this.pack();
        this.setLocationRelativeTo(getParent());
        this.setVisible(true);
    }

    /**
     * Prépare les données à afficher pour une liste de livraisons.
     *
     * @param livraisons La liste de livraisons.
     * @return La matrice des données relatives à la liste de livraisons.
     */
    private Vector<Vector<String>> calculerDonnees(IListeLivraisons livraisons) {
        Vector<Vector<String>> donnees = new Vector<>();

        for (Livraison l : livraisons) {
            Vector<String> ligne = new Vector<>();
            ligne.add(String.valueOf(l.getId()));
            ligne.add(String.valueOf(l.getLot()));
            ligne.add(l.getPriorite().toString());
            ligne.add(String.valueOf(l.getTentative()));
            donnees.add(ligne);
        }

        return donnees;
    }

    // TODO : À compléter/modifier
}
