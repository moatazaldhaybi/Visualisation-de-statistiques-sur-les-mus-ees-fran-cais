package test.fr.univrennes.istic.l2gen.geometrie;

import static org.junit.Assert.*;
import org.junit.*;
import fr.univrennes.istic.l2gen.geometrie.*;

public class GroupeTest {
    Groupe grp ; 

    @Before
    public void setUp(){
        grp = new Groupe(new Rectangle(new Point(256, 256), 256, 128)) ; 
    }

    @Test
    public void testCentreOneFigure(){
        assertEquals(grp.centre(), new Point(256,256)) ; 
    }

    @Test
    public void testCentreMultipleFigures(){
        grp.ajouter(new Cercle(new Point(128, 128), 10)) ; 
        grp.ajouter(new Triangle(new Point(128, 128), new Point(256, 256), new Point(750, 750))) ; 
        assertEquals(grp.centre(), new Point(254,254)) ; 
    }

    @Test 
    public void testHauteurOneFigure(){
        assertEquals(grp.hauteur(), 128, 0.0);
    }

    @Test
    public void testHauteurMultipleFigures(){
        grp.ajouter(new Cercle(new Point(128, 128), 10)) ; 
        grp.ajouter(new Triangle(new Point(128, 128), new Point(256, 256), new Point(750, 750))) ; 
        assertEquals(grp.hauteur(), 622, 0.0) ; 
    }

    @Test 
    public void testLargeurOneFigure(){
        assertEquals(grp.largeur(), 256, 0.0);
    }

    @Test
    public void testLargeurMultipleFigures(){
        grp.ajouter(new Cercle(new Point(128, 128), 10)) ; 
        grp.ajouter(new Triangle(new Point(128, 128), new Point(256, 256), new Point(750, 750))) ; 
        assertEquals(grp.largeur(), 622, 0.0) ; 
    }

    @Test
    public void testDescriptionClassic() {
        assertEquals("Groupe\n  Rectangle Centre=256,256 L=256.0 H=128.0 Couleur=white\n", grp.description(0)) ; 
    }

    @Test
    public void testEnSVGClassic() {
        assertEquals("<g>\n" + //
                        "<rect x=\"128.0\" y=\"192.0\" width=\"256.0\" height=\"128.0\" fill=\"white\" stroke=\"black\" />\n" + //
                        "</g>", grp.enSVG()) ; 
    }

    @Test
    public void testAjouterClassic() {
        //pb triangle normal
        grp.ajouter(new Triangle(new Point(10, 10), new Point(20, 20), new Point(30, 30))) ; 
        assertEquals("Groupe\n  Rectangle Centre=256,256 L=256.0 H=128.0 Couleur=white\n" + //
                        "  Triangle 10,10 20,20 30,30\n", grp.description(0)) ;
    }

    @Test
    public void testColorierClassic() {
        grp.ajouter(new Cercle(new Point(256, 256), 10)) ; 
        String[] lst = {"yellow", "purple"} ; 
        grp.colorier(lst);
        assertEquals("Groupe\n  Rectangle Centre=256,256 L=256.0 H=128.0 Couleur=yellow\n" + //
        "  Cercle centre=256,256 r=10.0\n", grp.description(0)) ;
    }

    @Test
    /**
     * Avec plus de valeurs dans grp(les figures) que dans lst(les couleurs)
     */
    public void testColorier2() {
        grp.ajouter(new Cercle(new Point(256, 256), 10)) ; 
        grp.ajouter(new Cercle(new Point(256, 256), 10)) ; 
        String[] lst = {"yellow", "purple"} ; 
        grp.colorier(lst);
        assertEquals("Groupe\n  Rectangle Centre=256,256 L=256.0 H=128.0 Couleur=yellow\n" + //
        "  Cercle centre=256,256 r=10.0\n" + //
        "  Cercle centre=256,256 r=10.0\n", grp.description(0)) ;
    }

    @Test
    public void testDeplacerClassic() {
        String before = grp.description(0) ; 
        grp.deplacer(10, 10);
        String after = grp.description(0) ; 
        assertNotEquals(before, after) ;
    }

    @Test
    public void testDupliquer() {
        String descrGrp1 = grp.description(0) ; 
        Groupe tmp = (Groupe)grp.dupliquer() ; 
        assertEquals(descrGrp1, tmp.description(0)) ; 
    }

    @Test
    public void testRedimensionner() {
        String before = grp.description(0) ; 
        grp.redimensionner(0.5, 0.5);
        String after = grp.description(0) ; 
        assertNotEquals(before, after) ; 
    }
}
