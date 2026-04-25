package com.controleur;

import com.vue.PanneauLivreurs;
import com.vue.InfoLivreurDialogue;
import com.gestionnaireLivraisons.Livreur;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * La classe des écouteurs de la liste (Table de ComposantTable) de livreurs.
 *
 */
public class EcouteurListeLivreurs implements MouseListener {

    private PanneauLivreurs panneauLivreurs;

    /**
     * Le constructeur pour un tel écouteur.
     * @param panneauLivreurs Le panneau à écouter.
     */
    public EcouteurListeLivreurs(PanneauLivreurs panneauLivreurs) {
        this.panneauLivreurs = panneauLivreurs;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!SwingUtilities.isLeftMouseButton(e) || e.getClickCount() < 2) {
            return;
        }

        Livreur livreur = this.panneauLivreurs.livreurSelectionne();
        if (livreur != null) {
            new InfoLivreurDialogue(this.panneauLivreurs.getMiniServerUI(), livreur);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Rien à faire
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Rien à faire
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Rien à faire
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Rien à faire
    }
}