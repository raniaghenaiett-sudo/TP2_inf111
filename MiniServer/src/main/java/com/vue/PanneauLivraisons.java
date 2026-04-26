package com.vue;

import com.gestionnaireLivraisons.*;
import com.observer.Observable;
import com.observer.Observateur;

import javax.swing.*;
import java.awt.*;

/**
 * La classe qui constitue la table des livraisons.
 *
 */
public class PanneauLivraisons extends JPanel implements Observateur {

    private ComposantTable tableLivraisons;

    final private String[] nomsColonnes = {"Id", "Lot", "Priorité", "Tentatives", "Statut"};
    final private boolean[] donneesCentrees = {true, true, true, true, true};


    private GestionnaireLivraisons gestionnaireLivraisons;

    /**
     * Constructeur pour la classe PanneauLivraisons
     *
     * @param gestionnaireLivraisons Le gestionnaire de livraisons associé.
     */
    public PanneauLivraisons(GestionnaireLivraisons gestionnaireLivraisons) {
        // TODO : À compléter/modifier
        this.gestionnaireLivraisons = gestionnaireLivraisons;
        this.setLayout(new java.awt.BorderLayout());

        this.tableLivraisons = new ComposantTable(
                "Liste des livraisons", 500, 300, nomsColonnes, donneesCentrees);

        this.add(this.tableLivraisons, BorderLayout.NORTH);

        // Q3.1 — Observer
        this.gestionnaireLivraisons.ajouterObservateur(this);

        this.rafraichir();
    }

    @Override
    public void seMettreAJour(Observable observable) {
        this.rafraichir();
    }

    public void rafraichir() {
        java.util.Vector<java.util.Vector<String>> donnees = new java.util.Vector<>();

        //En attente
        for (Livraison l : this.gestionnaireLivraisons.getLivraisonsAEffectuer()) {
            donnees.add(creerLigne(l));
        }

        //En cours
        for (Livreur livreur : this.gestionnaireLivraisons.getLivreursEnregistres()) {
            for (Livraison l : livreur.getLivraisonsEnCours()) {
                donnees.add(creerLigne(l));
            }

            //Effectues
            for (Livraison l : livreur.getLivraisonsEffectuees()) {
                donnees.add(creerLigne(l));
            }
        }

        //Echoue
        for (Livraison l : this.gestionnaireLivraisons.getLivraisonsEchouees()) {
            donnees.add(creerLigne(l));
        }

        this.tableLivraisons.mettreAJour(donnees);
    }

    private java.util.Vector<String> creerLigne(Livraison l) {
        java.util.Vector<String> ligne = new java.util.Vector<>();
        ligne.add(String.valueOf(l.getId()));
        ligne.add(String.valueOf(l.getLot()));
        ligne.add(l.getPriorite().toString());
        ligne.add(String.valueOf(l.getTentative()));
        ligne.add(l.getStatut().toString());
        return ligne;
    }

    // TODO : À compléter/modifier
}
