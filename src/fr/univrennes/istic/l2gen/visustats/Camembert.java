package fr.univrennes.istic.l2gen.visustats;

import fr.univrennes.istic.l2gen.geometrie.*;

public class Camembert implements IForme {
    private Point centre;
    private double rayon;
    private Groupe grpSect;
    private double angle = 0.0;

    public Camembert(double x, double y, double rayon) {
        this.centre = new Point(x, y);
        this.rayon = rayon;
        this.grpSect = new Groupe();
    }

    public Camembert(Point centre, double rayon) {
        this.centre = centre;
        this.rayon = rayon;
        this.grpSect = new Groupe();
    }

    /**
     * Ajoute un Secteur au camembert
     * 
     * @param color       la couleur du secteur
     * @param pourcentage la "taille" du secteur : 1.0 = camembert entier, 0.50
     *                    = moitiee du camembert
     * @return un camembert avec l'ajout du secteur.
     */
    public Camembert ajouterSecteur(String color, double pourcentage) {
        double arc = pourcentage * 360.0;
        if (arc == 360) {
            Cercle crl = new Cercle(this.centre, this.rayon);
            grpSect.ajouter(crl);
        } else {
            Secteur sect = new Secteur(this.centre, this.rayon, angle, arc);
            sect.colorier(color);
            angle += sect.getArc();
            grpSect.ajouter(sect);
        }

        return this;
    }

    @Override
    public void colorier(String... couleur) {
        grpSect.colorier(couleur);
    }

    @Override
    public String enSVG() {
        return grpSect.enSVG();
    }

    @Override
    public Point centre() {
        return this.centre;
    }

    @Override
    public String description(int indent) {
        return grpSect.description(indent);
    }

    @Override
    public double hauteur() {
        return this.rayon;
    }

    @Override
    public double largeur() {
        return this.rayon;
    }

    @Override
    public void deplacer(double dx, double dy) {
        this.centre = this.centre.plus(dx, dy);
        // grpSect.deplacer(dx, dy);
    }

    @Override
    public IForme dupliquer() {
        Camembert result = new Camembert(this.centre, this.rayon);
        result.grpSect = (Groupe) this.grpSect.dupliquer();
        return result;
    }

    @Override
    public void redimensionner(double px, double py) {
        this.rayon = this.rayon * px;
        grpSect.redimensionner(px, py);
    }

    @Override
    public void tourner(int angle) {
        grpSect.tourner(angle);
    }

    @Override
    public String getColor() {
        return "this.color";
    }

    @Override
    public IForme aligner(Alignement alin, double cible) {
        return this;
    }

}
