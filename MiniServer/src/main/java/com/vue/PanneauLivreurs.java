package com.vue;

import com.gestionnaireLivraisons.*;
import com.controleur.EcouteurListeLivreurs;
import com.observer.Observable;
import com.observer.Observateur;
import javax.swing.*;

/**
 * Classe de type JPanel pour lister les livreurs enregistrés.
 *
 *
 */
public class PanneauLivreurs extends JPanel implements Observateur {
    // private final JTable table;
    private ComposantTable tableLivreurs;

    final private String[] nomsColonnes = {"Id", "Nom", "Type", "Authentifié"};
    final private boolean[] donneesCentrees = new boolean[]{true, false, true, true};

    private MiniServerUI miniServerUI;
    private GestionnaireLivraisons gestionnaireLivraisons;

    /**
     * Constructeur pour cette classe.
     *
     * @param miniServerUI                La fenêtre qui contient cette gruille.
     * @param gestionnaireLivraisons Le gestionnaire de livraisons associé.
     */
    public PanneauLivreurs(MiniServerUI miniServerUI, GestionnaireLivraisons gestionnaireLivraisons) {
        this.miniServerUI = miniServerUI;
        this.gestionnaireLivraisons = gestionnaireLivraisons;

        this.setLayout(new java.awt.BorderLayout());

        this.tableLivreurs = new ComposantTable("Liste des livreurs", 400, 300, nomsColonnes, donneesCentrees);

        this.add(this.tableLivreurs, java.awt.BorderLayout.CENTER);

        this.gestionnaireLivraisons.ajouterObservateur(this);

        EcouteurListeLivreurs ecouteur = new EcouteurListeLivreurs(this);
        this.enregisterEcouteur(ecouteur);

        this.rafraichir();
    }

    @Override
    public void seMettreAJour(Observable observable) {
        this.rafraichir();
    }

    /**
     * Getter pour l'attribut MiniServerUI de cette classe.
     *
     * @return La fenêtre graphique principale dans laquelle se trouve ce panneau.
     */
    public MiniServerUI getMiniServerUI() {
       return this.miniServerUI;
    }

    /**
     * Enregistrer un écouteur pour ce panneau de livreurs.
     *
     * @param ecouteurLL L'écouteur à ajouter.
     */
    public void enregisterEcouteur(EcouteurListeLivreurs ecouteurLL) {
        this.tableLivreurs.enregistrerEcouteur(ecouteurLL); //on passe l'écouteur à la table
    }

    /**
     * Retourne l'objet Livreur sélectionné dans la Table,
     * Null si aucun livreur n'est sélectionné
     *
     * @return Le livreur sélectionné dans cette table.
     */
    public Livreur livreurSelectionne() {
        int index = this.tableLivreurs.ligneSelectionnee();

        if (index == -1) {
            return null;
        }

        int i = 0;
        for (Livreur l : this.gestionnaireLivraisons.getLivreursEnregistres()) {
            if (i == index) {
                return l;
            }
            i++;
        }
        return null;
    }



    public void rafraichir() {
     //on prépare ligne et colonnes
        java.util.Vector<java.util.Vector<String>> donnees = new java.util.Vector<>();


        for (Livreur l : this.gestionnaireLivraisons.getLivreursEnregistres()) {
            java.util.Vector<String> ligne = new java.util.Vector<>();

            ligne.add(String.valueOf(l.getId()));
            ligne.add(l.getNom());
            ligne.add(l.type());


            if (l.isAuthentifie()) {
                ligne.add("✔");
            } else {
                ligne.add("✘");
            }

            donnees.add(ligne);
        }


        this.tableLivreurs.mettreAJour(donnees);
    }


}
