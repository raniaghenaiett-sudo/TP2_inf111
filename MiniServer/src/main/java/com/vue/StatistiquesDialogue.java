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
    private JLabel labelTotal;
    private JLabel labelEffectuees;
    private JLabel labelEnCours;
    private JLabel labelEchouees;
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

        // TODO : À compléter/modifier
        this.gestionnaireLivraisons.ajouterObservateur(this);
    }

    /**
     * Prépare la boite de dialogue pour les statistiques
     */
    private void initialiserComposants() {
        // TODO : À compléter/modifier
        this.infos = new JPanel(new GridLayout(4, 2, 10, 5));

        this.labelTotal      = new JLabel("0");
        this.labelEffectuees = new JLabel("0");
        this.labelEnCours    = new JLabel("0");
        this.labelEchouees   = new JLabel("0");

        this.infos.add(new JLabel("Nombre total de livraisons :"));
        this.infos.add(this.labelTotal);
        this.infos.add(new JLabel("Livraisons effectuées :"));
        this.infos.add(this.labelEffectuees);
        this.infos.add(new JLabel("Livraisons en cours :"));
        this.infos.add(this.labelEnCours);
        this.infos.add(new JLabel("Livraisons échouées :"));
        this.infos.add(this.labelEchouees);

        this.infos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.add(this.infos);

        // Mettre à jour les valeurs
        this.rafraichir();

        this.pack();
        this.setLocationRelativeTo(getParent());
        this.setVisible(true);
    }

    @Override
    public void seMettreAJour(Observable observable) {
        this.rafraichir();
    }



    // TODO : À compléter/modifier
    private void rafraichir() {
        // Livraisons en attente
        int enAttente = this.gestionnaireLivraisons.getLivraisonsAEffectuer().taille();

        // Livraisons en cours
        int enCours = 0;
        int effectuees = 0;
        for (Livreur l : this.gestionnaireLivraisons.getLivreursEnregistres()) {
            enCours    += l.nbLivraisonsEnCours();
            effectuees += l.nbLivraisonsEffectuees();
        }

        // Livraisons échouées
        int echouees = this.gestionnaireLivraisons.getLivraisonsEchouees().taille();

        int total = enAttente + enCours + effectuees + echouees;

        this.labelTotal.setText(String.valueOf(total));
        this.labelEffectuees.setText(String.valueOf(effectuees));
        this.labelEnCours.setText(String.valueOf(enCours));
        this.labelEchouees.setText(String.valueOf(echouees));
    }
}
