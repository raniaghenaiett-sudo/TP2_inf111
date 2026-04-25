package com.vue;

import com.gestionnaireLivraisons.GestionnaireLivraisons;
import com.observer.Observable;
import com.observer.Observateur;

import javax.swing.*;
import java.awt.*;

/**
 * La classe qui constitue la console dans l'interface graphique.
 *
 */
public class PanneauConsole extends JPanel implements Observateur
{

    private JTextArea console;
    private GestionnaireLivraisons gestionnaireLivraisons;

    // TODO : À compléter/modifier

    /**
     * Constructeur pour cette classe.
     *
     * @param gestionnaireLivraisons Le gestionnaire de livraisons associé.
     */
    public PanneauConsole(GestionnaireLivraisons gestionnaireLivraisons) {
        // TODO : À compléter/modifier
        this.gestionnaireLivraisons = gestionnaireLivraisons;

        this.setLayout(new BorderLayout());

        // JTextArea
        this.console = new JTextArea();
        this.console.setEditable(false);              // non-éditable
        this.console.setBackground(Color.BLACK);      // fond noir
        this.console.setForeground(Color.GREEN);      // texte vert
        this.console.setFont(new Font("Monospaced", Font.PLAIN, 12));

        // JScrollPane pour défilement
        JScrollPane scrollPane = new JScrollPane(this.console);
        scrollPane.setPreferredSize(new Dimension(900, 150));

        this.add(scrollPane, BorderLayout.CENTER);

        // Observer
        this.gestionnaireLivraisons.ajouterObservateur(this);
    }

    @Override
    public void seMettreAJour(Observable observable) {
        String msg = this.gestionnaireLivraisons.consommerTrace();
        if (msg != null && !msg.isEmpty()) {
            this.console.append(msg + "\n");
            // scroll automatique vers le bas
            this.console.setCaretPosition(this.console.getDocument().getLength());
        }
    }



    // TODO : À compléter/modifier
}
