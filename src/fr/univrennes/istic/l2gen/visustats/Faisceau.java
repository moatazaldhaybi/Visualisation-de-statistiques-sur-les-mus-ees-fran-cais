package fr.univrennes.istic.l2gen.visustats;

import fr.univrennes.istic.l2gen.geometrie.Alignement;
import fr.univrennes.istic.l2gen.geometrie.Groupe;
import fr.univrennes.istic.l2gen.geometrie.IForme;
import fr.univrennes.istic.l2gen.geometrie.Point;
import fr.univrennes.istic.l2gen.geometrie.Rectangle;
import fr.univrennes.istic.l2gen.geometrie.Texte;
import java.util.ArrayList;
import java.util.List;

//import org.w3c.dom.css.Rect;

public class Faisceau extends Groupe {
    private final String titre;
    private final List<Double> valeurs;

    public Faisceau(String titre, double... valeur) {
        this.titre = titre;
        this.valeurs = new ArrayList<>();
        // super(new Texte(0, 0, 12, titre));
        // Groupe g = new Groupe();
        for (double v : valeur) {
            if (v <= 0) {
                throw new IllegalArgumentException(
                        "Erreur : La largeur et la hauteur doivent être strictement positives.");
            }
            this.valeurs.add(v);
            ajouter(new Rectangle(new Point(30, 0), 100, v));
            // Rectangle r = new Rectangle(0, 0, 10,v);
            // g.ajouter(r);

        }
        // ajouter(g);
    }

    public void agencer(double axeX, double axeY, double largeur, double echelle, boolean verticalement) {
        if (largeur <= 0 || echelle <= 0) {
            throw new IllegalArgumentException("Erreur : largeur et échelle doivent être strictement positives.");
        }

        // redimensionner(largeur, echelle);
        Texte t = new Texte(axeX - 10, (axeY + 1 / 2 * largeur()), (int) echelle + 50, titre);
        if (verticalement) {
            alignerElements(Alignement.GAUCHE, axeY);
            empilerElements(Alignement.HAUT, axeX, 0);
            t.setCentre(new Point(axeY, axeY * 2 - 2 * axeX));
            // ajouter(t);
        } else {
            alignerElements(Alignement.BAS, axeX);
            empilerElements(Alignement.GAUCHE, axeY, largeur);
            // ajouter(t);
        }

        // Texte t = new Texte( axeX - 10, (axeY + 1 / 2 * largeur()), (int) echelle+50,
        // titre);

    }

    @Override
    public void colorier(String... couleurs) {
        if (couleurs.length == 0)
            return;

        int i = 0;
        for (IForme forme : this.getFormes()) {
            forme.colorier(couleurs[i % couleurs.length]);
            i++;
        }
    }

    @Override
    public IForme dupliquer() {
        ArrayList<IForme> copie = new ArrayList<>();
        for (IForme forme : this.getFormes()) {
            copie.add(forme.dupliquer());
        }
        return new Groupe(copie.toArray(new IForme[0]));
    }

    @Override
    public String enSVG() {
        String svgContent = super.enSVG();
        svgContent = "<!-- Faisceau -->\n" + svgContent;
        return svgContent;
    }

    public static <T> T getElement(Iterable<T> iterable, int index) {
        int i = 0;
        for (T element : iterable) {
            if (i == index) {
                return element;
            }
            i++;
        }
        throw new IndexOutOfBoundsException("Index trop grand !");
    }

}
