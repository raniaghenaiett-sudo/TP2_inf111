package com.controleur;


import com.vue.MiniServerUI;
import com.vue.AjoutLivraisonDialogue;
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

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if ("AJOUT_LIVRAISON".equals(action)) {
            new AjoutLivraisonDialogue(this.miniServerUI, this.miniServerUI.getGestionnaireLivraisons());
        } else if ("STATISTIQUES".equals(action)) {
            new StatistiquesDialogue(this.miniServerUI, this.miniServerUI.getGestionnaireLivraisons());
        }
    }

}
