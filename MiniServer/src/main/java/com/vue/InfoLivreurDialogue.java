package com.vue;

import com.gestionnaireLivraisons.*;
import java.util.Vector;

import com.observer.Observable;
import com.observer.Observateur;

import javax.swing.*;  //POUR JPanel, JDialog
import java.awt.*;   // pour BorderLayout, GridLayout

/**
 * Classe qui affiche la boite de dialogue pour les informations d'un livreur.
 *
 */
public class InfoLivreurDialogue extends JDialog implements Observateur {
   //les attributs
    private final Livreur livreur;
    private ComposantTable grilleLivraisonsEnCours;
    private ComposantTable grilleLivraisonsEffectuees;
    private final String[] nomsColonnes = {"N°", "Lot", "Priorité", "Statut"};
    /**
     * Le constructeur de cette boite de dialogue.
     *
     * @param miniServerUI           La fenêtre contenant cette boite de dialogue.
     * @param livreur                Le livreur dont on veut afficher les données (infos personnelles et livraisons).
     */
    public InfoLivreurDialogue(MiniServerUI miniServerUI, Livreur livreur) {
        super(miniServerUI, "Informations livreur", true);
        this.livreur = livreur;

        // Enregistrer ce dialogue comme observateur du livreur (Q2.5)
        this.livreur.ajouterObservateur(this);

        this.initialiserComposants();

    }

    /**
     * Afficher la boite de dialogue contenant les infos du livreur.
     * Cette méthode est invoquée par le constructeur
     *
     */
    private void initialiserComposants() {

        this.setLayout(new BorderLayout());

        JPanel panneauInfos = new JPanel();
        panneauInfos.setLayout(new BoxLayout(panneauInfos, BoxLayout.Y_AXIS));
        panneauInfos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panneauInfos.add(new JLabel("Id : " + livreur.getId()));
        panneauInfos.add(new JLabel("Nom : " + livreur.getNom()));
        panneauInfos.add(new JLabel("Type : " + livreur.type()));
        panneauInfos.add(new JLabel("Capacité : " + livreur.capaciteLivraison()));


        JPanel panneauTables = new JPanel(new GridLayout(1, 2));

        this.grilleLivraisonsEnCours = new ComposantTable(
                "Livraisons en cours", 300, 200, nomsColonnes);

        this.grilleLivraisonsEffectuees = new ComposantTable(
                "Livraisons effectuées", 300, 200, nomsColonnes);

        panneauTables.add(this.grilleLivraisonsEnCours);
        panneauTables.add(this.grilleLivraisonsEffectuees);


        JPanel panneauBas = new JPanel();  // conteneur pour le bouton fermer
        JButton boutonFermer = new JButton("Fermer");

        // Classe anonyme
        boutonFermer.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                dispose();
            }
        });

        panneauBas.add(boutonFermer);

        //assemble
        this.add(panneauInfos, BorderLayout.WEST);
        this.add(panneauTables, BorderLayout.CENTER);
        this.add(panneauBas, BorderLayout.SOUTH);

        //rempli les tables
        this.rafraichir();

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

    /**
     * Rafraîchit les deux tables de livraisons.
     */
    public void rafraichir() {
        this.grilleLivraisonsEnCours.mettreAJour(
                calculerDonnees(this.livreur.getLivraisonsEnCours()));

        this.grilleLivraisonsEffectuees.mettreAJour(
                calculerDonnees(this.livreur.getLivraisonsEffectuees()));
    }

    /**
     * Mise à jour automatique quand le livreur change (patron Observer - Q2.5).
     *
     * @param observable Le livreur qui a changé.
     */
    @Override
    public void seMettreAJour(Observable observable) {
        this.rafraichir();
    }
}