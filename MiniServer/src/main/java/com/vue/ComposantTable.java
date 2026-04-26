package com.vue;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseListener;
import java.util.Vector;
import java.awt.BorderLayout;

/**
 * Un composant qui permet d'afficher des informations dans une table.
 * Ce composant peut être utilisé dans plusieurs panneaux.
 *
 */

public class ComposantTable extends JPanel {
    private final JTable table;
    private final String[] nomsColonnes;

    /**
     * Un tableau pour indiquer si les données de la colonne doivent être centrées (true) ou non (false)
     * Il doit être de la même taille que le tableau nomsColonnes
     *
     * L'utilisation de cet attribut est optionnel. Le but est seulement esthétique
     * Vous pouvez le laisser à null et ne pas l'utiliser dans votre code.
     *
     */
    private final boolean[] donneesCentrees;

    /**
     * Constructeur pour ComposantTable.
     *
     * @param titre   Le titre de la grille.
     * @param largeur La largeur de la grille.
     * @param hauteur La hauteur de la grille.
     * @param nomsColonnes Tableau des noms de colonnes
     * @param donneesCentrees La hauteur de la grille.
     */
    public ComposantTable(String titre, int largeur, int hauteur, String[] nomsColonnes, boolean[] donneesCentrees) {
        this.nomsColonnes = nomsColonnes;
        this.donneesCentrees = donneesCentrees;
        this.table = new JTable();
        this.table.setFont(Config.fonte); // considère la fonte définie dans Config

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder(titre));

        //classe anonyme pour stocker les données qui seront affichées dans l'objet JTable
        DefaultTableModel modele = new DefaultTableModel (null, nomsColonnes){
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };
        this.table.setModel(modele);   //relié données a la table

        this.table.getTableHeader().setReorderingAllowed(false); //pour pas que les colonnes bouges

        this.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //une ligne a la fois

        JScrollPane scrollPane = new JScrollPane(this.table);  //affiche titres des colonnes
        scrollPane.setPreferredSize(new java.awt.Dimension(largeur, hauteur)); //tailel grille

        this.add(scrollPane, BorderLayout.CENTER);  //pour que le panneau soit visible

    }

    /**
     * Constructeur (complété)
     *
     * @param titre
     * @param largeur
     * @param hauteur
     * @param nomsColonnes
     */
    public ComposantTable(String titre, int largeur, int hauteur, String[] nomsColonnes) {
        this(titre, largeur, hauteur, nomsColonnes , null) ;
    }

    /**
     * Enregistre un écouteur de souris sur la  (MouseListener)
     * programmation événementielle
     * @param ml
     */
    public void enregistrerEcouteur(MouseListener ml) {
        this.table.addMouseListener(ml);  // on ajoute le mouselistener sur ma table actuelle
    }

    /**
     * Retourne l'index de la ligne qui est actuellement sélectionnée.
     * Les index commencent à 0.
     *
     * @return La ligne sélectionnée ou -1 síl n'y a aucune sélection.
     */
    public int ligneSelectionnee() {

        return this.table.getSelectedRow();

    }

    /**
     * Retourne la donnée se trouvant à une ligne/colonne données en paramètres.
     *
     * @param ligne La ligne où trouver la donnée.
     * @param colonne La colonne où trouver la donnée.
     * @return La donnée.
     */
    public String lireCase(int ligne, int colonne) {
        return (String) (((DefaultTableModel) this.table.getModel()).getDataVector().get(ligne).get(colonne));
    }

    /**
     * Met à jour les données de la table avec la matrice de données passée en paramètre.
     *
     * @param donnees La matrice de données.
     */
    public void mettreAJour(Vector<Vector<String>> donnees) {
        DefaultTableModel modele = (DefaultTableModel) this.table.getModel();
        //update
        modele.setDataVector(donnees, new Vector<>(java.util.Arrays.asList(this.nomsColonnes)));
    }

}
