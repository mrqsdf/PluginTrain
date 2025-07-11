package fr.mrqsdf.test;

import fr.mrqsdf.plugintrain.api.AbstractModelPlugin;
import fr.mrqsdf.plugintrain.api.IModelPlugin;

public class Main extends AbstractModelPlugin {

    @Override
    public void printText() {
        System.out.println("Bonjour, je suis le plugin de démo !");
    }

    @Override
    public void onLoad() throws Exception {
        System.out.println("Plugin chargé avec succès !");
    }

    @Override
    public void onEnable() throws Exception {
        System.out.println("Plugin activé !");
    }

    @Override
    public void onDisable() throws Exception {
        System.out.println("Plugin désactivé !");
    }


}
