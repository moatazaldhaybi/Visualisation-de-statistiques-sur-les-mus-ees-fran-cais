package fr.univrennes.istic.l2gen.geometrie;

import java.util.ArrayList;
import java.util.List;

public class Ligne implements IForme {
    protected List<Point> sommet;
    protected String color;

    /**
     * Constructeur de la classe Ligne
     * 
     * @param points les points de la ligne en liste de doubles
     *               (x0, y0, x1, y1, ...)
     */
    public Ligne(double... points) {
        this.sommet = new ArrayList<>();
        for (int i = 0; i < points.length; i += 2) {
            sommet.add(new Point(points[i], points[i + 1]));
        }
    }

    /**
     * Constructeur de la classe Ligne
     * 
     * @param autre l'autre ligne à copier
     */
    public Ligne(Ligne autre) {
        this.sommet = new ArrayList<>();
        for (Point p : autre.sommet) {
            this.sommet.add(new Point(p.x(), p.y()));
        }
    }// DeepCopy

    /**
     * Ajouter un sommet à la ligne
     * 
     * @param p le point à ajouter
     */
    public void ajouterSommet(Point p) {
        sommet.add(p);
    }

    /**
     * Ajouter un sommet à la ligne
     * 
     * @param x0 le point en abscisse
     * @param y0 le point en ordonnée
     */
    public void ajouterSommet(double x0, double y0) {
        sommet.add(new Point(x0, y0));
    }

    /**
     * le centre de la ligne
     * 
     * @return le centre de la ligne en tant que Point
     */
    @Override
    public Point centre() {
        return new Point(largeur() / 2, hauteur() / 2);
    }

    /**
     * le hauteur de la ligne
     * 
     * @return le hauteur de la ligne en tant que double
     */
    @Override
    public double hauteur() {
        Point pointMax = sommet.get(0);
        Point pointMin = sommet.get(0);
        for (int i = 1; i < sommet.size(); i++) {
            if (sommet.get(i).y() >= pointMax.y()) {
                pointMax = sommet.get(i);
            }
            if (sommet.get(i).y() <= pointMin.y()) {
                pointMin = sommet.get(i);
            }
        }
        return pointMax.y() - pointMin.y();
    }

    /**
     * le largeur de la ligne
     * 
     * @return le largeur de la ligne en tant que double
     */
    @Override
    public double largeur() {
        Point pointMax = sommet.get(0);
        Point pointMin = sommet.get(0);
        for (int i = 1; i < sommet.size(); i++) {
            if (sommet.get(i).x() >= pointMax.x()) {
                pointMax = sommet.get(i);
            }
            if (sommet.get(i).x() <= pointMin.x()) {
                pointMin = sommet.get(i);
            }
        }
        return pointMax.x() - pointMin.x();
    }

    /**
     * description de la classe Ligne
     * 
     * @param i l'indentation
     * @return String, la description de la ligne en tant que String
     */
    @Override
    public String description(int i) {
        String res = "Ligne ";
        for (Point p : sommet) {
            res += p.toString();
        }
        return res;
    }

    /**
     * Fonction qui permet de retourner le SVG de la ligne
     * 
     * @return String, le SVG de la ligne
     */
    @Override
    public String enSVG() {
        StringBuilder svg = new StringBuilder();
        svg.append("<polyline points=\"");
        for (Point p : sommet) {
            svg.append(p.x()).append(",").append(p.y()).append(" ");
        }
        svg.append("\" fill=\"white\" stroke=\"black\"/>");
        return svg.toString();
    }

    /**
     * Le sommet de la ligne
     * 
     * @return le sommet de la ligne en tant que liste de Point
     */
    public List<Point> getSommets() {
        return sommet;
    }

    /**
     * Fonction qui permet de colorier la ligne
     * 
     * @param couleur la couleur de la ligne en liste de String
     */
    @Override
    public void colorier(String... couleur) {
        this.color = couleur[0];
    }

    /**
     * * Fonction qui permet de deplacer la ligne
     * 
     * @param dx le deplacement en abscisse
     * @param dy le deplacement en ordonnee
     */
    @Override
    public void deplacer(double dx, double dy) {
        for (Point point : sommet) {
            point.plus(dx, dy);
        }
    }

    /**
     * * Fonction qui duplique la ligne
     * 
     * @return la ligne duplique en tant que IForme
     */
    @Override
    public IForme dupliquer() {
        return new Ligne(this);
    }

    /**
     * * Fonction qui redimensionne la ligne
     * 
     * @param px le facteur de redimensionnement en abscisse
     * @param py le facteur de redimensionnement en ordonnee
     * @see IForme#redimensionner(double, double)
     */
    @Override
    public void redimensionner(double px, double py) {
        for (Point p : sommet) {
            p.plus((p.x() * px) - p.x(), (p.y() * py) - p.y());
        }
    }

    /**
     * * Fonction qui permet de tourner la ligne
     * 
     * @param angle l'angle de rotation
     */
    @Override
    public void tourner(int angle) {
        // Convertir l'angle en radians
        double radians = Math.toRadians(angle);

        // Calcul du centre de la ligne (ici, on peut prendre le centre de la bounding
        // box)
        Point centre = centre();

        // Rotation de chaque sommet autour du centre
        for (Point p : sommet) {
            double x = p.x() - centre.x(); // Décalage par rapport au centre
            double y = p.y() - centre.y();

            // Appliquer la transformation de rotation
            double xRot = x * Math.cos(radians) - y * Math.sin(radians);
            double yRot = x * Math.sin(radians) + y * Math.cos(radians);

            // Replacer le point après la rotation, en réajustant par rapport au centre
            p.plus(xRot - p.x(), yRot - p.y());
        }
    }

    /**
     * * Fonction qui permet de retourner la couleur de la ligne
     * 
     * @return la couleur de la ligne en tant que String
     */
    @Override
    public String getColor() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getColor'");
    }

    /**
     * * Fonction qui permet d'aligner la ligne
     * 
     * @param alin  le type d'alignement
     * @param cible la valeur cible de l'alignement
     * @return la ligne aligne en tant que IForme
     */
    @Override
    public IForme aligner(Alignement alin, double cible) {
        return this;
    }

}
