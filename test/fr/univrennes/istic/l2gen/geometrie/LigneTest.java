package test.fr.univrennes.istic.l2gen.geometrie;

import static org.junit.Assert.*;
import org.junit.*;
import fr.univrennes.istic.l2gen.geometrie.*;

public class LigneTest {
    Ligne ligne;

    @Before
    public void setUp() {
        ligne = new Ligne(0, 0, 10, 10);
    }

    // TEST CONSTRUCTEUR

    @Test
    public void testConstructeurClassic() {
        assertEquals(2, ligne.getSommets().size());
        assertEquals(new Point(0, 0), ligne.getSommets().get(0));
        assertEquals(new Point(10, 10), ligne.getSommets().get(1));
    }

    @Test
    public void testConstructeurCopie() {
        Ligne copie = new Ligne(ligne);
        assertNotSame(ligne, copie);
        assertEquals(ligne.getSommets(), copie.getSommets());
    }

    // TEST AJOUT DE SOMMETS

    @Test
    public void testAjouterSommetPoint() {
        ligne.ajouterSommet(new Point(5, 5));
        assertEquals(3, ligne.getSommets().size());
        assertEquals(new Point(5, 5), ligne.getSommets().get(2));
    }

    @Test
    public void testAjouterSommetCoordonnees() {
        ligne.ajouterSommet(2, 3);
        assertEquals(3, ligne.getSommets().size());
        assertEquals(new Point(2, 3), ligne.getSommets().get(2));
    }

    // TEST DIMENSIONS

    @Test
    public void testHauteur() {
        ligne.ajouterSommet(5, 15);
        assertEquals(15, ligne.hauteur(), 0.001);
    }

    @Test
    public void testLargeur() {
        ligne.ajouterSommet(20, 5);
        assertEquals(20, ligne.largeur(), 0.001);
    }

    // TEST DEPLACEMENT

    @Test
    public void testDeplacer() {
        ligne.deplacer(5, 5);
        assertEquals(new Point(5, 5), ligne.getSommets().get(0));
        assertEquals(new Point(15, 15), ligne.getSommets().get(1));
    }

    // TEST DUPLICATION

    @Test
    public void testDupliquer() {
        Ligne copie = (Ligne) ligne.dupliquer();
        assertNotSame(ligne, copie);
        assertEquals(ligne.getSommets(), copie.getSommets());
    }

    // TEST REDIMENSIONNEMENT

    @Test
    public void testRedimensionner() {
        ligne.redimensionner(2, 2);
        assertEquals(new Point(0, 0), ligne.getSommets().get(0));
        assertEquals(new Point(20, 20), ligne.getSommets().get(1));
    }

    // TEST DESCRIPTION

    @Test
    public void testDescription() {
        String expected = "Ligne Point[x=0.0, y=0.0]Point[x=10.0, y=10.0]";
        assertEquals(expected, ligne.description(0));
    }

    // TEST SVG

    @Test
    public void testEnSVG() {
        String expected = "<polyline points=\"0.0,0.0 10.0,10.0 \" fill=\"white\" stroke=\"black\"/>";
        assertEquals(expected, ligne.enSVG());
    }
}
