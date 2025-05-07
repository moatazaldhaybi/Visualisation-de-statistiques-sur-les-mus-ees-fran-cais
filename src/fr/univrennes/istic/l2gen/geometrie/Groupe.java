package fr.univrennes.istic.l2gen.geometrie;

import java.util.ArrayList;

import fr.univrennes.istic.l2gen.visustats.Camembert;

public class Groupe implements IForme {
    private ArrayList<IForme> grp;

    /**
     * Constructeur de la classe Groupe
     * 
     * @param formes les formes à ajouter au groupe en tant que tableau d'IForme
     */
    public Groupe(IForme... formes) {
        this.grp = new ArrayList<IForme>();
        for (IForme f : formes) {
            grp.add(f);
        }
    }

    /**
     * Tourne chaque forme du groupe d'un certain angle
     * 
     * @param angle l'angle de rotation en degrés
     */
    @Override
    public void tourner(int angle) {
        grp.forEach((v) -> v.tourner(angle));
    }

    /**
     * * Retourne la représentation SVG du groupe de formes
     * 
     * @return la chaîne de caractères représentant le groupe en SVG
     */
    @Override
    public String enSVG() {

        String result = "<g>\n";
        for (int i = 0; i < grp.size(); i++) {
            result += grp.get(i).enSVG() + "\n";
        }
        return result + "</g>";
    }

    /**
     * * la liste des formes du groupe
     * 
     * @return un Iterable<IForme> contenant toutes les formes du groupe
     */
    public Iterable<IForme> getFormes() {
        return grp;

    }

    /**
     * le nombre d'éléments dans le groupe
     * 
     * @return le nombre d'éléments dans le groupe
     */
    public int nombreElements() {
        return grp.size();
    }

    /**
     * * Fonction qui deplace chaque forme du groupe d'un certain vecteur
     * 
     * @param dx le déplacement en x
     * @param dy le déplacement en y
     */
    @Override
    public void deplacer(double dx, double dy) {
        grp.forEach(v -> {
            v.deplacer(dx, dy);
        });
    }

    /**
     * * Fonction qui duplique le groupe de formes
     * 
     * @return le groupe dupliqué en tant que IForme
     */
    @Override
    public IForme dupliquer() {
        IForme[] lst = new IForme[grp.size()];
        for (int i = 0; i < grp.size(); i++) {
            lst[i] = grp.get(i).dupliquer();
        }

        return new Groupe(lst);
    }

    /**
     * Ajoute une forme au groupe.
     * 
     * @param f une forme IForme
     * @return le groupe mis a jour
     */
    public Groupe ajouter(IForme f) {
        grp.add(f);
        return this;
    }

    /**
     * * * Fonction qui retourne le centre du groupe de formes
     * 
     * @return le centre du groupe de formes
     */
    @Override
    public Point centre() {
        double moy_x = 0;
        double moy_y = 0;
        for (int i = 0; i < grp.size(); i++) {
            moy_x += grp.get(i).centre().x();
            moy_y += grp.get(i).centre().y();
        }
        return new Point(moy_x / grp.size(), moy_y / grp.size());
    }

    /**
     * * * Fonction qui retourne la hauteur du groupe de formes
     * 
     * @return la hauteur du groupe de formes
     */
    @Override
    public double hauteur() {

        double heightMax = grp.get(0).centre().y() + (grp.get(0).hauteur() / 2);
        double heightMin = grp.get(0).centre().y() - (grp.get(0).hauteur() / 2);

        for (int i = 1; i < grp.size(); i++) {
            double yCentre = grp.get(i).centre().y();
            double demiHauteur = grp.get(i).hauteur() / 2;

            double yMax = yCentre + demiHauteur;
            double yMin = yCentre - demiHauteur;

            if (yMax > heightMax) {
                heightMax = yMax;
            }
            if (yMin < heightMin) {
                heightMin = yMin;
            }
        }

        return heightMax - heightMin;
    }

    /**
     * Fonction qui retourne la largeur du groupe de formes
     * 
     * @return la largeur du groupe de formes
     */
    @Override
    public double largeur() {
        double widthMax = grp.get(0).centre().x() + (grp.get(0).largeur() / 2);
        double widthMin = grp.get(0).centre().x() - (grp.get(0).largeur() / 2);

        for (int i = 1; i < grp.size(); i++) {
            double xCentre = grp.get(i).centre().x();
            double halfWidth = grp.get(i).largeur() / 2;

            double xMax = xCentre + halfWidth;
            double xMin = xCentre - halfWidth;

            // Mise à jour des valeurs max et min
            if (xMax > widthMax) {
                widthMax = xMax;
            }
            if (xMin < widthMin) {
                widthMin = xMin;
            }
        }

        return widthMax - widthMin;
    }

    /**
     * * Fonction qui retourne la description du groupe de formes
     * 
     * @param indent l'indentation
     * @return la description du groupe de formes
     */
    @Override
    public String description(int indent) {
        String result = "";
        for (int i = 0; i < indent; i++) {
            result += "  ";
        }
        result += "Groupe\n";
        for (IForme f : grp) {
            result += f.description(indent + 1) + "\n";
        }
        return result;

    }

    /**
     * * Fonction qui redimensionne chaque forme du groupe d'un certain facteur
     * 
     * @param px le facteur de redimensionnement en x
     * @param py le facteur de redimensionnement en y
     */
    @Override
    public void redimensionner(double px, double py) {
        grp.forEach(v -> {
            v.redimensionner(px, py);
        });
    }

    /**
     * * Fonction qui colorie chaque forme du groupe d'une certaine couleur
     * 
     * @param couleur la couleur de la forme
     */
    @Override
    public void colorier(String... couleur) {
        int i = 0;
        while (i < grp.size()) {
            for (String v : couleur) {
                if (grp.get(i).getClass().equals(Camembert.class)) {
                    grp.get(i).colorier(couleur);
                } else {
                    grp.get(i).colorier(v);
                }
                i++;

                if (i >= grp.size()) {
                    break;
                }
            }
        }
    }

    /*
     * *
     * Vide ce groupe de tous ses elements
     * 
     * @return IForme : ce groupe qui ne contient plus aucun element
     */
    public IForme vider() {
        grp.removeAll(grp);
        return this;
    }

    /*
     * *
     * 
     * @param alignement direction HAUT , BAS , DROITE , GAUCHE tc .
     * 
     * @param cible ligne horizontale ou verticale sur laquelle
     * doivent s ’ aligner chacun des elements du groupe
     * 
     * @return IForme
     */

    public IForme alignerElements(Alignement alignement, double cible) {
        if (alignement == Alignement.HAUT) {
            for (int i = 0; i < grp.size(); i++) {
                grp.get(i).deplacer(0, 0 - grp.get(i).centre().y() + cible + (grp.get(i).hauteur() / 2));
            }
        } else if (alignement == Alignement.BAS) {
            for (int i = 0; i < grp.size(); i++) {

                grp.get(i).deplacer(0, 0 - grp.get(i).centre().y() + cible - (grp.get(i).hauteur() / 2));
            }
        } else if (alignement == Alignement.DROITE) {
            for (int i = 0; i < grp.size(); i++) {
                grp.get(i).deplacer(0 - grp.get(i).centre().x() + cible - (grp.get(i).largeur() / 2), 0);
            }
        } else {
            for (int i = 0; i < grp.size(); i++) {

                grp.get(i).deplacer(0 - grp.get(i).centre().x() + cible + (grp.get(i).largeur() / 2), 0);
            }
        }

        return this;
    }
    /*
     * *
     * 
     * @param alignement direction HAUT , BAS , DROITE , GAUCHE tc .
     * 
     * @param cible ligne horizontale ou verticale sur laquelle
     * doivent s ’ empiler chacun des elements du groupe
     * 
     * @param separation : distance entre chaque element empile
     * 
     * @return IForme
     */

    /*
     * public IForme empilerElements(Alignement alignement, double cible, double
     * separation) {
     * if (alignement == Alignement.HAUT || alignement == Alignement.BAS) {
     * double cib = cible;
     * for (int i = 0; i < grp.size(); i++) {
     * grp.get(i).deplacer(cible, cib);
     * cib = cib + grp.get(i).hauteur() + separation;
     * }
     * } else {
     * double cib = cible;
     * for (int i = 0; i < grp.size(); i++) {
     * grp.get(i).deplacer(cib, cible);
     * cib = cib + grp.get(i).largeur() + separation;
     * }
     * }
     * return this;
     * }
     */

    /*
     * *
     * 
     * @param alignement direction HAUT , BAS , DROITE , GAUCHE tc .
     * 
     * @param cible ligne horizontale ou verticale sur laquelle
     * doivent s ’ empiler chacun des elements du groupe
     * 
     * @param separation : distance entre chaque element empile
     * 
     * @return IForme
     */
    public IForme empilerElements(Alignement alignement, double cible, double separation) {
        double x = 20.0;
        double y = 0.0;
        double position = cible;

        for (int i = 0; i < grp.size(); i++) {
            IForme forme = grp.get(i);

            if (alignement == Alignement.HAUT) {
                forme.deplacer(0, position - forme.hauteur() / 2);
                position -= (forme.hauteur() + separation);
            } else if (alignement == Alignement.BAS) {
                forme.deplacer(0, position + forme.hauteur() / 2);
                position += (forme.hauteur() + separation);
            } else if (alignement == Alignement.GAUCHE) {
                forme.deplacer(position + forme.largeur() / 2, 0);
                position += (forme.largeur() + separation);
            } else if (alignement == Alignement.DROITE) {
                forme.deplacer(position - forme.largeur() / 2, 0);
                position -= (forme.largeur() + separation);
            }
        }
        return this;
    }

    /**
     * * Fonction qui retourne la couleur du groupe de formes
     * 
     * @return la couleur du groupe de formes
     */
    @Override
    public String getColor() {
        return "this.color";
    }

    /**
     * Fonction qui alligne le groupe de formes par rapport a un alignement et une
     * 
     * @param cible la cible
     * @param alin  l'alignement
     * @return le groupe de formes aligné
     */
    @Override
    public IForme aligner(Alignement alin, double cible) {
        return this;
    }

}
