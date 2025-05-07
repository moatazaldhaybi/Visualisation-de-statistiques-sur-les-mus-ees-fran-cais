package fr.univrennes.istic.l2gen.geometrie;

public class Texte implements IForme {
    private Point centre;
    private int fontSize;
    private String txt;

    public void setCentre(Point centre) {
        this.centre = centre;
    }

    private String color;
    private double angle;

    public Texte(double x, double y, int font, String txt) {
        this.centre = new Point(x, y);
        this.fontSize = font;
        this.txt = txt;
        this.color = "black";
    }

    @Override
    public String enSVG() {
        String result = "<text x=\"" + this.centre.x() + "\" y=\"" + this.centre.y() + "\" font-size=\"" + this.fontSize
                + "\" text-anchor=\"middle\" fill=\"" + this.color + "\" stroke=\"black\" ";
        if (this.angle != 0) {
            result += "transform=\"rotate(" + this.angle + " " + this.centre.x() + " " + this.centre.y() + ")\"";
        }
        return result + ">" + this.txt + "</text>";
    }

    @Override
    public Point centre() {
        return this.centre;
    }

    @Override
    public String description(int indent) {
        String result = "";
        for (int i = 0; i < indent; i++) {
            result += "  ";
        }
        result += "Texte Centre=" + (int) this.centre.x() + "," + (int) this.centre.y() + " Font=" + this.fontSize
                + " Texte=" + this.txt + " Couleur=" + this.color + " Angle=" + this.angle;
        return result;
    }

    @Override
    public void tourner(int angle) {
        double finalAngle = this.angle + angle;
        if (finalAngle > 360) {
            this.angle = finalAngle - 360;
        } else {
            this.angle = finalAngle;
        }
    }

    @Override
    public double hauteur() {
        return 4 * this.fontSize / 3;
    }

    @Override
    public double largeur() {
        return this.txt.length() * this.fontSize / 1.9;
    }

    @Override
    public void deplacer(double dx, double dy) {
        this.centre = this.centre.plus(dx, dy);
    }

    @Override
    public IForme dupliquer() {
        Texte result = new Texte(this.centre.x(), this.centre.y(), this.fontSize, this.txt);
        result.colorier(this.color);
        result.tourner((int) this.angle);
        return result;
    }

    @Override
    public void redimensionner(double px, double py) {
        this.fontSize *= Math.min(px, py);
        if (this.fontSize < 10) { // Définit une taille minimale
            this.fontSize += 50;
        }
    }

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

    @Override
    public void colorier(String... couleur) {
        this.color = couleur[0];
    }

    @Override
    public String getColor() {
        return this.color;
    }

}
