package fr.univrennes.istic.l2gen.data;

import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataLoad {

    //Si URL merde -> PATH
    
    private final String PATH = "src"+File.separator+"fr"+File.separator+"univrennes"+File.separator+"istic"+File.separator+"l2gen"+File.separator+"data"+File.separator+"jsonMusee.json";
    private Map<String, Map<String, String>> musee ;

    public DataLoad(){
        this.musee = new HashMap<String, Map<String, String>>();
    }

    /**
     * FAIT AVEC COPILOT POUR LA GESTION D'EXCEPTION ET LES PUT() -Yanis.
     * Converti le fichier json en map
     * Exemple d'utilisation : System.out.println(d.musee.get("M0842").get("refmer") );
     * 
     */
    public Map<String, Map<String, String>> loadJsonData() {
        System.out.println("Loading data...");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode rootNode = objectMapper.readTree(new File(PATH));
            for (JsonNode node : rootNode) {
                String identifiant = node.get("identifiant").asText();
                Map<String, String> details = new HashMap<>();
                details.put("nom_officiel", node.get("nom_officiel").asText());
                details.put("adresse", node.get("adresse").asText());
                details.put("lieu", node.get("lieu").asText());
                details.put("code_postal", node.get("code_postal").asText());
                details.put("ville", node.get("ville").asText());
                details.put("region", node.get("region").asText());
                details.put("departement", node.get("departement").asText());
                details.put("url", node.get("url").asText());
                details.put("telephone", node.get("telephone").asText());
                details.put("categorie", node.has("categorie") && !node.get("categorie").isNull() ? node.get("categorie").asText() : "");
                details.put("domaine_thematique", node.has("domaine_thematique") && !node.get("domaine_thematique").isNull() ? String.join(", ", objectMapper.convertValue(node.get("domaine_thematique"), String[].class)) : "");
                details.put("histoire", node.has("histoire") && !node.get("histoire").isNull() ? node.get("histoire").asText() : "");
                details.put("atout", node.has("atout") && !node.get("atout").isNull() ? node.get("atout").asText() : "");
                details.put("themes", node.has("themes") && !node.get("themes").isNull() ? node.get("themes").asText() : "");
                details.put("artiste", node.has("artiste") && !node.get("artiste").isNull() ? node.get("artiste").asText() : "");
                details.put("personnage_phare", node.has("personnage_phare") && !node.get("personnage_phare").isNull() ? node.get("personnage_phare").asText() : "");
                details.put("interet", node.has("interet") && !node.get("interet").isNull() ? node.get("interet").asText() : "");
                details.put("protection_batiment", node.has("protection_batiment") && !node.get("protection_batiment").isNull() ? node.get("protection_batiment").asText() : "");
                details.put("protection_espace", node.has("protection_espace") && !node.get("protection_espace").isNull() ? node.get("protection_espace").asText() : "");
                details.put("refmer", node.has("refmer") && !node.get("refmer").isNull() ? node.get("refmer").asText() : "");
                details.put("annee_creation", node.has("annee_creation") && !node.get("annee_creation").isNull() ? node.get("annee_creation").asText() : "");
                details.put("date_de_mise_a_jour", node.get("date_de_mise_a_jour").asText());
                // Add more fields as needed
                this.musee.put(identifiant, details);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Data loaded with " + this.musee.size() + " entries");
        return musee ; 

    }
}
