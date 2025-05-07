package test.fr.univrennes.istic.l2gen.visustats;


import org.junit.*;
import fr.univrennes.istic.l2gen.geometrie.*;
import fr.univrennes.istic.l2gen.visustats.*;

public class DiagBarresTest {

    @Before
    public void setUp() {
        double[] tab ={100,200,300,80};
        double[] tab2 ={125,75,160,70};
        double[] tab3 ={180,700,200,120};
        DiagBarres diagramme = new DiagBarres("TEST", 12);
        diagramme.legender("test1","test2","test3","test4");
        diagramme.ajouterDonnees("1652", tab);
        diagramme.ajouterDonnees("1924", tab2);
        diagramme.ajouterDonnees("2000", tab3);
        diagramme.colorier("blue","red","green","purple","yellow");
    }

}
