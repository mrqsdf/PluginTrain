package fr.mrqsdf.plugintrain;

import fr.mrqsdf.plugintrain.api.IModelPlugin;
import fr.mrqsdf.plugintrain.core.PluginManager;

public class MainApp {
    public static void main(String[] args) throws Exception {
        PluginManager manager = new PluginManager();
        PluginLoader loader   = new PluginLoader(manager);

        loader.loadAll();

        // --------------------------------------------------------------------------------
        // Application principale...
        // --------------------------------------------------------------------------------

        Runtime.getRuntime().addShutdownHook(new Thread(manager::shutdown));

        int i = 0;

        while (true){
            i++;
            System.out.println("Application en cours d'exécution... " + i);
            Thread.sleep(1000);
            if (i == 5) {
                manager.shutdown(); // not necessary if you use the shutdown hook
                System.out.println("Tous les plugins ont été désactivés.");
                break; // Sortie de la boucle pour terminer l'application
            }
        }

    }
}