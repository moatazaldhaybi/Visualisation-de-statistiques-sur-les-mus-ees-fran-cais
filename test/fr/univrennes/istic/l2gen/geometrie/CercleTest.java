package test.fr.univrennes.istic.l2gen.geometrie;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import fr.univrennes.istic.l2gen.geometrie.Cercle;
import fr.univrennes.istic.l2gen.geometrie.Point;

public class CercleTest {

    Cercle c;

    @Before
    public void setUp() {
        c = new Cercle(100, 100, 50);
    }

    // test

    @Test
    public void testDescription() {
        assertEquals("Cercle centre=100,100 r=50.0", c.description(0));
    }

    @Test
    public void testEnSVG() {
        assertEquals("<circle cx=\"100.0\"cy=\"100.0\" r=\"50.0\"" + "fill= \" white \"stroke= \"black\" />",
                c.enSVG());
    }

    @Test
    public void testDeplacer() {
        Point before = c.centre();
        Cercle d = (Cercle) c.dupliquer();
        d.deplacer(10, 10);
        Point after = d.centre();
        System.out.println(before.x()+","+after.x());
        assertNotEquals(before, after);
    }

    @Test
    public void testDupliquer() {
        Cercle d = (Cercle) c.dupliquer();
        assertNotEquals(c,d);      
    }
}