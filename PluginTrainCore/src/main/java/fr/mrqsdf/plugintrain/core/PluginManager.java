package fr.mrqsdf.plugintrain.core;


import fr.mrqsdf.plugintrain.api.AbstractModelPlugin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Enregistre les plug-ins actifs et gère leur cycle de vie. */
public final class PluginManager {
    private final List<AbstractModelPlugin> plugins = new ArrayList<>();

    public void register(AbstractModelPlugin plugin) { plugins.add(plugin); }

    /** Appelé à la fin de l'application ou lors d'un reload. */
    public void shutdown() {
        Collections.reverse(plugins);          // d'abord les dépendants
        for (AbstractModelPlugin p : plugins) {
            try { p.onDisable(); }
            catch (Exception e) { e.printStackTrace(); }
        }
        plugins.clear();
    }
}
