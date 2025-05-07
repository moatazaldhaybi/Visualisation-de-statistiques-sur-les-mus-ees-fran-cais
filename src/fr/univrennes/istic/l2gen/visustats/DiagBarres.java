package fr.univrennes.istic.l2gen.visustats;

import fr.univrennes.istic.l2gen.geometrie.Alignement;
import fr.univrennes.istic.l2gen.geometrie.Groupe;
import fr.univrennes.istic.l2gen.geometrie.IForme;
import fr.univrennes.istic.l2gen.geometrie.Point;
import fr.univrennes.istic.l2gen.geometrie.Rectangle;
import fr.univrennes.istic.l2gen.geometrie.Texte;

public class DiagBarres implements IDataVisualiseur {
    // constantes pour les rectangles et leurs legendes
    private final int H_RECT = 25;
    private final int L_RECT = 25;
    private final int X_RECT = 100;
    private final int Y_RECT = 100;
    private int DECAL_RECT = 0;
    private int DECAL_TI = 0;
    // constantes rec
    private final int X_REC = 100;
    private final int Y_REC = 100;
    private int DECAL_REC = 0;

    private String Titre;
    private String[] leg;
    private Double[] val;
    private int fontSize;

    private Groupe grpbarres;
    private Groupe grpRect;
    private Groupe grpTxt;
    private Groupe globalGrp;

    public DiagBarres(String titre, int valeur) {
        this.Titre = titre;
        this.fontSize = valeur;
        this.grpbarres = new Groupe();
        this.grpRect = new Groupe();
        this.grpTxt = new Groupe();
        this.globalGrp = new Groupe(grpbarres, grpRect, grpTxt);
    }

    @Override
    public IDataVisualiseur agencer() {
        Texte TitreDiag = new Texte(grpbarres.centre().x(), grpbarres.centre().y() - grpbarres.hauteur(), fontSize,
                Titre);
        TitreDiag.deplacer(0, -(TitreDiag.hauteur()));
        TitreDiag.colorier("white");

        globalGrp.ajouter(grpbarres);
        globalGrp.ajouter(grpRect);
        globalGrp.ajouter(grpTxt);
        globalGrp.ajouter(TitreDiag);
        

        return this;
    }

    @Override
    public IDataVisualiseur ajouterDonnees(String nomData, double... data) {
        double s = 0;
        for (double v : data) {
            s = s + v;
        }
        double[] valeur = data;
        for (int i = 0; i < valeur.length; i++) {
            valeur[i] = (valeur[i] / s) * 200;
        }

        Faisceau fai = new Faisceau(nomData, valeur);

        // modifier true en false pour colonne
        fai.agencer(X_REC, Y_REC + DECAL_REC - 50, 10, 0.1, true);
        fai.colorier("white");
        
        DECAL_REC= DECAL_REC+(int)fai.largeur()+50;
        
        //creation titre
        Texte legende = new Texte(X_REC+DECAL_TI, Y_REC+20 , fontSize, nomData) ;
        DECAL_TI=DECAL_TI+(int)fai.largeur()+50;
        legende.colorier("white");

        grpbarres.ajouter(fai) ; 
        grpTxt.ajouter(legende) ; 

        return this;

    }

    @Override
    public IDataVisualiseur legender(String... txt) {
        for (String t : txt) {
            
            Texte legend = new Texte(X_REC + DECAL_RECT, Y_RECT + 50, fontSize, t);
            legend.colorier("white");
            legend.deplacer((int) (legend.largeur() / 2), 0);
            DECAL_RECT = DECAL_RECT + (int) legend.largeur() + 20;
            Rectangle rec = new Rectangle(new Point(X_REC + DECAL_RECT, Y_RECT + 45), 30, 15);

            grpTxt.ajouter(legend);
            grpRect.ajouter(rec);
            DECAL_RECT = DECAL_RECT + 40;
        }
        return this;
    }

    @Override
    public void colorier(String... couleur) {
        for (IForme fai : grpbarres.getFormes()) {
            fai.colorier(couleur);
        }
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
        grpbarres.deplacer(dx, dy);
        grpRect.deplacer(dx, dy);
        grpTxt.deplacer(dx, dy);
    }

    @Override
    public IForme dupliquer() {
        DiagBarres diag = new DiagBarres(this.Titre, this.fontSize);
        diag.grpbarres = (Groupe) this.grpRect.dupliquer();
        diag.grpRect = (Groupe) this.grpRect.dupliquer();
        diag.grpTxt = (Groupe) this.grpTxt.dupliquer();
        return diag;
    }

    @Override
    public void redimensionner(double px, double py) {
        grpbarres.redimensionner(px, py);
        grpRect.redimensionner(px, py);
        grpTxt.redimensionner(px, py);
    }

    @Override
    public void tourner(int angle) {
        grpbarres.tourner(angle);
    }

    @Override
    public String getColor() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public IDataVisualiseur setOptions(String... options) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public IForme aligner(Alignement alin, double cible) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
