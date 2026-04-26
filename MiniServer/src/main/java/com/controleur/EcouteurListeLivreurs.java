package com.controleur;

import com.vue.PanneauLivreurs;
import com.vue.InfoLivreurDialogue;
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
      this.panneauLivreurs = panneauLivreurs;
    }

    // pour détecter le double clic
    @Override
    public void mouseClicked (MouseEvent e){

        if(e.getClickCount()==2){
            Livreur livreur = panneauLivreurs.livreurSelectionne();

        if (livreur != null) {
            InfoLivreurDialogue dialogue = new InfoLivreurDialogue(
                    panneauLivreurs.getMiniServerUI(),
                    livreur
            );
            dialogue.setVisible(true);
        }
        }
    }
}