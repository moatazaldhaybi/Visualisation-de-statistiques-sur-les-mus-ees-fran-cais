package test.fr.univrennes.istic.l2gen.geometrie;

import static org.junit.Assert.*;
import org.junit.*;

import fr.univrennes.istic.l2gen.geometrie.Point;
import fr.univrennes.istic.l2gen.geometrie.Polygone;

class PolygoneTest {
    private Polygone polygone;

    @Before
    void setUp() {
        polygone = new Polygone(0, 0, 4, 0, 2, 3);
    }

    @Test
    void testCentre() {
        Point centre = polygone.centre();
        assertEquals(2.0, centre.x(), 0.01);
        assertEquals(1.0, centre.y(), 0.01);

    }

    @Test
    void testHauteur() {
        assertEquals(3.0, polygone.hauteur(), 0.01);
    }

    @Test
    void testLargeur() {
        assertEquals(4.0, polygone.largeur(), 0.01);
    }

    @Test
    void testDeplacer() {
        polygone.deplacer(2, 3);
        Point centre = polygone.centre();
        assertEquals(4.0, centre.x(), 0.01);
        assertEquals(4.0, centre.y(), 0.01);
    }

    @Test
    void testDupliquer() {
        Polygone copie = (Polygone) polygone.dupliquer();
        assertNotSame(polygone, copie);
        assertEquals(polygone.getSommets().length, copie.getSommets().length);
    }

    @Test
    void testRedimensionner() {
        polygone.redimensionner(2, 2);
        assertEquals(6, polygone.hauteur(), 0.01);
        assertEquals(8, polygone.largeur(), 0.01);
    }

    @Test
    void testColorier() {
        polygone.colorier("red");
        assertEquals("red", polygone.getColor());
    }

    @Test
    void testEnSVG() {
        polygone.colorier("blue");
        String svg = polygone.enSVG();
        assertTrue(svg.contains("<polygon points=\""), "Le SVG doit contenir une balise <polygon>.");
        assertTrue(svg.contains("style=\"fill:blue;stroke:black;"), "Le style du SVG doit inclure la couleur.");
    }
}
