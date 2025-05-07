package fr.univrennes.istic.l2gen.geometrie;

public class Point {
    private double x;
    private double y;

    /**
     * Constructeur de la classe Point
     * 
     * @param x le point en abscisse
     * @param y le point en ordonnee
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * true si le point est égal à l'objet
     * 
     * @param obj Object un objet quelconque
     * @return boolean
     */
    public boolean equals(Object obj) {
        if (obj.getClass().equals(getClass())) {
            Point other = (Point) obj;
            return Double.compare(this.x, other.x) == 0 && Double.compare(this.y, other.y) == 0;
        } else {
            return false;
        }
    }

    /**
     * modifie les coordonnees du point
     * 
     * @param pt un Point
     * @return Point
     */
    public Point plus(Point pt) {
        this.x += pt.x;
        this.y += pt.y;
        return this;
    }

    /**
     * modifie les coordonnees du point
     * 
     * @param x : la coordonnee x du point
     * @param y : la coordonnee y du point
     * @return Point
     */
    public Point plus(double x, double y) {

        this.x += x;
        this.y += y;
        return this;

    }

    /**
     * la coordonnee x du point
     * 
     * @return double
     */
    public double x() {
        return this.x;
    }

    /**
     * la coordonnee y du point
     * 
     * @return double
     */
    public double y() {
        return this.y;
    }
}
