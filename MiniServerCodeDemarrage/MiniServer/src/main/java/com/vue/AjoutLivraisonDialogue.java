package com.vue;

import com.gestionnaireLivraisons.GestionnaireLivraisons;
import com.gestionnaireLivraisons.Livraison;
import com.gestionnaireLivraisons.Priorite;

import javax.swing.*;
import java.awt.*;

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
        this.setLayout(new BorderLayout(8, 8));

        JPanel formulaire = new JPanel(new GridLayout(2, 2, 8, 8));
        JLabel lotLabel = new JLabel("Lot");
        JTextField lotChamp = new JTextField();
        JLabel prioriteLabel = new JLabel("Priorité");
        JComboBox<Priorite> prioriteChoix = new JComboBox<>(Priorite.values());

        formulaire.add(lotLabel);
        formulaire.add(lotChamp);
        formulaire.add(prioriteLabel);
        formulaire.add(prioriteChoix);

        JPanel boutons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton annuler = new JButton("Annuler");
        JButton ajouter = new JButton("Ajouter");
        boutons.add(annuler);
        boutons.add(ajouter);

        annuler.addActionListener(e -> this.dispose());
        ajouter.addActionListener(e -> {
            String texteLot = lotChamp.getText() == null ? "" : lotChamp.getText().trim();
            if (texteLot.isEmpty()) {
                this.afficherErreur("Le numéro de lot est obligatoire.");
                return;
            }

            try {
                int lot = Integer.parseInt(texteLot);
                if (!Livraison.validerLotLivraison(lot)) {
                    this.afficherErreur("Numéro de lot invalide.");
                    return;
                }
                this.ajouterLivraison(lot, (Priorite) prioriteChoix.getSelectedItem());
                this.dispose();
            } catch (NumberFormatException nfe) {
                this.afficherErreur("Le numéro de lot doit être un entier.");
            }
        });

        this.add(formulaire, BorderLayout.CENTER);
        this.add(boutons, BorderLayout.SOUTH);
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
        this.gestionnaireLivraisons.getLivraisonsAEffectuer().ajouter(new Livraison(priorite, lot));
        this.gestionnaireLivraisons.notifierObservateurs();
        JOptionPane.showMessageDialog(
                this,
                "Livraison ajoutée avec succès.",
                "Succès",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

}
