package fr.mrqsdf.test;

import fr.mrqsdf.plugintrain.IModelPlugin;

public class Main implements IModelPlugin {

    @Override
    public void printText() {
        System.out.println("Bonjour, je suis le plugin de démo !");
    }
}
