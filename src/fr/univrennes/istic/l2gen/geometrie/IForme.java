package fr.univrennes.istic.l2gen.geometrie;

public interface IForme {

    /**
     * Colorie la figure selon la ou les couleur(s) en param
     * Pour les figures simples, le premier elem de couleur sera utilise
     * 
     * @param couleur une ou plusieurs couleur(s), en anglais, String
     */
    public void colorier(String... couleur);

    public String getColor();

    /**
     * Retourne le format svg de la figure
     * 
     * @return String
     */
    public String enSVG();

    /**
     * Retourne le centre de la figure
     * 
     * @return Point(x,y). x = largeur, y = hauteur
     */
    public Point centre();

    /**
     * Descriptif de la figure
     * 
     * @param indent l'indentation de la description. indent=1 -> 2 espaces.
     * @return String
     */
    public String description(int indent);

    /**
     * hauteur de la figure
     * 
     * @return double
     */
    public double hauteur();

    /**
     * largeur de la figure
     * 
     * @return double
     */
    public double largeur();

    /**
     * deplace la figure en modifiant son centre.
     * 
     * @param dx double : décalage sur l'axe x de la figure
     * @param dy double : décalage sur l'axe y de la figure
     * @return
     */
    public void deplacer(double dx, double dy);

    /**
     * duplique la figure
     * 
     * @return IForme : une figure. Faire un cast dans le main pour que ca marche.
     */
    public IForme dupliquer();

    /**
     * redimensionne la figure
     * 
     * @param px double : redimensionne la largeur de la figure.
     * @param py double : redimensionne la hauteur de la figure.
     * @return
     */
    public void redimensionner(double px, double py);

    /**
     * Effectue une rotation de la figure d'un certain angle.
     * 
     * @param angle int : l'angle de rotation en degrés.
     *              Cette méthode modifie l'angle de la figure et met à jour la
     *              description de l'angle dans la méthode description().
     * @return
     */
    public void tourner(int angle);

    public IForme aligner(Alignement alin, double cible);

}
