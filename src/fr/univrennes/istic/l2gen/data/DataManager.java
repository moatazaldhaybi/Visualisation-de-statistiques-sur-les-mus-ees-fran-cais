package fr.univrennes.istic.l2gen.data;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.awt.Desktop;

import fr.univrennes.istic.l2gen.visustats.*;

public class DataManager {
    // CONSTANTES
    private final String[] COL = {
            "red", "#00FF00", "#0000FF", "#FFFF00", "#00FFFF", "#FF00FF", "#000000", "#FFFFFF",
            "#FFA500", "#800080", "#008000", "#FFC0CB", "#808080", "#800000", "#000080", "#808000",
            "#C0C0C0", "#FFD700", "#A52A2A", "#D2691E", "#FF7F50", "#6495ED", "navy", "#00FFFF",
            "#00008B", "#008B8B", "#B8860B", "#A9A9A9", "#006400", "#BDB76B", "#8B008B", "#556B2F",
            "#FF8C00", "#9932CC", "#8B0000", "#E9967A", "#8FBC8F", "#483D8B", "#2F4F4F", "#00CED1",
            "#9400D3", "#FF1493", "#00BFFF", "#696969", "#1E90FF", "#B22222", "#FFFAF0", "#228B22",
    };

    private Map<String, Map<String, String>> musee;
    private Map<String, Integer> stat;
    private String granularite;
    private String format;

    public DataManager() {
        this.musee = new DataLoad().loadJsonData();
        this.stat = new HashMap<>();
        this.granularite = "region";
        this.format = "DiagCam";
    }

    /**
     * test si le musee contient la ou les thematique
     * 
     * @param thematique la ou les thematique a rechercher
     * @param musee      le musee a tester
     * @return true si le musee contient la thematique, false sinon
     */
    public boolean thematique(String thematique, Map<String, String> musee) {
        String[] values = thematique.split(", ");
        boolean cond = true;
        for (String value : values) {
            if (!musee.get("domaine_thematique").contains(value)) {
                cond = false;
                break;
            }
        }
        return cond;

    }

    /**
     * Regle la granularite des stats. Par defaut la granularite est "region"
     * 
     * @param granularite "region" ou "departement"
     * @return DataManager pour le chainage ->
     *         obj.setGranularite("region").getStat(criteres)
     */
    public DataManager setGranularite(String granularite) {
        this.granularite = granularite;
        return this;
    }

    /**
     * Regle le format du fichier de sortie. Par defaut le format est "DiagCam"
     * 
     * @param format "DiagCam", "DiagBarre" ou "DiagCol"
     * @return DataManager pour le chainage ->
     *         obj.setFormat("DiagBarre").getStat(criteres)
     */
    public DataManager setFormat(String format) {
        this.format = format;
        return this;
    }

    /**
     * Retourne le svg en fonction du format
     * 
     * @param critere   le ou les criteres de la statistique, pour le
     *                  titre(temporaire)
     * @param titreDiag le titre du camembert
     * @return le svg correspondant au format
     */
    private String getSvg(Map<String, String> critere, String titreDiag) {
        String height = "1500";
        String svg = "<svg width=\"1500\" height=\"" + height + "\" xmlns=\"http://www.w3.org/2000/svg\">\r\n";
        // Création du titre
        String titre = "";
        for (Map.Entry<String, String> entry : critere.entrySet()) {
            titre += entry.getKey() + " : " + entry.getValue() + " ";
        }
        // Creation legende
        String[] leg = new String[stat.size()];
        int i = 0;
        for (String l : stat.keySet()) {
            leg[i] = l;
            i++;
        }
        // Ajout des donnees
        double[] data = new double[stat.size()];
        i = 0;
        for (int d : stat.values()) {
            // copilot -> on divise par la somme des valeurs
            data[i] = (double) d / stat.values().stream().mapToInt(Integer::intValue).sum();
            i++;
        }
        switch (this.format) {
            case "DiagCam":
                DiagCamembert dc = new DiagCamembert(titre, 35);
                dc.legender(leg);
                dc.ajouterDonnees(titreDiag + "(s)", data);
                // Coloriage
                dc.colorier(COL);
                // Deplacement en fct de la longueur du bordel. Peut etre a changer
                dc.deplacer(500, 0);
                return svg + dc.agencer().enSVG() + "</svg>";

            case "DiagBarre":
                DiagBarres db = new DiagBarres(titre, 35);
                db.legender(leg);
                db.ajouterDonnees(titreDiag + "(s)", data);
                // Coloriage
                db.colorier(COL);
                // Deplacement en fct de la longueur du bordel. Peut etre a changer
                return svg + db.agencer().enSVG() + "</svg>";

            case "DiagCol":
                DiagColonnes dcol = new DiagColonnes(titre, 35);
                dcol.legender(leg);
                dcol.ajouterDonnees(titreDiag, data);
                // Coloriage
                dcol.colorier(COL);
                // Deplacement en fct de la longueur du bordel. Peut etre a changer
                return svg + dcol.agencer().enSVG() + "</svg>";

            default:
                return "<Text>Format non reconnu</Text>";
        }
    }

    /**
     * Creation du fichier SVG
     * 
     * @param svg le svg correspondant au format
     */
    private void createFile(String svg) {
        // Creation du fichier SVG
        try {
            // Creation du fichier
            File result = new File("src" + File.separator + "fr" + File.separator + "univrennes" + File.separator
                    + "istic" + File.separator + "l2gen" + File.separator + "data" + File.separator + "Resultat.svg");
            if (result.createNewFile()) {
                System.out.println("Nouveau fichier SVG : " + result.getName());
            } else {
                System.out.println("Le fichier SVG " + result.getName() + " existe deja, le contenu sera change");
            }
            // Ecriture dans le fichier
            System.out.println("Ecriture...");
            FileWriter fw = new FileWriter(result);
            fw.write(svg);
            fw.close();
            int total = stat.values().stream().mapToInt(Integer::intValue).sum();
            System.out.println(
                    "Ecriture terminee, " + stat.size() + " secteur(s) differents. " + total
                            + " musees respectent les criteres"
                            + "\nFichier dispo dans src" + File.separator + "fr" + File.separator + "univrennes"
                            + File.separator + "istic" + File.separator + "l2gen" + File.separator + "data");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Ecrit la statistique dans le fichier "Resultat.svg" selon le format, la
     * granularite et les criteres
     * ATTENTION -> si plusieurs thematiques separation par une virgule et un espace
     * :
     * 
     * <pre>
     * ", "
     * </pre>
     * 
     * @exemple:
     *           Map<String, String> criteres = Map.of("domaine_thematique",
     *           "Ethnologie");
     *           String[] depReg = { "Loire", "Bretagne", "Pays de la Loire",
     *           "Centre-Val de Loire", "Bas-Rhin", "Finistère" };
     *           getStat(criteres, depReg);
     * 
     * @param critere Map<cle, val> ou la cle est le critere et la val est la valeur
     *                du critere
     * @param reg_dep String[] contenant les regions ou departements cibles. Si la
     *                liste est vide tous les dep/reg seront select
     * @return String -> le svg de la statistique, en plus du fichier (dans /data)
     */
    public String getStat(Map<String, String> critere, String[] reg_dep) {
        this.stat.clear();
        System.out.println("Debut getStat");

        for (Map<String, String> musee : this.musee.values()) {
            boolean cond_reg_dep = false;
            if (reg_dep.length >= 1) {
                for (String v : reg_dep) {
                    if (musee.get(granularite).equals(v)) {
                        cond_reg_dep = true;
                        break;
                    }
                }
            } else {
                cond_reg_dep = true;
            }

            boolean cond = true;
            for (Map.Entry<String, String> entry : critere.entrySet()) {
                // Cas Domaine Thematique -> pluisieurs valeurs possibles
                if (entry.getKey() == "domaine_thematique") {
                    if (!thematique(entry.getValue(), musee)) {
                        cond = false;
                        break;
                    }
                }
                // Cas global
                else {
                    if (!musee.get(entry.getKey()).contains(entry.getValue())) {
                        cond = false;
                        break;
                    }
                }
            }
            // +1 pour la region ou le departement si le musee respecte les criteres
            if (cond && cond_reg_dep) {
                if (!stat.containsKey(musee.get(granularite))) {
                    stat.put(musee.get(granularite), 1);
                } else {
                    stat.put(musee.get(granularite), stat.get(musee.get(granularite)) + 1);
                }

            }
        }
        // Creation du svg
        String svg = this.getSvg(critere, granularite);
        this.createFile(svg);
        System.out.println("Fin getStat\n");
        try {
            new svg2png();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return svg;
    }

    /**
     * Affiche la liste de musee respectant les criteres dans le terminal.
     * ATTENTION -> si plusieurs thematiques separation par une virgule et un espace
     * :
     * 
     * <pre>
     * ", "
     * </pre>
     * 
     * @exemple:
     *           getMusee(Map.of("ville", "Rennes", "domaine_thematique",
     *           "Histoire, Sciences et techniques"))
     * 
     * @param critere Map<cle, val> ou la cle est le critere et la val est la valeur
     *                du critere
     * @return Map<String,String> -> une map<Nom musee, url musee>
     */
    public Map<String, String> getLstMusee(Map<String, String> critere) {
        System.out.println("Debut getLstMusee");
        Map<String, String> result = new HashMap<>();

        for (Map<String, String> musee : this.musee.values()) {
            boolean cond = true;
            for (Map.Entry<String, String> entry : critere.entrySet()) {
                // Cas Domaine Thematique -> pluisieurs valeurs possibles
                if (entry.getKey() == "domaine_thematique") {
                    if (!thematique(entry.getValue(), musee)) {
                        cond = false;
                        break;
                    }
                }
                // Cas global
                else {
                    if (!musee.get(entry.getKey()).contains(entry.getValue())) {
                        cond = false;
                        break;
                    }
                }
            }
            // ajoute le musee au result si il respecte les criteres
            if (cond) {
                result.put(musee.get("nom_officiel"), musee.get("url"));
            }
        }
        System.out.println("Musee respectant les criteres suivant: " + critere + "\n");
        result.forEach((k, v) -> {
            System.out.println(k + " : " + v);
        });
        System.out.println("Fin getLstMusee");
        return result;
    }

    /**
     * Ecrit la statistique dans le fichier "Resultat.svg"
     * 
     * @return String -> le svg correspondant a la stat
     * 
     */
    public String getStatThematique() {
        this.stat.clear();
        System.out.println("Debut getMuseeParThematique");
        for (Map<String, String> musee : this.musee.values()) {
            String[] thematiques = musee.get("domaine_thematique").split(", ");
            for (String thematique : thematiques) {
                // Si la cle exsite pas -> on ajoute, sinon incremente
                if (thematique != "") {
                    if (!stat.containsKey(thematique)) {
                        stat.put(thematique, 1);
                    } else {
                        stat.put(thematique, stat.get(thematique) + 1);
                    }
                }
            }
        }
        String svg = this.getSvg(Map.of("Nombre de musee par thematique", ""), "Thematiques");
        this.createFile(svg);
        System.out.println("Fin getMuseeParThematique");
        try {
            new svg2png();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return svg;
    }

    /**
     * 
     * Ecrit la statistique dans le fichier "Resultat.svg" selon le format, la
     * granularite et les criteres
     * ATTENTION -> si plusieurs thematiques separation par une virgule et un espace
     * :
     * 
     * <pre>
     * ", "
     * </pre>
     * 
     * @exemple:
     * 
     *           Map<String, String> criteres = Map.of("domaine_thematique",
     *           "Ethnologie");
     *           String[] depReg = { "Loire", "Bretagne", "Pays de la Loire",
     *           "Centre-Val de Loire", "Bas-Rhin", "Finistère" };
     *           getStatAge(criteres, depReg);
     * 
     * @param critere Map<cle, val> ou la cle est le critere et la val est la valeur
     *                du critere
     * @param reg_dep String[] contenant les regions ou departements cibles. Si la
     *                liste est vide tous les dep/reg seront select
     * @return String -> le svg correspondant a la stat en plus du fichier (dans
     *         /data)
     */
    public String getStatAge(Map<String, String> critere, String[] reg_dep) {
        this.stat.clear();
        System.out.println("Debut getStatAge");
        for (Map<String, String> musee : this.musee.values()) {
            // Check reg_dep
            boolean cond_reg_dep = false;
            if (reg_dep.length >= 1) {
                for (String v : reg_dep) {
                    if (musee.get(granularite).equals(v)) {
                        cond_reg_dep = true;
                        break;
                    }
                }
            } else {
                cond_reg_dep = true;
            }

            boolean cond = true;
            for (Map.Entry<String, String> entry : critere.entrySet()) {
                // Cas Domaine Thematique -> plusieurs valeurs possibles
                if (entry.getKey() == "domaine_thematique") {
                    if (!thematique(entry.getValue(), musee)) {
                        cond = false;
                        break;
                    }
                }
                // Cas global
                else {
                    if (!musee.get(entry.getKey()).contains(entry.getValue())) {
                        cond = false;
                        break;
                    }
                }
            }
            // +1 pour l'age du musee si le musee respecte les criteres
            if (cond && cond_reg_dep && musee.get("annee_creation") != "") {
                String creation = musee.get("annee_creation");
                if (!stat.containsKey(creation)) {
                    stat.put(creation, 1);
                } else {
                    stat.put(creation, stat.get(creation) + 1);
                }

            }
        }
        String svg = this.getSvg(critere, "annee_creation");
        this.createFile(svg);
        System.out.println("Fin getStatAge");
        try {
            new svg2png();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return svg;

    }

    /**
     * Renvoi toutes les clees d'un musee
     * 
     * @return ArrayList<String> la liste de clees
     */
    public ArrayList<String> getAllKeys() {
        // iterator() -> pour iterer dans une collection
        // next() -> pour passer a la valeur suivante
        return new ArrayList<>(this.musee.values().iterator().next().keySet());

    }

    public String getGranularite() {
        return this.granularite;
    }

    public String getFormat() {
        return this.format;
    }

    public ArrayList<String> getAllDep() {
        ArrayList<String> result = new ArrayList<>();
        for (Map<String, String> musee : musee.values()) {
            String v = musee.get("departement");
            if (!result.contains(v)) {
                result.add(v);
            }
        }
        return result;
    }

    public ArrayList<String> getAllReg() {
        ArrayList<String> result = new ArrayList<>();
        for (Map<String, String> musee : musee.values()) {
            String v = musee.get("region");
            if (!result.contains(v)) {
                result.add(v);
            }
        }
        return result;
    }

    public ArrayList<String> getAllThematique() {
        ArrayList<String> result = new ArrayList<>();
        for (Map<String, String> musee : musee.values()) {
            String v = musee.get("domaine_thematique");
            String[] values = v.split(", ");
            for (String theme : values) {
                if (!result.contains(theme)) {
                    result.add(theme);
                }
            }
        }
        return result;
    }

    public ArrayList<String> getPersoPhare() {
        ArrayList<String> result = new ArrayList<>();
        for (Map<String, String> musee : musee.values()) {
            String v = musee.get("personnage_phare");
            String[] values = v.split(". ");
            for (String theme : values) {
                if (!result.contains(theme)) {
                    result.add(theme);
                }
            }
        }
        return result;
    }

    public ArrayList<String> getMotCle() {
        ArrayList<String> result = new ArrayList<>();
        for (Map<String, String> musee : musee.values()) {
            String v = musee.get("themes");
            String[] values = v.split(", ");
            for (String theme : values) {
                if (!result.contains(theme)) {
                    result.add(theme);
                }
            }
        }
        return result;
    }

    public ArrayList<String> getCategorie() {
        ArrayList<String> result = new ArrayList<>();
        for (Map<String, String> musee : musee.values()) {
            String v = musee.get("categorie");
            String[] values = v.split(". ");
            for (String theme : values) {
                if (!result.contains(theme)) {
                    result.add(theme);
                }
            }
        }
        return result;
    }

    public void makeHtml(String svg) {

        String content = "<DOCTYPE html>\n<html>\n<head>\n<link rel=\"stylesheet\" href=\"style.css\">\n<title>Stat-Gebra</title><h1>Stat-Gebra</h1>\n</head>\n<body>\n"
                + svg + "\n</body>\n</html>";
        System.out.println(content);
        try {
            File html = new File("src" + File.separator + "fr" + File.separator + "univrennes" + File.separator
                    + "istic" + File.separator + "l2gen" + File.separator + "html" + File.separator + "rapport.html");
            FileWriter fw = new FileWriter(html);
            fw.write(content);
            fw.close();
            System.out.println("File generated !");
            Desktop.getDesktop().browse(html.toURI());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
