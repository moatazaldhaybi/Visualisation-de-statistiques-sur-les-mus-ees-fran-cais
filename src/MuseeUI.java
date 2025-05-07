import javax.imageio.ImageIO;
import javax.swing.*;

import fr.univrennes.istic.l2gen.data.DataManager;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MuseeUI implements ItemListener{
    private JFrame frame;
    private JTabbedPane tabbedPane;
    private JPanel visualisationPanel;
    private JComboBox<String> domaineComboBox;
    private JComboBox<String> regionComboBox;
    private JComboBox<String> departementComboBox;
    private DataManager dataManager;
    private Map<String, String> criteres;
    //Pour domaine_thematique -> separation par ", ". Voir Yanis
    private String domaineCritere ;
    private ArrayList<String> reg; 
    private ArrayList<String> dep;

    public MuseeUI(DataManager dataManager) {
        this.dataManager = dataManager;
        this.criteres = new HashMap<>();
        this.domaineCritere = "" ;
        this.reg = new ArrayList<>();
        this.dep = new ArrayList<>();
        createUI();
    }

    private void createUI() {
        frame = new JFrame("Visualisation des Musées Français");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();

        // Configuration Panel
        JPanel configPanel = createConfigPanel();

        // Visualization Panel
        visualisationPanel = new JPanel(new BorderLayout());
        visualisationPanel.add(new JLabel("Les visualisations apparaîtront ici", SwingConstants.CENTER),
                BorderLayout.CENTER);

        tabbedPane.addTab("Configuration", configPanel);
        tabbedPane.addTab("Visualisation", visualisationPanel);

        frame.add(tabbedPane);
        frame.setVisible(true);
    }

    private JPanel createConfigPanel() {
        JPanel configPanel = new JPanel(new BorderLayout(10, 10));
        configPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Left panel for selection criteria
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        // Granularity selection
        JPanel granularitePanel = new JPanel(new GridLayout(2, 1, 5, 5));
        granularitePanel.setBorder(BorderFactory.createTitledBorder("Granularité"));
        ButtonGroup granulariteGroup = new ButtonGroup();
        JRadioButton departementsBtn = new JRadioButton("Départements", true);
        JRadioButton regionsBtn = new JRadioButton("Régions");
        granulariteGroup.add(departementsBtn);
        granulariteGroup.add(regionsBtn);
        granularitePanel.add(departementsBtn);
        granularitePanel.add(regionsBtn);

        // Region/Department selection
        JPanel locationPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        locationPanel.setBorder(BorderFactory.createTitledBorder("Localisation"));

        // Remplir les régions depuis DataManager
        ArrayList<String> regions = dataManager.getAllReg();
        regionComboBox = new JComboBox<>(regions.toArray(new String[0]));
        regionComboBox.addItemListener(this);
        regionComboBox.addActionListener(e -> updateDepartementsComboBox());

        departementComboBox = new JComboBox<>();
        updateDepartementsComboBox();
        departementComboBox.addItemListener(this);

        locationPanel.add(new JLabel("Région:"));
        locationPanel.add(regionComboBox);
        locationPanel.add(new JLabel("Département:"));
        locationPanel.add(departementComboBox);

        // Criteria selection
        JPanel criteresPanel = new JPanel();
        criteresPanel.setLayout(new BoxLayout(criteresPanel, BoxLayout.Y_AXIS));
        criteresPanel.setBorder(BorderFactory.createTitledBorder("Critères de sélection"));

        // Remplir les domaines thématiques depuis DataManager
        JPanel domainePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        domainePanel.add(new JLabel("Domaine thématique:"));
        ArrayList<String> domaines = dataManager.getAllThematique();
        domaineComboBox = new JComboBox<>(domaines.toArray(new String[0]));
        domainePanel.add(domaineComboBox);
        domaineComboBox.addItemListener(this);

        JCheckBox personnageCheck = new JCheckBox("Personnage phare");
        JCheckBox themesCheck = new JCheckBox("Mots-clés (thèmes)");
        JCheckBox categorieCheck = new JCheckBox("Catégorie");

        criteresPanel.add(domainePanel);
        criteresPanel.add(personnageCheck);
        criteresPanel.add(themesCheck);
        criteresPanel.add(categorieCheck);

        leftPanel.add(granularitePanel);
        leftPanel.add(locationPanel);
        leftPanel.add(criteresPanel);

        // Right panel for statistics and visualization options
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        // Statistics selection
        JPanel statsPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        statsPanel.setBorder(BorderFactory.createTitledBorder("Type de statistiques"));
        ButtonGroup statsGroup = new ButtonGroup();

        JRadioButton nbMuseesRadio = new JRadioButton("Nombre de musées par domaine", true);
        JRadioButton ageMuseesRadio = new JRadioButton("Âge des musées");
        JRadioButton listeMuseesRadio = new JRadioButton("Liste des musées avec URL");

        statsGroup.add(nbMuseesRadio);
        statsGroup.add(ageMuseesRadio);
        statsGroup.add(listeMuseesRadio);

        statsPanel.add(nbMuseesRadio);
        statsPanel.add(ageMuseesRadio);
        statsPanel.add(listeMuseesRadio);

        // Visualization type selection
        JPanel diagramPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        diagramPanel.setBorder(BorderFactory.createTitledBorder("Type de diagramme"));
        ButtonGroup diagramGroup = new ButtonGroup();

        JRadioButton camembertBtn = new JRadioButton("Camembert", true);
        JRadioButton barresBtn = new JRadioButton("Barres");
        JRadioButton colonnesBtn = new JRadioButton("Colonnes");

        diagramGroup.add(camembertBtn);
        diagramGroup.add(barresBtn);
        diagramGroup.add(colonnesBtn);

        diagramPanel.add(camembertBtn);
        diagramPanel.add(barresBtn);
        diagramPanel.add(colonnesBtn);

        rightPanel.add(statsPanel);
        rightPanel.add(diagramPanel);

        // Generate button
        JButton genererBtn = new JButton("Générer le rapport");
        genererBtn.addActionListener(e -> generateReport(
                departementsBtn.isSelected() ? "departement" : "region",
                (String) regionComboBox.getSelectedItem(),
                (String) departementComboBox.getSelectedItem(),
                (String) domaineComboBox.getSelectedItem(),
                personnageCheck.isSelected(),
                themesCheck.isSelected(),
                categorieCheck.isSelected(),
                nbMuseesRadio.isSelected() ? "nombre" : ageMuseesRadio.isSelected() ? "âge" : "liste",
                camembertBtn.isSelected() ? "camembert" : barresBtn.isSelected() ? "barres" : "colonnes"));

        configPanel.add(leftPanel, BorderLayout.WEST);
        configPanel.add(rightPanel, BorderLayout.CENTER);
        configPanel.add(genererBtn, BorderLayout.SOUTH);

        return configPanel;
    }

    private void updateDepartementsComboBox() {
        // Dans cette implémentation, on affiche tous les départements
        // Si vous voulez filtrer par région, vous devrez implémenter cette logique
        ArrayList<String> departements = dataManager.getAllDep();
        departementComboBox.setModel(new DefaultComboBoxModel<>(departements.toArray(new String[0])));
    }

    private void generateReport(String granularite, String region, String departement,
            String domaine, boolean personnage, boolean themes,
            boolean categorie, String statistique, String diagramme) {
        visualisationPanel.removeAll();

        // Appel à DataManager selon le type de statistique
        String svg = "";
        try {
            dataManager.setGranularite(granularite);
            ArrayList<String> regDep = dep ; 
            if (dataManager.getGranularite() == "region"){
                regDep = reg;
            }
            if (statistique.equals("nombre")) {
                if (diagramme.equals("camembert")) {
                    dataManager.setFormat("DiagCam");
                } else if (diagramme.equals("barres")) {
                    dataManager.setFormat("DiagBarre");
                } else {
                    dataManager.setFormat("DiagCol");
                }
                svg = dataManager.getStat(criteres, regDep.toArray(new String[0]));
            } else if (statistique.equals("âge")) {
                svg = dataManager.getStatAge(criteres, regDep.toArray(new String[0]));
            } else {
                Map<String, String> musees = dataManager.getLstMusee(criteres);
                // Afficher la liste des musées dans l'interface
                JTextArea textArea = new JTextArea();
                textArea.setEditable(false);
                musees.forEach((nom, url) -> textArea.append(nom + ": " + url + "\n"));
                visualisationPanel.add(new JScrollPane(textArea), BorderLayout.CENTER);
                visualisationPanel.revalidate();
                visualisationPanel.repaint();
                this.domaineCritere = "" ;
                return;
            }

            BufferedImage originalImage = ImageIO.read(new File("src"+File.separator+"fr"+File.separator+"univrennes"+File.separator+"istic"+File.separator+"l2gen"+File.separator+"data"+File.separator+"stat.png"));
            // Taille du panel
            int panelWidth = visualisationPanel.getWidth();
            int panelHeight = visualisationPanel.getHeight();

            // Calculer l'échelle pour garder les proportions
            double widthRatio = (double) panelWidth / originalImage.getWidth();
            double heightRatio = (double) panelHeight / originalImage.getHeight();
            double scale = Math.min(widthRatio, heightRatio);

            // Nouvelles dimensions
            int newWidth = (int) (originalImage.getWidth() * scale);
            int newHeight = (int) (originalImage.getHeight() * scale);

            // Redimensionnement de l’image
            Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

            // Affichage
            JLabel picLabel = new JLabel(new ImageIcon(scaledImage));
            visualisationPanel.setLayout(new BorderLayout());
            visualisationPanel.add(picLabel, BorderLayout.CENTER);

            // popup html
            dataManager.makeHtml(svg);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame,
                    "Erreur lors de la génération du rapport: " + e.getMessage(),
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }

        visualisationPanel.revalidate();
        visualisationPanel.repaint();
        tabbedPane.setSelectedIndex(1);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        System.out.println(e.getSource());
        if (e.getSource() == departementComboBox) {
            this.dep.add((String) departementComboBox.getSelectedItem());
            System.out.println("departement ajoute" + (String) departementComboBox.getSelectedItem());

        } else if (e.getSource() == regionComboBox) {
            this.reg.add((String) regionComboBox.getSelectedItem());
            System.out.println("region ajoute" + (String) regionComboBox.getSelectedItem());

        } else {
            domaineCritere += (", " + (String) domaineComboBox.getSelectedItem());
            System.out.println(domaineCritere + "test 2 domaineCritere");
            criteres.put("domaine_thematique", domaineCritere);
            System.out.println("critere ajoute" + (String) domaineComboBox.getSelectedItem());
        }
    }

    public static void main(String[] args) {
        // Initialisation de DataManager
        DataManager dataManager = new DataManager();

        // Création de l'interface
        SwingUtilities.invokeLater(() -> new MuseeUI(dataManager));
    }


}