package test.fr.univrennes.istic.l2gen.visustats;

import static org.junit.Assert.assertEquals;
import org.junit.*;
import fr.univrennes.istic.l2gen.geometrie.Point;
import fr.univrennes.istic.l2gen.visustats.Camembert;

public class CamembertTest {
    private Camembert cam;

    @Before
    public void setUp() {
        cam = new Camembert(new Point(200, 200), 100);
        cam.ajouterSecteur("red", 0.15);
        cam.ajouterSecteur("blue", 0.2);
        cam.ajouterSecteur("purple", 0.65);
    }

    @Test
    public void testDescription() {
        assertEquals("Groupe\n" + //
                "  Secteur centre=200,200 r=100.0 Angle=0.0 Arc=54.0\n" + //
                "  Secteur centre=200,200 r=100.0 Angle=54.0 Arc=72.0\n" + //
                "  Secteur centre=200,200 r=100.0 Angle=126.0 Arc=234.0\n", cam.description(0));
    }

    @Test
    public void testEnSVG() {
        assertEquals("<g>\n" + //
                "<path d=\"M200 200 L 300 200 A 100.0 100.0 0 0,1 258 280 Z\" fill=\"red\" stroke=\"black\" />\n" + //
                "<path d=\"M200 200 L 300 200 A 100.0 100.0 0 0,1 230 295 Z\" fill=\"blue\" stroke=\"black\" transform=\"rotate(54.0 200.0 200.0)\"/>\n"
                + //
                "<path d=\"M200 200 L 300 200 A 100.0 100.0 0 1,1 141 119 Z\" fill=\"purple\" stroke=\"black\" transform=\"rotate(126.0 200.0 200.0)\"/>\n"
                + //
                "</g>", cam.enSVG());
    }

    @Test
    public void testAjouterSecteur() {
        cam.ajouterSecteur("orange", 0.40);
        assertEquals("Groupe\n" + //
                "  Secteur centre=200,200 r=100.0 Angle=0.0 Arc=54.0\n" + //
                "  Secteur centre=200,200 r=100.0 Angle=54.0 Arc=72.0\n" + //
                "  Secteur centre=200,200 r=100.0 Angle=126.0 Arc=234.0\n" + //
                "  Secteur centre=200,200 r=100.0 Angle=360.0 Arc=144.0\n" + //
                "", cam.description(0));
    }

    @Test
    public void testCentre() {
        assertEquals(200, cam.centre().x(), 0.0);
        assertEquals(200, cam.centre().y(), 0.0);

    }

    @Test
    public void testColorier() {
        cam.colorier("orange");
        assertEquals("<g>\n" + //
                "<path d=\"M200 200 L 300 200 A 100.0 100.0 0 0,1 258 280 Z\" fill=\"orange\" stroke=\"black\" />\n" + //
                "<path d=\"M200 200 L 300 200 A 100.0 100.0 0 0,1 230 295 Z\" fill=\"orange\" stroke=\"black\" transform=\"rotate(54.0 200.0 200.0)\"/>\n"
                + //
                "<path d=\"M200 200 L 300 200 A 100.0 100.0 0 1,1 141 119 Z\" fill=\"orange\" stroke=\"black\" transform=\"rotate(126.0 200.0 200.0)\"/>\n"
                + //
                "</g>", cam.enSVG());
    }

    @Test
    public void testColorierMultipleColors() {
        String[] colors = { "chartreuse", "azure", "navy(16)" };
        cam.colorier(colors);
        assertEquals("<g>\n" + //
                "<path d=\"M200 200 L 300 200 A 100.0 100.0 0 0,1 258 280 Z\" fill=\"chartreuse\" stroke=\"black\" />\n"
                + //
                "<path d=\"M200 200 L 300 200 A 100.0 100.0 0 0,1 230 295 Z\" fill=\"azure\" stroke=\"black\" transform=\"rotate(54.0 200.0 200.0)\"/>\n"
                + //
                "<path d=\"M200 200 L 300 200 A 100.0 100.0 0 1,1 141 119 Z\" fill=\"navy(16)\" stroke=\"black\" transform=\"rotate(126.0 200.0 200.0)\"/>\n"
                + //
                "</g>", cam.enSVG());
    }

    @Test
    public void testDeplacer() {
        cam.deplacer(10, 10);
        assertEquals(210, cam.centre().x(), 0.0);
        assertEquals(210, cam.centre().y(), 0.0);
    }

    @Test
    public void testDupliquer() {
        Camembert cam2 = (Camembert) cam.dupliquer();
        cam.colorier("red");// On s'assure que les secteurs ne sont pas les memes(en memoire).
        assertEquals("<g>\n" + //
                "<path d=\"M200 200 L 300 200 A 100.0 100.0 0 0,1 258 280 Z\" fill=\"red\" stroke=\"black\" />\n" + //
                "<path d=\"M200 200 L 300 200 A 100.0 100.0 0 0,1 230 295 Z\" fill=\"blue\" stroke=\"black\" transform=\"rotate(54.0 200.0 200.0)\"/>\n"
                + //
                "<path d=\"M200 200 L 300 200 A 100.0 100.0 0 1,1 141 119 Z\" fill=\"purple\" stroke=\"black\" transform=\"rotate(126.0 200.0 200.0)\"/>\n"
                + //
                "</g>", cam2.enSVG());
    }

    @Test
    public void testHauteur() {
        assertEquals(100, cam.hauteur(), 0.0);
    }

    @Test
    public void testLargeur() {
        assertEquals(100, cam.largeur(), 0.0);
    }

    @Test
    public void testRedimensionner() {
        cam.redimensionner(0.5, 0.5);
        assertEquals("<g>\n<path d=\"M200 200 L 250 200 A 50.0 50.0 0 0,1 229 240 Z\" fill=\"red\" stroke=\"black\" />\n" + //
                "<path d=\"M200 200 L 250 200 A 50.0 50.0 0 0,1 215 247 Z\" fill=\"blue\" stroke=\"black\" transform=\"rotate(54.0 200.0 200.0)\"/>\n" + //
                "<path d=\"M200 200 L 250 200 A 50.0 50.0 0 1,1 170 159 Z\" fill=\"purple\" stroke=\"black\" transform=\"rotate(126.0 200.0 200.0)\"/>\n</g>",cam.enSVG());
    }

    @Test
    public void testTourner() {
        cam.tourner(90);
        assertEquals("<g>\n" + //
                        "<path d=\"M200 200 L 300 200 A 100.0 100.0 0 0,1 258 280 Z\" fill=\"red\" stroke=\"black\" transform=\"rotate(90.0 200.0 200.0)\"/>\n" + //
                        "<path d=\"M200 200 L 300 200 A 100.0 100.0 0 0,1 230 295 Z\" fill=\"blue\" stroke=\"black\" transform=\"rotate(144.0 200.0 200.0)\"/>\n" + //
                        "<path d=\"M200 200 L 300 200 A 100.0 100.0 0 1,1 141 119 Z\" fill=\"purple\" stroke=\"black\" transform=\"rotate(216.0 200.0 200.0)\"/>\n" + //
                        "</g>", cam.enSVG());
    }
}
