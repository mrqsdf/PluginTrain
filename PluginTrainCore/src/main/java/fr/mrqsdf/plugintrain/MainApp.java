package fr.mrqsdf.plugintrain;

public class MainApp {
    public static void main(String[] args) throws Exception {
        PluginLoader loader = new PluginLoader();
        System.out.println("Chargement des plugins...");
        for (IModelPlugin p : loader.load()) {
            System.out.println("=========================================");
            System.out.println("Plugin trouvé : " + p.getClass().getSimpleName());
            p.printText();
        }
    }
}