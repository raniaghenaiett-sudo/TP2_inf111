package com.vue;

import com.gestionnaireLivraisons.GestionnaireLivraisons;
import com.gestionnaireLivraisons.Livreur;
import com.observer.Observable;
import com.observer.Observateur;

import javax.swing.*;
import java.awt.*;

/**
 * La classe des boites de dialogues pour l'affichage des statistiques.
 *
 */
public class StatistiquesDialogue extends JDialog implements Observateur {

    final private GestionnaireLivraisons gestionnaireLivraisons;
    private JPanel infos;   // regroupe les informations de statistiques à afficher
    private JLabel valeurTotalLivraisons;
    private JLabel valeurLivraisonsEffectuees;
    private JLabel valeurLivraisonsEnCours;
    private JLabel valeurLivraisonsEchouees;

    /**
     * Le constructeur pour une boite de dialogue de statistiques.
     *
     * @param miniServerUI La fenêtre propriétaire de cette boite de dialogue.
     * @param gestionnaireLivraisons Le gestionnaire de livraisons associé.
     */
    public StatistiquesDialogue(MiniServerUI miniServerUI, GestionnaireLivraisons gestionnaireLivraisons) {
        super(miniServerUI, "Statistiques", true);
        this.gestionnaireLivraisons = gestionnaireLivraisons;
        this.initialiserComposants();
        this.gestionnaireLivraisons.ajouterObservateur(this);
        this.seMettreAJour(this.gestionnaireLivraisons);

        this.pack();
        this.setLocationRelativeTo(miniServerUI);
        this.setVisible(true);
    }

    /**
     * Prépare la boite de dialogue pour les statistiques
     */
    private void initialiserComposants() {
        this.setLayout(new BorderLayout(8, 8));
        this.infos = new JPanel(new GridLayout(0, 2, 8, 6));

        this.valeurTotalLivraisons = new JLabel();
        this.valeurLivraisonsEffectuees = new JLabel();
        this.valeurLivraisonsEnCours = new JLabel();
        this.valeurLivraisonsEchouees = new JLabel();

        this.ajouterLigne("Nombre total des livraisons", this.valeurTotalLivraisons);
        this.ajouterLigne("Nombre des livraisons effectuées", this.valeurLivraisonsEffectuees);
        this.ajouterLigne("Nombre des livraisons en cours", this.valeurLivraisonsEnCours);
        this.ajouterLigne("Nombre des livraisons échouées", this.valeurLivraisonsEchouees);

        this.add(this.infos, BorderLayout.CENTER);

        JButton fermer = new JButton("Fermer");
        fermer.addActionListener(e -> this.dispose());
        JPanel boutons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        boutons.add(fermer);
        this.add(boutons, BorderLayout.SOUTH);
    }

    private void ajouterLigne(String etiquette, JLabel valeur) {
        this.infos.add(new JLabel(etiquette));
        this.infos.add(valeur);
    }

    private void mettreAJourStats() {
        int nbEnCours = 0;
        int nbEffectuees = 0;
        for (Livreur livreur : this.gestionnaireLivraisons.getLivreursEnregistres()) {
            nbEnCours += livreur.nbLivraisonsEnCours();
            nbEffectuees += livreur.nbLivraisonsEffectuees();
        }

        int nbEnAttente = this.gestionnaireLivraisons.getLivraisonsAEffectuer().taille();
        int nbEchouees = this.gestionnaireLivraisons.getLivraisonsEchouees().taille();
        int nbTotal = nbEnAttente + nbEnCours + nbEffectuees + nbEchouees;

        this.valeurTotalLivraisons.setText(String.valueOf(nbTotal));
        this.valeurLivraisonsEffectuees.setText(String.valueOf(nbEffectuees));
        this.valeurLivraisonsEnCours.setText(String.valueOf(nbEnCours));
        this.valeurLivraisonsEchouees.setText(String.valueOf(nbEchouees));
    }

    @Override
    public void seMettreAJour(Observable observable) {
        this.mettreAJourStats();
    }
}
