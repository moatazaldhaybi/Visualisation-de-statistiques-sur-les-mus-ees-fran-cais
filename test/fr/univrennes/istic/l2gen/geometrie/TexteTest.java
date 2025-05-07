package test.fr.univrennes.istic.l2gen.geometrie ;

import static org.junit.Assert.*;
import org.junit.*;
import fr.univrennes.istic.l2gen.geometrie.*;


public class TexteTest {
    Texte txt ; 

    @Before
    public void setUp(){
        txt = new Texte(192.0, 128.0, 64, "Istic L2GEN") ; 
    }

    //TEST DESCRITPION

    @Test
    public void testDescription() {
        assertEquals("Texte Centre=192,128 Font=64 Texte=Istic L2GEN Couleur=black Angle=0.0", txt.description(0)) ;
    }

    //TEST ENSVG

    @Test
    public void testEnSVG() {  
        assertEquals("<text x=\"192.0\" y=\"128.0\" font-size=\"64\" text-anchor=\"middle\" fill=\"black\" stroke=\"black\" >Istic L2GEN</text>",txt.enSVG());
    }

    @Test 
    public void testEnSVGAngle(){
        txt.tourner(45);
        assertEquals("<text x=\"192.0\" y=\"128.0\" font-size=\"64\" text-anchor=\"middle\" fill=\"black\" stroke=\"black\" transform=\"rotate(45.0 192.0 128.0)\">Istic L2GEN</text>",txt.enSVG());

    }

    //TEST ALIGNER

    @Test
    public void testAligner(){
        txt.aligner(Alignement.DROITE, 256);
        assertEquals("<text x=\"70.73684210526315\" y=\"128.0\" font-size=\"64\" text-anchor=\"middle\" fill=\"black\" stroke=\"black\" >Istic L2GEN</text>", txt.enSVG()) ;
        txt = new Texte(192.0, 128.0, 64, "Istic L2GEN") ; 
        txt.aligner(Alignement.GAUCHE, 256);
        assertEquals("<text x=\"441.2631578947369\" y=\"128.0\" font-size=\"64\" text-anchor=\"middle\" fill=\"black\" stroke=\"black\" >Istic L2GEN</text>", txt.enSVG()) ;
        txt = new Texte(192.0, 128.0, 64, "Istic L2GEN") ; 
        txt.aligner(Alignement.BAS, 256);
        assertEquals("<text x=\"192.0\" y=\"213.5\" font-size=\"64\" text-anchor=\"middle\" fill=\"black\" stroke=\"black\" >Istic L2GEN</text>", txt.enSVG()) ;
        txt = new Texte(192.0, 128.0, 64, "Istic L2GEN") ; 
        txt.aligner(Alignement.HAUT, 256);
        assertEquals("<text x=\"192.0\" y=\"298.5\" font-size=\"64\" text-anchor=\"middle\" fill=\"black\" stroke=\"black\" >Istic L2GEN</text>", txt.enSVG()) ; 
    }

    //TEST HAUTEUR

    @Test 
    public void testHauteur(){
        assertEquals(txt.hauteur(), 85.0, 0.1) ;
    }

    //TEST LARGEUR

    @Test
    public void testLargeur(){
        assertEquals(txt.largeur(), 370.5, 0.1) ;
    }

    //TEST COLORIER

    @Test
    public void testColorier() {
        txt.colorier("yellow");
        assertEquals("<text x=\"192.0\" y=\"128.0\" font-size=\"64\" text-anchor=\"middle\" fill=\"yellow\" stroke=\"black\" >Istic L2GEN</text>", txt.enSVG());
    }

    //TESTDEPLACER

    @Test
    public void testDeplacer() {
        txt.deplacer(10, 10);
        Point after = txt.centre();
        assertEquals(after.x(), 202, 0.0) ;
        assertEquals(after.y(), 138, 0.0) ;
    }

    //TEST DUPLIQUER 

    @Test
    public void testDupliquer() {
        Texte duplicate = (Texte) txt.dupliquer();
        assertEquals(txt.enSVG(), duplicate.enSVG());
    }

    //TEST REDIMENSIONNER

    @Test
    public void testRedimensionner() {
        txt.redimensionner(0.5, 0.5);
        assertEquals("<text x=\"192.0\" y=\"128.0\" font-size=\"32\" text-anchor=\"middle\" fill=\"black\" stroke=\"black\" >Istic L2GEN</text>", txt.enSVG()) ;
    }
}
