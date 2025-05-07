package fr.univrennes.istic.l2gen.geometrie;

public class Secteur implements IForme {
    protected Point centre;
    protected double rayon;
    protected double angle;
    protected double arc;
    protected String color;
    protected int largeArc;

    /**
     * Constructeur de la classe Secteur
     * 
     * @param x     le pint de depart en abscisse
     * @param y     le point de depart en ordonnee
     * @param rayon le rayon du secteur
     * @param angle l'angle de la figure
     * @param arc   l'arc de la figure
     */
    public Secteur(double x, double y, double rayon, double angle, double arc) {
        this.centre = new Point(x, y);
        this.rayon = rayon;
        this.angle = angle;
        this.arc = arc;
        this.color = "white";
    }

    /**
     * Constructeur de la classe Secteur
     * 
     * @param centre le centre du secteur
     * @param rayon  le rayon du secteur
     * @param angle  l'angle de la figure
     * @param arc    l'arc de la figure
     */
    public Secteur(Point centre, double rayon, double angle, double arc) {
        this.centre = centre;
        this.rayon = rayon;
        this.angle = angle;
        this.arc = arc;
        this.color = "white";
    }

    /**
     * Fonction colorier qui permet de colorier le secteur
     */
    @Override
    public void colorier(String... couleur) {
        this.color = couleur[0];
    }

    /**
     * Retourne l'angle de la figure
     * 
     * @return Double, l'angle de la figure
     */
    public double getAngle() {
        return this.angle;
    }

    /**
     * Retourne l'arc de la figure
     * 
     * @return Double, l'arc de la figure
     */
    public double getArc() {
        return this.arc;
    }

    @Override
    public String enSVG() {
        double ptDepartX = this.centre().x() + rayon;
        double ptDepartY = this.centre().y();

        double radians = Math.toRadians(this.arc);
        double x = this.centre.x() + (this.rayon * Math.cos(radians));
        double y = this.centre.y() + (this.rayon * Math.sin(radians));

        this.largeArc = (this.arc > 180) ? 1 : 0;

        String result = "<path d=\"M" + (int) this.centre.x() + " " + (int) this.centre.y() + " L " + (int) ptDepartX
                + " "
                + (int) ptDepartY + " A "
                + this.rayon + " " + this.rayon + " 0 " + this.largeArc + ",1 " + (int) x + " " + (int) y
                + " Z\" fill=\""
                + this.color
                + "\" stroke=\"black\" ";

        if (this.angle != 0.0) {
            result += "transform=\"rotate(" + this.angle + " " + this.centre.x() + " " + this.centre.y() + ")\"";
        }
        return result + "/>";

    }

    /**
     * Fonction qui retourne le centre du secteur
     * 
     * @return le centre du secteur
     */
    @Override
    public Point centre() {
        return this.centre;
    }

    /**
     * Fonction qui tourne le secteur d'un certain angle
     * 
     * @param angle l'angle de rotation
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
     * Fonction description du secteur
     * 
     * @param indent le nombre d'espace a ajouter avant la description
     * @return la description du secteur
     */
    @Override
    public String description(int indent) {
        String result = "";
        for (int i = 0; i < indent; i++) {
            result += "  ";
        }
        result += "Secteur centre=" + (int) this.centre.x() + "," + (int) this.centre.y() + " r=" + this.rayon
                + " Angle=" + this.angle + " Arc=" + this.arc + "Couleur=" + this.color;
        return result;
    }

    @Override
    public double hauteur() {
        return this.rayon;
    }

    @Override
    public double largeur() {
        return this.rayon;
    }

    /**
     * Fonction qui alligne le secteur par rapport a un alignement et une cible
     * 
     * @param align l'alignement
     * @param cible la cible
     * @return le secteur aligné
     */
    // @Override
    public IForme aligner(Alignement align, double cible) {
        double ecart;
        switch (align) {
            case HAUT:
                ecart = (cible + this.hauteur() / 2) - this.centre.y();
                this.centre.plus(0, ecart);
                break;
            case GAUCHE:
                ecart = (cible + this.largeur() / 2) - this.centre.x();
                this.centre.plus(ecart, 0);
                break;
            case DROITE:
                ecart = (cible - this.largeur() / 2) - this.centre.x();
                this.centre.plus(ecart, 0);
                break;
            case BAS:
                ecart = (cible - this.hauteur() / 2) - this.centre.y();
                this.centre.plus(0, ecart);
                break;
            default:
                break;
        }
        return this;
    }

    /**
     * * Fonction qui deplace le secteur
     * 
     * @param dx le deplacement en abscisse
     * @param dy le deplacement en ordonnee
     */
    @Override
    public void deplacer(double dx, double dy) {
        this.centre = this.centre.plus(dx, dy);
    }

    /**
     * * Fonction qui duplique le secteur
     * 
     * @return le secteur duplique
     * @see IForme#dupliquer()
     */
    @Override
    public IForme dupliquer() {
        Secteur result = new Secteur(this.centre, this.rayon, this.angle, this.arc);
        result.colorier(this.color);
        return result;
    }

    /**
     * * Fonction qui redimensionne le secteur
     * 
     * @param px le facteur de redimensionnement en abscisse
     * @param py le facteur de redimensionnement en ordonnee
     * @see IForme#redimensionner(double, double)
     */
    @Override
    public void redimensionner(double px, double py) {
        double rad = 0;
        if (px < py) {
            rad = px;
        } else {
            rad = py;
        }
        this.rayon = rayon * rad;
    }

    /**
     * * Fonction qui retourne la couleur du secteur
     * 
     * @return la couleur du secteur
     */
    @Override
    public String getColor() {
        return this.color;
    }

}
