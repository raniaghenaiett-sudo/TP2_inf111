package com.vue;

import com.gestionnaireLivraisons.*;
import com.observer.Observable;
import com.observer.Observateur;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

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
        this.gestionnaireLivraisons = gestionnaireLivraisons;

        setLayout(new BorderLayout());

        this.tableLivraisons = new ComposantTable("Liste des livraisons", 400, 300, nomsColonnes, donneesCentrees);
        this.add(this.tableLivraisons, BorderLayout.CENTER);

        rafraichir();

        this.gestionnaireLivraisons.ajouterObservateur(this);
    }

    public void rafraichir() {
        Vector<Vector<String>> donnees = new Vector<>();

        for (Livraison l : this.gestionnaireLivraisons.getListeLivraisons()) {
            Vector<String> ligne = new Vector<>();
            ligne.add(String.valueOf(l.getId()));
            ligne.add(String.valueOf(l.getLot()));
            ligne.add(l.getPriorite());
            ligne.add(String.valueOf(l.getTentative()));
            ligne.add(l.getStatut());
            donnees.add(ligne);
        }

        this.tableLivraisons.mettreAJour(donnees);
    }
    // on implémente l'interface Observateur
    @Override
    public void seMettreAJour(Observable observable) {
        rafraichir();
    }
}
