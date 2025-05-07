package fr.univrennes.istic.l2gen.geometrie;

public class Polygone implements IForme {
    private Point[] points;
    private String color;

    /**
     * Constructeur de la classe Polygone
     * 
     * @param coords les coordonnées des sommets du polygone, en alternant x et y en
     *               list de double
     *               ex: (x1, y1, x2, y2, x3, y3) pour un triangle
     */
    public Polygone(double... coords) {
        if (coords.length % 2 != 0) {
            throw new IllegalArgumentException("Le nombre de coordonnées doit être pair.");
        }
        this.color = "white";
        points = new Point[coords.length / 2];
        for (int i = 0; i < coords.length; i += 2) {
            points[i / 2] = new Point(coords[i], coords[i + 1]);
        }
    }

    /**
     * Fonction qui permet de retourner le SVG du polygone
     * 
     * @return String, le SVG du polygone
     * @see IForme#enSVG()
     */
    @Override
    public String enSVG() {
        StringBuilder svg = new StringBuilder("<polygon points=\"");
        for (Point point : points) {
            svg.append(point.x()).append(",").append(point.y()).append(" ");
        }
        svg.deleteCharAt(svg.length() - 1); // Supprimer l'espace final
        svg.append("\" fill=\"" + this.color + "\" stroke=\"black\"/>");
        return svg.toString();
    }

    /**
     * * Fonction qui retourne le centre du polygone
     * 
     * @return le centre du polygone en tant que Point
     */
    @Override
    public Point centre() {
        double sumX = 0, sumY = 0;
        for (Point point : points) {
            sumX += point.x();
            sumY += point.y();
        }
        return new Point(sumX / points.length, sumY / points.length);
    }

    /**
     * * Fonction qui retourne la description du polygone
     * 
     * @param ind l'indice de la forme
     * @return String, la description du polygone
     * @see IForme#description(int)
     */
    @Override
    public String description(int ind) {
        return "Polygone avec " + points.length + " sommets.";
    }

    /**
     * * Fonction qui retourne la hauteur du polygone
     * 
     * @return la hauteur du polygone en tant que double
     * @see IForme#hauteur()
     */
    @Override
    public double hauteur() {
        double minY = Double.MAX_VALUE, maxY = Double.MIN_VALUE;
        for (Point point : points) {
            minY = Math.min(minY, point.y());
            maxY = Math.max(maxY, point.y());
        }
        return maxY - minY;
    }

    /**
     * * Fonction qui retourne la largeur du polygone
     * 
     * @return la largeur du polygone en tant que double
     * @see IForme#largeur()
     */
    @Override
    public double largeur() {
        double minX = Double.MAX_VALUE, maxX = Double.MIN_VALUE;
        for (Point point : points) {
            minX = Math.min(minX, point.x());
            maxX = Math.max(maxX, point.x());
        }
        return maxX - minX;
    }

    /**
     * * Fonction qui deplace le polygone
     * 
     * @param x le deplacement en abscisse
     * @param y le deplacement en ordonnee
     * @see IForme#deplacer(double, double)
     */
    @Override
    public void deplacer(double x, double y) {
        for (Point point : points) {
            point.plus(x, y);
        }
    }

    /**
     * * Fonction qui duplique le polygone
     * 
     * @return le polygone duplique en tant que IForme
     * @see IForme#dupliquer()
     */
    @Override
    public IForme dupliquer() {
        double[] coords = new double[points.length * 2];
        for (int i = 0; i < points.length; i++) {
            coords[2 * i] = points[i].x();
            coords[2 * i + 1] = points[i].y();
        }
        return new Polygone(coords);
    }

    /**
     * * Fonction qui redimensionne le polygone
     * 
     * @param x le facteur de redimensionnement en abscisse
     * @param y le facteur de redimensionnement en ordonnee
     * @see IForme#redimensionner(double, double)
     */
    @Override
    public void redimensionner(double x, double y) {
        Point centre = centre();
        for (int i = 0; i < points.length; i++) {
            double newX = centre.x() + (points[i].x() - centre.x()) * x;
            double newY = centre.y() + (points[i].y() - centre.y()) * y;
            points[i] = new Point(newX, newY);
        }
    }

    /**
     * * Fonction qui colorie le polygone
     * 
     * @param couleur la couleur du polygone en tant que liste de String
     * @see IForme#colorier(String...)
     */
    @Override
    public void colorier(String... couleur) {
        this.color = couleur[0];
    }

    /**
     * * * Fonction qui tourne le polygone
     * 
     * @param angle l'angle de rotation en degres
     * @see IForme#tourner(int)
     */
    @Override
    public void tourner(int angle) {
        Point centre = centre();
        double rad = Math.toRadians(angle);
        double cosA = Math.cos(rad);
        double sinA = Math.sin(rad);

        for (int i = 0; i < points.length; i++) {
            double x = points[i].x() - centre.x();
            double y = points[i].y() - centre.y();

            double newX = x * cosA - y * sinA + centre.x();
            double newY = x * sinA + y * cosA + centre.y();

            points[i] = new Point(newX, newY);
        }

    }

    /**
     * * Fonction qui retourne la couleur du polygone
     * 
     * @return la couleur du polygone en tant que String
     * @see IForme#getColor()
     */
    @Override
    public String getColor() {
        return this.color;
    }

    /**
     * * Fonction qui aligne le polygone
     * 
     * @param alin  l'alignement
     * @param cible la valeur cible
     * @return le polygone aligne en tant que IForme
     * @see IForme#aligner(Alignement, double)
     */
    @Override
    public IForme aligner(Alignement alin, double cible) {
        return this;
    }

}