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
    }
}