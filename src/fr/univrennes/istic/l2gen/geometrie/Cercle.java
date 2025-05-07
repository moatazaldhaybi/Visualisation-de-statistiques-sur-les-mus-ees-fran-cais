package fr.univrennes.istic.l2gen.geometrie;

public class Cercle implements IForme {
    private Point point;
    private double rayon;
    private String color;
    private int angle;

    /**
     * Constructeur de la classe Cercle
     * 
     * @param p le point de depart
     * @param r le rayon du cercle
     */
    public Cercle(Point p, double r) {
        this.point = p;
        this.rayon = r;
        this.color = "white";
        this.angle = 0;
    }

    /**
     * Constructeur de la classe Cercle
     * 
     * @param x le point de depart en abscisse
     * @param y le point de depart en ordonnee
     * @param r le rayon du cercle
     */
    public Cercle(double x, double y, double r) {
        this.point = new Point(x, y);
        this.rayon = r;
        this.color = "white";
        this.angle = 0;
    }

    /**
     * Fonction qui retourne le rayon du cercle
     * 
     * @return double, le rayon du cercle
     */
    public Point centre() {
        return point;
    }

    /**
     * Description de la figure
     * 
     * @param in le nombre d'espace avant la description
     * @return String, la description de la figure
     */
    public String description(int in) {
        String desc, inde;
        inde = "";
        for (int i = 1; i < in; i++) {
            inde = inde + "  ";
        }
        desc = "Cercle centre=" + (int) point.x() + "," + (int) point.y() + " r=" + rayon;
        return inde + desc;
    }

    /**
     * * le hauteur du cercle
     * 
     * @return double, le hauteur du cercle
     */
    public double hauteur() {
        return rayon * 2;
    }

    /**
     * * le largeur du cercle
     * 
     * @return double, le largeur du cercle
     */
    public double largeur() {
        return rayon * 2;
    }

    /**
     * Deplacer le cercle
     * 
     * @param x le deplacement en abscisse
     * @param y le deplacement en ordonnee
     */
    @Override
    public void deplacer(double x, double y) {
        this.point.plus(x, y);
    }

    /**
     * Dupliquer le cercle
     * 
     * @return IForme, le cercle duplique
     */
    @Override
    public IForme dupliquer() {
        return new Cercle(new Point(this.point.x(), this.point.y()), this.rayon);
    }

    /**
     * * Redimensionner le cercle
     * 
     * @param x le facteur de redimensionnement en abscisse
     * @param y le facteur de redimensionnement en ordonnee
     */
    @Override
    public void redimensionner(double x, double y) {
        double red = 0;
        if (x < y) {
            red = x;
        } else {
            red = y;
        }
        this.rayon = rayon * red;
    }

    /**
     * * Mettre le cercle en SVG
     * 
     * @return String, la representation SVG du cercle
     */
    @Override
    public String enSVG() {
        String svg = "";
        svg = "<circle cx=\"" + point.x() + "\" cy=\"" + point.y() + "\" r=\"" + rayon + "\" fill=\"" + color
                + "\" stroke= \"black\" />";

        return svg;
    }

    /**
     * * Fonction qui permet de colorier le cercle
     * 
     * @param couleur la couleur du cercle en tant que liste de String
     */
    @Override
    public void colorier(String... couleur) {
        this.color = couleur[0];
    }

    /**
     * Tourner le cercle
     * 
     * @param angle l'angle de rotation
     */
    @Override
    public void tourner(int angle) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Colorier le cercle
     * 
     * @return String, la couleur du cercle
     */
    @Override
    public String getColor() {
        return this.color;
    }

    /**
     * Alligner le cercle
     * 
     * @param alin  l'alignement
     * @param cible la cible de l'alignement
     * @return IForme, le cercle aligne
     */
    @Override
    public IForme aligner(Alignement alin, double cible) {
        return this;
    }
}
