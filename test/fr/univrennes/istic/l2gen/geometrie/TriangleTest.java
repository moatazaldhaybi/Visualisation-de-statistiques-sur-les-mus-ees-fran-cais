package test.fr.univrennes.istic.l2gen.geometrie;

import static org.junit.Assert.*;

import org.junit.*;
import fr.univrennes.istic.l2gen.geometrie.*;

public class TriangleTest {
    Triangle triangle;

    @Before
    public void setUp() {
        triangle = new Triangle(100, 100, 300, 100, 100, 300);
    }

    // TEST DESCRIPTION

    @Test
    public void testDescription() {
        assertEquals("Triangle 100,00,100,00 300,00,100,00 100,00,300,00", triangle.description(0).trim());
    }

    // TEST enSVG

    @Test
    public void testEnSVG() {
        assertEquals(
                "<polygon points=\"100.0,100.0 300.0,100.0 100.0,300.0 \" fill=\"white\" stroke=\"black\"/>",
                triangle.enSVG());
    }

    // TEST DEPLACER

    @Test
    public void testDeplacer() {
        Triangle t2 = new Triangle(100, 100, 300, 100, 100, 300);
        Point before = triangle.centre();
        t2.deplacer(2, 3);
        Point after = t2.centre();
        assertNotEquals(before, after);
    }

    @Test
    public void testDeplacerOutOfBound() {
        Triangle t1 = new Triangle(100, 100, 300, 100, 100, 300);
        Triangle t2 = new Triangle(100, 100, 300, 100, 100, 300);

        Point before = t1.centre();
        t2.deplacer(triangle.centre().x() * 2, triangle.centre().y() * 2);
        Point after = t2.centre();
        assertNotEquals(before, after);
    }

    // TEST HAUTEUR
    @Test
    public void testHauteur() {

        assertEquals(200, triangle.hauteur(), 0.01);
    }

    // TEST COLORIER

    @Test
    public void testColorier() {
        String[] colors = { "blue", "red" };
        triangle.colorier(colors);
        // On ne peut pas récupérer la couleur directement, donc on vérifie que ça ne
        // plante pas
        assertEquals("blue", triangle.getColor());
    }

    // TEST DUPLIQUER

    @Test
    public void testDupliquer() {
        Triangle duplicate = (Triangle) triangle.dupliquer();
        assertEquals(triangle.enSVG(), duplicate.enSVG());
    }

    // TEST REDIMENSIONNER

    @Test
    public void testRedimensionner() {
        double beforeHeight = triangle.hauteur();
        double beforeWidth = triangle.largeur();
        triangle.redimensionner(0.5, 0.5);
        assertNotEquals(beforeHeight, triangle.hauteur());
        assertNotEquals(beforeWidth, triangle.largeur());
        assertEquals(triangle.hauteur(), beforeHeight * 0.5, 0.01);
        assertEquals(triangle.largeur(), beforeWidth * 0.5, 0.01);
    }

    @Test
    public void testTourner() {
        Triangle t = new Triangle(100, 100, 300, 100, 100, 300);
        t.tourner(90);
        Triangle expected = new Triangle(233.33333333333331, 100.0, 233.33333333333331, 300.0, 33.333333333333314,
                100.00000000000001);
        assertEquals(expected.description(0), t.description(0));

    }

    @Test
    public void testDescriptionWithIndentation() {
        assertEquals("Triangle 100,00,100,00 300,00,100,00 100,00,300,00", triangle.description(1).trim());
    }

    @Test
    public void testDescriptionWithoutIndentation() {
        assertEquals("Triangle 100,00,100,00 300,00,100,00 100,00,300,00", triangle.description(0).trim());
    }

    @Test
    public void testDescriptionWithMultipleIndentations() {
        assertEquals("Triangle 100,00,100,00 300,00,100,00 100,00,300,00", triangle.description(2).trim());
    }
}
