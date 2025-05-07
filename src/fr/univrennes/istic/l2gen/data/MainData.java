package fr.univrennes.istic.l2gen.data;

import java.util.Map;

public class MainData {

    public static void main(String[] args) {
        DataManager dm = new DataManager();

        Map<String, String> criteres = Map.of( "domaine_thematique", "Ethnologie" );
        String[] depReg = {};

        //Exemple getStat
        String svg = dm.setFormat("DiagCol").setGranularite("region").getStat(criteres, depReg);
        dm.makeHtml(svg);
    
        //Exemple getLstMusee
        //criteres = Map.of( "domaine_thematique", "Histoire, Sciences et techniques" );
        //dm.getLstMusee(criteres) ;

        //Exemple getStatThematique
        //dm.getStatThematique();

        //Exemple getStatAge
        //dm.getStatAge(criteres, depReg);

        //System.out.println(dm.getAllKeys());
    }

}
