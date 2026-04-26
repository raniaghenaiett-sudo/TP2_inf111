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
public class PanneauConsole extends JPanel implements Observateur {

    private JTextArea console;
    private GestionnaireLivraisons gestionnaireLivraisons;


    /**
     * Constructeur pour cette classe.
     *
     * @param gestionnaireLivraisons Le gestionnaire de livraisons associé.
     */
    public PanneauConsole(GestionnaireLivraisons gestionnaireLivraisons) {
        this.gestionnaireLivraisons = gestionnaireLivraisons;

        setLayout(new BorderLayout());

        this.console = new JTextArea();
        this.console.setEditable(false);
        this.console.setBackground(Color.BLACK);
        this.console.setForeground(Color.GREEN);
        this.console.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JScrollPane scrollPane = new JScrollPane(this.console);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(scrollPane, BorderLayout.CENTER);

        this.gestionnaireLivraisons.ajouterObservateur(this);
    }
    public void ajouterMessage(String message) {
        SwingUtilities.invokeLater(() -> {
            this.console.append(message + "\n");
            this.console.setCaretPosition(this.console.getDocument().getLength());
        });
    }

    @Override
    public void seMettreAJour(Observable observable) {}

}
