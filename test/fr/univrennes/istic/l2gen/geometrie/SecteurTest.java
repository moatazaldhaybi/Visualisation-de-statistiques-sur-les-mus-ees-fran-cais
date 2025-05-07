package test.fr.univrennes.istic.l2gen.geometrie;

import static org.junit.Assert.*;
import org.junit.*;
import fr.univrennes.istic.l2gen.geometrie.*;

public class SecteurTest {
    Secteur sect;

    @Before
    public void setUp() {
        sect = new Secteur(new Point(256, 256), 128, 0, 60);
    }

    @Test
    public void testDescription() {
        assertEquals("Secteur centre=256,256 r=128.0 Angle=0.0 Arc=60.0", sect.description(0));
    }

    @Test
    public void testEnSVG() {
        assertEquals("<path d=\"M256 256 L 384 256 A 128.0 128.0 0 0,1 320 366 Z\" fill=\"white\" stroke=\"black\" />",
                sect.enSVG());
    }

    @Test
    public void testEnSVGRotation() {
        sect.tourner(90);
        assertEquals("<path d=\"M256 256 L 384 256 A 128.0 128.0 0 0,1 320 366 Z\" fill=\"white\" stroke=\"black\" transform=\"rotate(90.0 256.0 256.0)\"/>",
                sect.enSVG());
    }

    @Test
    public void testEnSVGArcLarge(){
        Secteur s = new Secteur(new Point(100, 100), 180, 0, 190) ; 
        assertEquals("<path d=\"M100 100 L 280 100 A 180.0 180.0 0 1,1 -77 68 Z\" fill=\"white\" stroke=\"black\" />", s.enSVG()) ;
    }

    @Test
    public void testColorier() {
        sect.colorier("purple");
        assertEquals("<path d=\"M256 256 L 384 256 A 128.0 128.0 0 0,1 320 366 Z\" fill=\"purple\" stroke=\"black\" />",
                sect.enSVG());
    }

    @Test
    public void testAligner(){
        sect.aligner(Alignement.DROITE, 500);
        assertEquals("<path d=\"M436 256 L 564 256 A 128.0 128.0 0 0,1 500 366 Z\" fill=\"white\" stroke=\"black\" />", sect.enSVG()) ;
        sect = new Secteur(new Point(256, 256), 128, 0, 60);
        sect.aligner(Alignement.GAUCHE, 500);
        assertEquals("<path d=\"M564 256 L 692 256 A 128.0 128.0 0 0,1 628 366 Z\" fill=\"white\" stroke=\"black\" />", sect.enSVG()) ;
        sect = new Secteur(new Point(256, 256), 128, 0, 60);
        sect.aligner(Alignement.BAS, 500);
        assertEquals("<path d=\"M256 436 L 384 436 A 128.0 128.0 0 0,1 320 546 Z\" fill=\"white\" stroke=\"black\" />", sect.enSVG()) ;
        sect = new Secteur(new Point(256, 256), 128, 0, 60);
        sect.aligner(Alignement.HAUT, 500);
        assertEquals("<path d=\"M256 564 L 384 564 A 128.0 128.0 0 0,1 320 674 Z\" fill=\"white\" stroke=\"black\" />", sect.enSVG()) ; 
    }

    @Test
    public void testGetAngle() {
        assertEquals(0.0, sect.getAngle(), 0.0);
    }

    @Test
    public void testGetArc() {
        assertEquals(60.0, sect.getArc(), 0.0);
    }

    @Test
    public void testCentre() {
        assertEquals(sect.centre().x(), 256.0, 0.0);
        assertEquals(sect.centre().y(), 256.0, 0.0);
    }

    @Test
    public void testHauteur() {
        assertEquals(sect.hauteur(), 128.0, 0.0);
    }

    @Test
    public void testLargeur() {
        assertEquals(sect.hauteur(), 128.0, 0.0);
    }

    @Test
    public void testDeplacerDefault() {
        sect.deplacer(10, 10);
        Point after = sect.centre();
        assertEquals(after.x(), 266, 0.0) ;
        assertEquals(after.y(), 266, 0.0) ;
    }

    @Test
    public void testDupliquer() {
        Secteur duplicate = (Secteur) sect.dupliquer();
        assertEquals(sect.enSVG(), duplicate.enSVG());
    }

    @Test
    public void testRedimensionner() {
        double beforeHeight = sect.hauteur();
        double beforeWidth = sect.largeur();
        sect.redimensionner(0.5, 0.5);
        assertEquals(sect.hauteur(), beforeHeight * 0.5, 0.0);
        assertEquals(sect.largeur(), beforeWidth * 0.5, 0.0);
    }
}
