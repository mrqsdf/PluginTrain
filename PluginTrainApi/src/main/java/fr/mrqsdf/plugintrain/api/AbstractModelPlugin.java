package fr.mrqsdf.plugintrain.api;

public abstract class AbstractModelPlugin implements IModelPlugin{

    /** Appelé immédiatement après l'instanciation (chargement des ressources). */
    public void onLoad() throws Exception {}

    /** Appelé lorsque toutes les dépendances de ce plug-in sont prêtes. */
    public void onEnable() throws Exception {}

    /** Appelé avant l'arrêt ou le rechargement. */
    public void onDisable() throws Exception {}

    /** Exemple d'API métier minimale. */
    public abstract void printText();

}
