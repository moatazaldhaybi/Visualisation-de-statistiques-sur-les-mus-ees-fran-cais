package fr.univrennes.istic.l2gen.visustats;

import fr.univrennes.istic.l2gen.geometrie.IForme;

public interface IDataVisualiseur extends IForme {

    /**
     * Agence les donnees pour les afficher enSVG() ou description() ? 
     * @return les donnees agencee
     */
    public IDataVisualiseur agencer(); 

    /**
     * Ajoute des donnees aux stats.
     * @param nomData le nom de la donnee 
     * @param data les donnees de la stat
     * @return les stats avec les donnees supplementaires 
     */
    public IDataVisualiseur ajouterDonnees(String nomData, double... data) ;
    
    /**
     * Ajoute une legende aux figures.
     * La figure 0 aura la légende 0, la figure 1 la legende 1...
     * @param txt String[] | String -> une legende
     * @return les stats avec une légende 
     */
    public IDataVisualiseur legender(String... txt) ; 

    /**
     * //TODO: Ca fait quoi ???
     * @param options
     * @return
     */
    public IDataVisualiseur setOptions(String... options) ; 

}