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

    private final Livreur livreur;
    private JLabel valeurAuthentifie;

    private ComposantTable grilleLivraisonsEnCours;
    private ComposantTable grilleLivraisonsEffectuees;

    /**
     * Le constructeur de cette boite de dialogue.
     *
     * @param miniServerUI           La fenêtre contenant cette boite de dialogue.
     * @param livreur                Le livreur dont on veut afficher les données (infos personnelles et livraisons).
     */
    public InfoLivreurDialogue(MiniServerUI miniServerUI, Livreur livreur) {
        super(miniServerUI, "Informations livreur", true);
        this.livreur = livreur;
        this.initialiserComposants();
        this.livreur.ajouterObservateur(this);
        this.seMettreAJour(this.livreur);
        this.pack();
        this.setLocationRelativeTo(miniServerUI);
        this.setVisible(true);
    }

    /**
     * Afficher la boite de dialogue contenant les infos du livreur.
     * Cette méthode est invoquée par le constructeur
     *
     */
    private void initialiserComposants() {
        this.setLayout(new BorderLayout(8, 8));

        JPanel infos = new JPanel(new GridLayout(0, 2, 8, 4));
        infos.add(new JLabel("Id"));
        infos.add(new JLabel(String.valueOf(this.livreur.getId())));
        infos.add(new JLabel("Nom"));
        infos.add(new JLabel(this.livreur.getNom()));
        infos.add(new JLabel("Type"));
        infos.add(new JLabel(this.livreur.type()));
        infos.add(new JLabel("Capacité"));
        infos.add(new JLabel(String.valueOf(this.livreur.capaciteLivraison())));
        infos.add(new JLabel("Authentifié"));
        this.valeurAuthentifie = new JLabel(this.livreur.isAuthentifie() ? "✔" : "✘");
        infos.add(this.valeurAuthentifie);

        String[] colonnes = {"Id", "Lot", "Priorité", "Tentative", "Statut"};
        boolean[] centrees = {true, true, true, true, true};
        this.grilleLivraisonsEnCours = new ComposantTable("Livraisons en cours", 450, 150, colonnes, centrees);
        this.grilleLivraisonsEffectuees = new ComposantTable("Livraisons effectuées", 450, 150, colonnes, centrees);

        this.grilleLivraisonsEnCours.mettreAJour(this.calculerDonnees(this.livreur.getLivraisonsEnCours()));
        this.grilleLivraisonsEffectuees.mettreAJour(this.calculerDonnees(this.livreur.getLivraisonsEffectuees()));

        JPanel tables = new JPanel(new GridLayout(2, 1, 8, 8));
        tables.add(this.grilleLivraisonsEnCours);
        tables.add(this.grilleLivraisonsEffectuees);

        JButton fermer = new JButton("Fermer");
        fermer.addActionListener(e -> this.dispose());
        JPanel boutons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        boutons.add(fermer);

        this.add(infos, BorderLayout.NORTH);
        this.add(tables, BorderLayout.CENTER);
        this.add(boutons, BorderLayout.SOUTH);
    }

    /**
     * Prépare les données à afficher pour une liste de livraisons.
     *
     * @param livraisons La liste de livraisons.
     * @return La matrice des données relatives à la liste de livraisons.
     */
    private Vector<Vector<String>> calculerDonnees(IListeLivraisons livraisons) {
        Vector<Vector<String>> donnees = new Vector<>();
        for (Livraison livraison : livraisons) {
            Vector<String> ligne = new Vector<>();
            ligne.add(String.valueOf(livraison.getId()));
            ligne.add(String.valueOf(livraison.getLot()));
            ligne.add(livraison.getPriorite().toString());
            ligne.add(String.valueOf(livraison.getTentative()));
            ligne.add(livraison.getStatut().toString());
            donnees.add(ligne);
        }
        return donnees;
    }

    @Override
    public void seMettreAJour(Observable observable) {
        this.valeurAuthentifie.setText(this.livreur.isAuthentifie() ? "✔" : "✘");
        this.grilleLivraisonsEnCours.mettreAJour(this.calculerDonnees(this.livreur.getLivraisonsEnCours()));
        this.grilleLivraisonsEffectuees.mettreAJour(this.calculerDonnees(this.livreur.getLivraisonsEffectuees()));
    }

}
