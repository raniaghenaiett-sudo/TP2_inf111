package com.controleur;

import com.vue.InfoLivreurDialogue;
import com.vue.PanneauLivreurs;
import com.gestionnaireLivraisons.Livreur;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * La classe des écouteurs de la liste (Table de ComposantTable) de livreurs.
 *
 */
public class EcouteurListeLivreurs extends MouseAdapter {

    private PanneauLivreurs panneauLivreurs;

    /**
     * Le constructeur pour un tel écouteur.
     * @param panneauLivreurs Le panneau à écouter.
     */
    public EcouteurListeLivreurs(PanneauLivreurs panneauLivreurs) {
        // TODO : À compléter/modifier
        this.panneauLivreurs = panneauLivreurs;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {  //double-clic
            Livreur livreur = this.panneauLivreurs.livreurSelectionne();

            if (livreur != null) {
                new InfoLivreurDialogue(
                        this.panneauLivreurs.getMiniServerUI(),
                        livreur
                );
            }
        }
    }
    // TODO : À compléter/modifier
}