package com.vue;

import com.gestionnaireLivraisons.*;
import com.controleur.EcouteurListeLivreurs;
import com.observer.Observable;
import com.observer.Observateur;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

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

        this.setLayout(new BorderLayout());
        this.tableLivreurs = new ComposantTable("Livreurs", 430, 220, this.nomsColonnes, this.donneesCentrees);
        this.add(this.tableLivreurs, BorderLayout.CENTER);

        this.enregisterEcouteur(new EcouteurListeLivreurs(this));
        this.gestionnaireLivraisons.ajouterObservateur(this);
        this.seMettreAJour(this.gestionnaireLivraisons);

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
        this.tableLivreurs.enregistrerEcouteur(ecouteurLL);

    }

    /**
     * Retourne l'objet Livreur sélectionné dans la Table,
     * Null si aucun livreur n'est sélectionné
     *
     * @return Le livreur sélectionné dans cette table.
     */
    public Livreur livreurSelectionne() {
        int index = this.tableLivreurs.ligneSelectionnee();
        if (index < 0) {
            return null;
        }

        String idLivreur = this.tableLivreurs.lireCase(index, 0);
        try {
            return this.gestionnaireLivraisons.getLivreursEnregistres().rechercher(Integer.parseInt(idLivreur));
        } catch (NumberFormatException nfe) {
            return null;
        }
    }

    private Vector<Vector<String>> calculerDonnees() {
        Vector<Vector<String>> donnees = new Vector<>();
        for (Livreur livreur : this.gestionnaireLivraisons.getLivreursEnregistres()) {
            Vector<String> ligne = new Vector<>();
            ligne.add(String.valueOf(livreur.getId()));
            ligne.add(livreur.getNom());
            ligne.add(livreur.type());
            ligne.add(livreur.isAuthentifie() ? "✔" : "✘");
            donnees.add(ligne);
        }
        return donnees;
    }

    @Override
    public void seMettreAJour(Observable observable) {
        this.tableLivreurs.mettreAJour(this.calculerDonnees());
    }


}
