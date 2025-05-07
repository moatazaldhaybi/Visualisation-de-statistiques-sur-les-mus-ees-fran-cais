package test.fr.univrennes.istic.l2gen.geometrie;

import static org.junit.Assert.*;
import org.junit.*;
import fr.univrennes.istic.l2gen.geometrie.*;

public class PointTest {
    Point pt;

    @Before
    public void setUp() {
        pt = new Point(256, 256);
    }

    // TEST EQUALS

    @Test
    public void testEqualsClassic() {
        Point pt2 = new Point(256, 256);
        assertTrue(pt.equals(pt2));
    }

    @Test
    public void testEqualsWrongValues() {
        Point pt2 = new Point(10, 10);
        assertFalse(pt.equals(pt2));
    }

    @SuppressWarnings("unlikely-arg-type")
    @Test
    public void testEqualsWrongObject() {
        Rectangle rect = new Rectangle(pt, 1, 1);
        assertFalse(pt.equals(rect));
    }

    // TEST PLUS(Avec Point en param)

    @Test
    public void testPlusPtClassic() {
        pt = pt.plus(new Point(4, 5));
        assertTrue(pt.x() == 260 && pt.y() == 261);
    }

    // TEST PLUS(Avec double x, double y en param)

    @Test
    public void testPlusPt() {
        pt = pt.plus(4, 5);
        assertTrue(pt.x() == 260 && pt.y() == 261);

    }
}
