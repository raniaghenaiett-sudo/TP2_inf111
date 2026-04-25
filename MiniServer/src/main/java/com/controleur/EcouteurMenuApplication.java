package com.controleur;


import com.vue.AjoutLivraisonDialogue;
import com.vue.MiniServerUI;
import com.vue.StatistiquesDialogue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Ecouteur pour les items "Ajouter Livraison" et "Statistiques" du Menu Application de la barre de menu
 *
 */
public class EcouteurMenuApplication implements ActionListener {

    final private MiniServerUI miniServerUI;

    public EcouteurMenuApplication(MiniServerUI miniServerUI) {
        this.miniServerUI = miniServerUI;
    }

    // TODO : À compléter/modifier
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "AJOUTER":
                new AjoutLivraisonDialogue(
                        this.miniServerUI,
                        this.miniServerUI.getGestionnaireLivraisons()
                );
                break;
            case "STATISTIQUES":
                new StatistiquesDialogue(
                        this.miniServerUI,
                        this.miniServerUI.getGestionnaireLivraisons()
                );
                break;
        }
    }

}
