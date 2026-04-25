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

    private JScrollPane panneauDefilement;

    /**
     * Constructeur pour cette classe.
     *
     * @param gestionnaireLivraisons Le gestionnaire de livraisons associé.
     */
    public PanneauConsole(GestionnaireLivraisons gestionnaireLivraisons) {
        this.gestionnaireLivraisons = gestionnaireLivraisons;
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createTitledBorder("Console serveur"));

        this.console = new JTextArea();
        this.console.setEditable(false);
        this.console.setRows(7);
        this.console.setFont(Config.fonte);
        this.console.setBackground(Color.BLACK);
        this.console.setForeground(new Color(80, 220, 120));
        this.console.setCaretColor(this.console.getForeground());

        this.panneauDefilement = new JScrollPane(this.console);
        this.add(this.panneauDefilement, BorderLayout.CENTER);

        this.gestionnaireLivraisons.ajouterObservateur(this);
    }

    @Override
    public void seMettreAJour(Observable observable) {
        String trace = this.gestionnaireLivraisons.consommerTrace();
        if (trace != null && !trace.isEmpty()) {
            this.console.append(trace + System.lineSeparator());
            this.console.setCaretPosition(this.console.getDocument().getLength());
        }
    }
}
