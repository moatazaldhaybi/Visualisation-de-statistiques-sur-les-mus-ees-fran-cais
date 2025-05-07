package fr.univrennes.istic.l2gen.visustats;

import fr.univrennes.istic.l2gen.geometrie.*;

public class DiagCamembert implements IDataVisualiseur {
    // CONSTANTES
    // constantes pour la fenetre, utilise pour gerer la legende
    private final int SCREEN_WIDTH = 1500;
    // constantes pour les camemberts et leurs titres
    private final int RAYON = 100;
    private final int X_CAM = 100;
    private final int Y_CAM = 300;
    private final int DECAL_CAM = 250;
    // constantes pour les rectangles et leurs legendes
    private final int H_RECT = 25;
    private final int L_RECT = 50;
    private final int X_RECT = -400;
    private final int Y_RECT = 515;
    private final int DECAL_RECT = 180;

    // AUTRE
    private int fontSize;
    private String nomDiag;
    // decalage manuel sans Groupe.aligner(), Plus simple
    private int decalage_cam = 0;
    private int decalage_rect_x = 0;
    private int decalage_rect_y = 0;
    // On peut pas avoir un seul grp a cause de colorier()
    private Groupe grpCam;
    private Groupe grpRect;
    private Groupe grpTxt;
    Groupe globalGrp;

    public DiagCamembert(String nomDiag, int fontSize) {
        this.fontSize = fontSize;
        this.nomDiag = nomDiag;
        this.grpCam = new Groupe();
        this.grpRect = new Groupe();
        this.grpTxt = new Groupe();
        this.globalGrp = new Groupe();
    }

    @Override
    public void colorier(String... couleur) {
        grpCam.colorier(couleur);
        grpRect.colorier(couleur);
    }

    @Override
    public String enSVG() {
        return globalGrp.enSVG();
    }

    @Override
    public Point centre() {
        return globalGrp.centre();
    }

    @Override
    public String description(int indent) {
        return globalGrp.enSVG();
    }

    @Override
    public double hauteur() {
        return globalGrp.hauteur();
    }

    @Override
    public double largeur() {
        return globalGrp.largeur();
    }

    @Override
    public void deplacer(double dx, double dy) {
        grpCam.deplacer(dx, dy);
        grpRect.deplacer(dx, dy);
        grpTxt.deplacer(dx, dy);
    }

    @Override
    public IForme dupliquer() {
        DiagCamembert diag = new DiagCamembert(this.nomDiag, this.fontSize);
        diag.grpCam = (Groupe) this.grpCam.dupliquer();
        diag.grpRect = (Groupe) this.grpRect.dupliquer();
        diag.grpTxt = (Groupe) this.grpTxt.dupliquer();
        return diag;
    }

    @Override
    public void redimensionner(double px, double py) {
        grpCam.redimensionner(px, py);
        grpRect.redimensionner(px, py);
        grpTxt.redimensionner(px, py);
    }

    @Override
    public void tourner(int angle) {
        grpCam.tourner(angle);
    }

    @Override
    public IDataVisualiseur agencer() {
        Texte TitreDiag = new Texte(grpCam.centre().x(), grpCam.centre().y() - grpCam.hauteur(), fontSize, nomDiag);
        TitreDiag.deplacer(0, -(TitreDiag.hauteur()));
        TitreDiag.colorier("white");

        globalGrp.ajouter(grpCam);
        globalGrp.ajouter(grpRect);
        globalGrp.ajouter(grpTxt);
        globalGrp.ajouter(TitreDiag);

        return this;
    }

    @Override
    public IDataVisualiseur ajouterDonnees(String nomData, double... data) {
        // Creation camembert
        Camembert cam = new Camembert(X_CAM + decalage_cam, Y_CAM, RAYON);
        for (double d : data) {
            cam.ajouterSecteur("white", d);
        }
        // creation legende du camembert
        Texte legende = new Texte(X_CAM + decalage_cam, Y_CAM + RAYON * 1.25, fontSize, nomData);
        legende.colorier("white");

        grpCam.ajouter(cam);
        grpTxt.ajouter(legende);

        decalage_cam += DECAL_CAM;

        return this;
    }

    @Override
    public IDataVisualiseur legender(String... txt) {
        for (String t : txt) {

            Texte l = new Texte(X_RECT + decalage_rect_x + L_RECT, Y_RECT + H_RECT / 2 + decalage_rect_y, fontSize, t);
            l.colorier("white");
            l.deplacer((int) (l.largeur() / 2), 0);

            Rectangle r = new Rectangle(X_RECT + decalage_rect_x, Y_RECT + decalage_rect_y, L_RECT, H_RECT);

            if (decalage_rect_x + L_RECT + l.largeur() * 2 > SCREEN_WIDTH) {
                // Deplace le rect,txt
                r.deplacer(-decalage_rect_x, H_RECT + 10);
                l.deplacer(-decalage_rect_x, H_RECT + 10);
                // Actualise le decalage
                decalage_rect_x = 0;
                decalage_rect_y += H_RECT + 10;

            }

            grpTxt.ajouter(l);
            grpRect.ajouter(r);

            decalage_rect_x += DECAL_RECT + l.largeur();
        }

        return this;
    }

    @Override
    public IDataVisualiseur setOptions(String... options) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Aucune info sur cette fonction");
    }

    @Override
    public String getColor() {
        return "this.color";
    }

    @Override
    public IForme aligner(Alignement alin, double cible){
        return this;
    }

}
