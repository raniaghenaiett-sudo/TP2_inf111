package com.vue;

import com.gestionnaireLivraisons.GestionnaireLivraisons;
import com.gestionnaireLivraisons.Priorite;

import javax.swing.*;

/**
 * La classe pour les boites de dialogues d'ajout d'une livraison.
 *
 */
public class AjoutLivraisonDialogue extends JDialog {
    final private MiniServerUI miniServerUI;
    final private GestionnaireLivraisons gestionnaireLivraisons;

    /**
     * Le constructeur pour la boite de dialogue d'ajout d'une livrason.
     *
     * @param miniServerUI La fenêtre propriétaire de cette boite de dialogue.
     * @param gestionnaireLivraisons Le gestionnaire de livraisons associé.
     */
    public AjoutLivraisonDialogue(MiniServerUI miniServerUI, GestionnaireLivraisons gestionnaireLivraisons) {
        super(miniServerUI, "Ajout d'une livraison", true);
        this.miniServerUI = miniServerUI;
        this.gestionnaireLivraisons = gestionnaireLivraisons;
        this.initialiserComposants();
    }

    /**
     * Affichage et gestion de la boite de dialogue pour l'ajout d'une livraison.
     *
     */
    public void initialiserComposants() {
        // TODO : À compléter/modifier
        JPanel panel = new JPanel(new java.awt.GridLayout(3, 2, 5, 5));

        JLabel labelLot = new JLabel("Lot :");
        JTextField champLot = new JTextField();

        JLabel labelPriorite = new JLabel("Priorité :");
        JComboBox<String> comboPriorite = new JComboBox<>(
                new String[]{"NORMALE", "URGENTE"});

        JButton boutonAjouter = new JButton("Ajouter");
        JButton boutonAnnuler = new JButton("Annuler");

        panel.add(labelLot);
        panel.add(champLot);
        panel.add(labelPriorite);
        panel.add(comboPriorite);
        panel.add(boutonAjouter);
        panel.add(boutonAnnuler);

        this.add(panel);

        // Bouton Ajouter
        boutonAjouter.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                try {
                    int lot = Integer.parseInt(champLot.getText().trim());
                    Priorite priorite = comboPriorite.getSelectedItem()
                            .equals("URGENTE") ? Priorite.URGENTE : Priorite.NORMALE;

                    if (!com.gestionnaireLivraisons.Livraison.validerLotLivraison(lot)) {
                        afficherErreur("Numéro de lot invalide !");
                        return;
                    }
                    ajouterLivraison(lot, priorite);
                    JOptionPane.showMessageDialog(miniServerUI,
                            "Livraison ajoutée avec succès!");
                    dispose();
                } catch (NumberFormatException ex) {
                    afficherErreur("Le numéro de lot doit être un nombre entier.");
                }

            }
        });

        //boutonAnnuler
        boutonAnnuler.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                dispose();
            }
        });
        this.pack();
        this.setLocationRelativeTo(this.miniServerUI);
        this.setVisible(true);


    }

    /**
     * Affiche une boite de dialogue pour les erreurs de saisies.
     *
     * @param msg Le message à afficher dans la boite.
     */
    private void afficherErreur(String msg) {
        String[] erreurOptions = {"Opps..."};
        JOptionPane.showOptionDialog(this.miniServerUI, msg,
                "Erreur", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, erreurOptions, erreurOptions[0]);
    }

    /**
     * Ajoute une livraison à la liste des livraisons à effectuer (mise à jour du modèle)
     *
     * @param lot      Le lot de la livraison.
     * @param priorite La priorité de la livraison.
     */
    private void ajouterLivraison(int lot, Priorite priorite) {
        // TODO : À compléter/modifier
        com.gestionnaireLivraisons.Livraison livraison =
                new com.gestionnaireLivraisons.Livraison(priorite, lot);
        this.gestionnaireLivraisons.getLivraisonsAEffectuer().ajouter(livraison);
        this.gestionnaireLivraisons.notifierObservateurs();    }

}
