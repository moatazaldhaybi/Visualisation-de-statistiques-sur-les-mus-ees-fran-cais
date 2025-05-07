package fr.univrennes.istic.l2gen.geometrie;

public class Rectangle implements IForme {
    private Point centre;
    private double hauteur;
    private double largeur;
    private String color;
    private double angle;

    /**
     * Constructeur de la classe Rectangle
     * 
     * @param centreX le cnetre du rectangle en abscisse
     * @param centreY le centre du rectangle en ordonnee
     * @param largeur le largeur du rectangle
     * @param hauteur le hauteur du rectangle
     * @param color   la couleur du rectangle
     */
    public Rectangle(double centreX, double centreY, double largeur, double hauteur, String color) {
        this.centre = new Point(centreX, centreY);
        this.hauteur = hauteur;
        this.largeur = largeur;
        this.color = color;
    }

    /**
     * Constructeur de la classe Rectangle
     * 
     * @param centreX le cnetre du rectangle en abscisse
     * @param centreY le centre du rectangle en ordonnee
     * @param largeur le largeur du rectangle
     * @param hauteur le hauteur du rectangle
     * @param color   la couleur du rectangle
     */
    public Rectangle(double centreX, double centreY, double largeur, double hauteur) {
        this.centre = new Point(centreX, centreY);
        this.hauteur = hauteur;
        this.largeur = largeur;
        this.color = "white";
    }

    /**
     * Constructeur de la classe Rectangle
     * 
     * @param pt      le centre du rectangle
     * @param largeur le largeur du rectangle
     * @param hauteur le hauteur du rectangle
     * @param color   la couleur du rectangle
     */
    public Rectangle(Point pt, double largeur, double hauteur) {
        this.centre = pt;
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.color = "white";

    }

    /**
     * Fonction qui retourne le centre du rectangle
     * 
     * @return le centre du rectangle
     */
    public Point getCentre() {
        return this.centre;
    }

    /**
     * Fonction qui retourne la largeur du rectangle
     * 
     * @param angle l'angle de rotation du rectangle
     */

    @Override
    public void tourner(int angle) {
        double finalAngle = this.angle + angle;
        if (finalAngle > 360) {
            this.angle = finalAngle - 360;
        } else {
            this.angle = finalAngle;
        }
    }

    /**
     * * Fonction qui retourne la largeur du rectangle
     * 
     * @return le centre du rectangle
     */
    @Override
    public Point centre() {
        return this.centre;
    }

    /**
     * Fonction description du rectangle
     * 
     * @param indent le nombre d'espace a ajouter avant la description
     * @return la description du rectangle
     */
    @Override
    public String description(int indent) {
        String result = "";
        for (int i = 0; i < indent; i++) {
            result += "  ";
        }
        result += "Rectangle Centre=" + (int) this.centre.x() + "," + (int) this.centre.y() + " L=" + this.largeur
                + " H=" + this.hauteur + " Couleur=" + this.color + " Angle=" + this.angle;
        return result;
    }

    /**
     * fonction qui retourne la hauteur du rectangle
     * 
     * @return la hauteur du rectangle
     * @see IForme#hauteur()
     */
    @Override
    public double hauteur() {
        return this.hauteur;
    }

    /**
     * * fonction qui retourne la largeur du rectangle
     * 
     * @return la largeur du rectangle
     * @see IForme#largeur()
     */
    @Override
    public double largeur() {
        return this.largeur;
    }

    /**
     * * Fonction qui deplace le rectangle
     * 
     * @param dx le deplacement en abscisse
     * @param dy le deplacement en ordonnee
     * @see IForme#deplacer(double, double)
     */
    @Override
    public void deplacer(double dx, double dy) {
        this.centre = this.centre.plus(dx, dy);
    }

    /**
     * Fonction qui duplique le rectangle
     * 
     * @return le rectangle duplique
     * @see IForme#dupliquer()
     */
    @Override
    public IForme dupliquer() {
        Rectangle result = new Rectangle(this.centre, this.largeur, this.hauteur);
        result.colorier(this.color);
        result.tourner((int) this.angle);
        return result;
    }

    /**
     * * Fonction qui redimensionne le rectangle
     * 
     * @param px le facteur de redimensionnement en abscisse
     * @param py le facteur de redimensionnement en ordonnee
     */
    @Override
    public void redimensionner(double px, double py) {
        this.hauteur *= px;
        this.largeur *= py;
    }

    /**
     * * Fonction qui aligne le rectangle
     * 
     * @param align le type d'alignement
     * @param cible la valeur cible de l'alignement
     * @return le rectangle aligne
     */
    public IForme aligner(Alignement align, double cible) {
        double ecart;
        switch (align) {
            case HAUT:
                ecart = (cible + this.hauteur / 2) - this.centre.y();
                this.centre.plus(0, ecart);
                break;
            case GAUCHE:
                ecart = (cible + this.largeur / 2) - this.centre.x();
                this.centre.plus(ecart, 0);
                break;
            case DROITE:
                ecart = (cible - this.largeur / 2) - this.centre.x();
                this.centre.plus(ecart, 0);
                break;
            case BAS:
                ecart = (cible - this.hauteur / 2) - this.centre.y();
                this.centre.plus(0, ecart);
                break;
            default:
                break;
        }
        return this;
    }

    /**
     * * Fonction qui retourne le code SVG du rectangle
     * 
     * @return le code SVG du rectangle
     * @see IForme#enSVG()
     */
    @Override
    public String enSVG() {
        double xSVG = this.centre.x() - this.largeur / 2;
        double ySVG = this.centre.y() - this.hauteur / 2;
        String result = "<rect x=\"" + xSVG + "\" y=\"" + ySVG + "\" width=\"" + this.largeur
                + "\" height=\"" + this.hauteur + "\" fill=\"" + this.color + "\" stroke=\"black\" ";
        if (this.angle != 0) {
            result += "transform=\"rotate(" + this.angle + " " + this.centre.x() + " " + this.centre.y() + ")\"";
        }
        return result + "/>";
    }

    /**
     * * Fonction qui retourne la couleur du rectangle
     * 
     * @param couleur la couleur du rectangle en liste de String
     */
    @Override
    public void colorier(String... couleur) {
        this.color = couleur[0];
    }

    /**
     * * Fonction qui retourne la couleur du rectangle
     * 
     * @return la couleur du rectangle
     * @see IForme#getColor()
     */
    @Override
    public String getColor() {
        return this.color;
    }

}
