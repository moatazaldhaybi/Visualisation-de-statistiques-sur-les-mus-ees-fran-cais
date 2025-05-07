package test.fr.univrennes.istic.l2gen.geometrie;

import static org.junit.Assert.*;
import org.junit.*;
import fr.univrennes.istic.l2gen.geometrie.*;

public class RectangleTest {
    Rectangle rect;

    @Before
    public void setUp() {
        rect = new Rectangle(256, 256, 256, 128);
    }

    // TEST DESCRIPTION

    @Test
    public void testDescription() {
        assertEquals("Rectangle Centre=256,256 L=256.0 H=128.0 Couleur=white Angle=0.0", rect.description(0));
    }

    // TEST enSVG

    @Test
    public void testEnSVG() {
        assertEquals("<rect x=\"128.0\" y=\"192.0\" width=\"256.0\" height=\"128.0\" fill=\"white\" stroke=\"black\" />",
                rect.enSVG());
    }

    @Test
    public void testEnSVGAngle() {
        rect.tourner(90);
        assertEquals("<rect x=\"128.0\" y=\"192.0\" width=\"256.0\" height=\"128.0\" fill=\"white\" stroke=\"black\" transform=\"rotate(90.0 256.0 256.0)\"/>",
                rect.enSVG());
    }

    //TEST ALIGNER

    @Test
    public void testAligner(){
        rect.aligner(Alignement.DROITE, 256);
        assertEquals("<rect x=\"0.0\" y=\"192.0\" width=\"256.0\" height=\"128.0\" fill=\"white\" stroke=\"black\" />", rect.enSVG()) ;
        rect = new Rectangle(256, 256, 256, 128);
        rect.aligner(Alignement.GAUCHE, 256);
        assertEquals("<rect x=\"256.0\" y=\"192.0\" width=\"256.0\" height=\"128.0\" fill=\"white\" stroke=\"black\" />", rect.enSVG()) ;
        rect = new Rectangle(256, 256, 256, 128);
        rect.aligner(Alignement.BAS, 256);
        assertEquals("<rect x=\"128.0\" y=\"128.0\" width=\"256.0\" height=\"128.0\" fill=\"white\" stroke=\"black\" />", rect.enSVG()) ;
        rect = new Rectangle(256, 256, 256, 128);
        rect.aligner(Alignement.HAUT, 256);
        assertEquals("<rect x=\"128.0\" y=\"256.0\" width=\"256.0\" height=\"128.0\" fill=\"white\" stroke=\"black\" />", rect.enSVG()) ; 
    }

    // TEST DEPLACER

    @Test
    public void testDeplacerDefault() {
        rect.deplacer(10, 10);
        Point after = rect.centre();
        assertEquals(after.x(), 266, 0.0) ;
        assertEquals(after.y(), 266, 0.0) ;
    }

    // TEST COLORIER

    @Test
    public void testColorier() {
        rect.colorier("purple");
        assertEquals(rect.description(0), "Rectangle Centre=256,256 L=256.0 H=128.0 Couleur=purple Angle=0.0") ; 
    }

    // TEST DUPLIQUER

    @Test
    public void testDupliquer() {
        Rectangle duplicate = (Rectangle) rect.dupliquer();
        assertEquals(rect.enSVG(), duplicate.enSVG());
    }

    @Test
    public void testRedimensionner() {
        double beforeHeight = rect.hauteur();
        double beforeWidth = rect.largeur();
        rect.redimensionner(0.5, 0.5);
        assertEquals(rect.hauteur(), beforeHeight * 0.5, 0.0);
        assertEquals(rect.largeur(), beforeWidth * 0.5, 0.0);
    }
}
