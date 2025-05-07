package fr.univrennes.istic.l2gen.geometrie;

public class Triangle implements IForme {
    private Point a;
    private Point b;
    private Point c;
    private String color;
    private int angle;

    /**
     * Constructeur de la classe Triangle
     * 
     * @param xa le point a en abscisse
     * @param ya le point a en ordonnee
     * @param xb le point b en abscisse
     * @param yb le point b en ordonnee
     * @param xc le point c en abscisse
     * @param yc le point c en ordonnee
     */
    public Triangle(double xa, double ya, double xb, double yb, double xc, double yc) {
        this.a = new Point(xa, ya);
        this.b = new Point(xb, yb);
        this.c = new Point(xc, yc);
        this.color = "white";
    }

    /**
     * Constructeur de la classe Triangle
     * 
     * @param xa le point a en abscisse
     * @param ya le point a en ordonnee
     * @param xb le point b en abscisse
     * @param yb le point b en ordonnee
     */
    public Triangle(Point pa, Point pb, Point pc) {
        this.a = pa;
        this.b = pb;
        this.c = pc;
        this.color = "white";

    }

    /**
     * Fonction qui retourne le couleur du triangle
     * 
     * @return String, la couleur du triangle
     */
    public String getColor() {
        return this.color;
    }

    /**
     * Fonction qui retourne le centre du triangle
     * 
     * @return Point, le centre du triangle
     */
    @Override
    public Point centre() {
        return new Point((a.x() + b.x() + c.x()) / 3, (a.y() + b.y() + c.y()) / 3);
    }

    /**
     * description de la classe Triangle
     * 
     * @param ind l'indentation
     * @return String, la description du triangle
     * @see IForme#description(int)
     */
    @Override
    public String description(int ind) {
        String desc, inde;
        inde = "";
        for (int i = 1; i < ind; i++) {
            inde = inde + "  ";
        }
        String format = "%.2f";
        desc = String.format(
                "Triangle " + String.format(format, a.x()) + "," + String.format(format, a.y()) + " "
                        + String.format(format, b.x()) + "," + String.format(format, b.y()) + " "
                        + String.format(format, c.x())
                        + "," + String.format(format, c.y()),
                a.x(), a.y(), b.x(), b.y(), c.x(), c.y());
        return inde + desc;
    }

    /**
     * Fonction qui retourne la hauteur du triangle
     * 
     * @return double, la hauteur du triangle
     * @see IForme#hauteur()
     */
    @Override
    public double hauteur() {
        double min = 0, max = 0;
        if (a.y() <= b.y()) {
            min = a.y();
            max = b.y();
            if (max < c.y()) {
                max = c.y();
            }
        }
        return max - min;

    }

    /**
     * Fonction qui retourne la largeur du triangle
     * 
     * @return double, la largeur du triangle
     * @see IForme#largeur()
     */
    @Override
    public double largeur() {
        double min = 0, max = 0;
        if (a.x() < b.x()) {
            min = a.x();
            max = b.x();
            if (max < c.x()) {
                max = c.x();
            } else if (min > c.x()) {
                min = c.x();
            }
        }
        return max - min;
    }

    /**
     * * Fonction qui deplace le triangle
     * 
     * @param x le deplacement en abscisse
     * @param y le deplacement en ordonnee
     * @see IForme#deplacer(double, double)
     */
    @Override
    public void deplacer(double x, double y) {
        this.a.plus(x, y);
        this.b.plus(x, y);
        this.c.plus(x, y);
    }

    /**
     * * Fonction qui duplique le triangle
     * 
     * @return le triangle duplique
     * @see IForme#dupliquer()
     */
    @Override
    public IForme dupliquer() {
        return new Triangle(this.a, this.b, this.c);
    }

    @Override
    public void redimensionner(double x, double y) {
        this.a.plus((a.x() * x) - a.x(), (a.y() * y) - a.y());
        this.b.plus((b.x() * x) - b.x(), (b.y() * y - b.y()));
        this.c.plus((c.x() * x - c.x()), (c.y() * y - c.y()));
    }

    /**
     * * Fonction qui retourne la representation SVG du triangle
     * 
     * @return String, la representation SVG du triangle
     * @see IForme#enSVG()
     */
    @Override
    public String enSVG() {
        if (angle != 0) {
            return "<polygon points=\"" + a.x() + "," + a.y() + " "
                    + b.x() + "," + b.y() + " "
                    + c.x() + "," + c.y() + " "
                    + "\" fill=\"" + this.color + "\" stroke=\"black\"/> " + "transform= rotate(" + angle + ")";
        } else {
            return "<polygon points=\"" + a.x() + "," + a.y() + " "
                    + b.x() + "," + b.y() + " "
                    + c.x() + "," + c.y() + " "
                    + "\" fill=\"" + this.color + "\" stroke=\"black\"/>";
        }
    }

    /**
     * * Fonction qui retourne la couleur du triangle
     * 
     * @param couleur la couleur du triangle en liste de String
     * @see IForme#colorier(String...)
     */
    @Override
    public void colorier(String... couleur) {
        this.color = couleur[0];
    }

    /**
     * tourner le triangle d'un angle
     * 
     * @param angle l'angle de rotation
     * @see IForme#tourner(int)
     */
    @Override
    public void tourner(int angle) {
        Point centre = centre();

        // Prendre le centre du triangle comme l'origine des coordonnées
        double p1x = a.x() - centre.x();
        double p2x = b.x() - centre.x();
        double p3x = c.x() - centre.x();

        double p1y = a.y() - centre.y();
        double p2y = b.y() - centre.y();
        double p3y = c.y() - centre.y();

        // Rotation
        double p1xR = p1x * Math.cos(Math.toRadians(angle)) - p1y * Math.sin(Math.toRadians(angle));
        double p2xR = p2x * Math.cos(Math.toRadians(angle)) - p2y * Math.sin(Math.toRadians(angle));
        double p3xR = p3x * Math.cos(Math.toRadians(angle)) - p3y * Math.sin(Math.toRadians(angle));

        double p1yR = p1x * Math.sin(Math.toRadians(angle)) + p1y * Math.cos(Math.toRadians(angle));
        double p2yR = p2x * Math.sin(Math.toRadians(angle)) + p2y * Math.cos(Math.toRadians(angle));
        double p3yR = p3x * Math.sin(Math.toRadians(angle)) + p3y * Math.cos(Math.toRadians(angle));

        // Remettre le centre du triangle comme l'origine des coordonnées

        double alphaX = p1xR + centre.x();
        double alphaY = p1yR + centre.y();

        double betaX = p2xR + centre.x();
        double betaY = p2yR + centre.y();

        double thetaX = p3xR + centre.x();
        double thetaY = p3yR + centre.y();

        this.a = new Point(alphaX, alphaY);
        this.b = new Point(betaX, betaY);
        this.c = new Point(thetaX, thetaY);

    }

    /**
     * * Fonction qui aligne le triangle
     * 
     * @param alin  l'alignement
     * @param cible la cible de l'alignement
     * @see IForme#aligner(Alignement, double)
     * @return le triangle aligne
     */
    @Override
    public IForme aligner(Alignement alin, double cible) {
        return this;
    }
}