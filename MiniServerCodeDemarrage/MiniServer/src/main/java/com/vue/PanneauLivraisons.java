package com.vue;

import com.gestionnaireLivraisons.*;
import com.observer.Observable;
import com.observer.Observateur;

import javax.swing.*;
import java.awt.*;
import java.util.Comparator;
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
        this.setLayout(new BorderLayout());
        this.tableLivraisons = new ComposantTable("Livraisons", 520, 220, this.nomsColonnes, this.donneesCentrees);
        this.add(this.tableLivraisons, BorderLayout.CENTER);

        this.gestionnaireLivraisons.ajouterObservateur(this);
        this.seMettreAJour(this.gestionnaireLivraisons);
    }

    private void ajouterDonneesLivraisons(Vector<Vector<String>> donnees, Iterable<Livraison> livraisons) {
        for (Livraison livraison : livraisons) {
            Vector<String> ligne = new Vector<>();
            ligne.add(String.valueOf(livraison.getId()));
            ligne.add(String.valueOf(livraison.getLot()));
            ligne.add(livraison.getPriorite().toString());
            ligne.add(String.valueOf(livraison.getTentative()));
            ligne.add(livraison.getStatut().toString());
            donnees.add(ligne);
        }
    }

    private Vector<Vector<String>> calculerDonnees() {
        Vector<Vector<String>> donnees = new Vector<>();

        this.ajouterDonneesLivraisons(donnees, this.gestionnaireLivraisons.getLivraisonsAEffectuer());
        this.ajouterDonneesLivraisons(donnees, this.gestionnaireLivraisons.getLivraisonsEchouees());

        for (Livreur livreur : this.gestionnaireLivraisons.getLivreursEnregistres()) {
            this.ajouterDonneesLivraisons(donnees, livreur.getLivraisonsEnCours());
            this.ajouterDonneesLivraisons(donnees, livreur.getLivraisonsEffectuees());
        }

        donnees.sort(Comparator.comparingInt(ligne -> Integer.parseInt(ligne.get(0))));
        return donnees;
    }

    @Override
    public void seMettreAJour(Observable observable) {
        this.tableLivraisons.mettreAJour(this.calculerDonnees());
    }
}
