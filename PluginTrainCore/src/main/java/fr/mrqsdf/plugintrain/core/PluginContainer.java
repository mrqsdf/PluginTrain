package fr.mrqsdf.plugintrain.core;

import fr.mrqsdf.plugintrain.api.AbstractModelPlugin;

public final class PluginContainer {

    private final PluginDescription desc;
    private final PluginClassLoader loader;
    private AbstractModelPlugin instance;

    public PluginContainer(PluginDescription desc, PluginClassLoader loader) {
        this.desc = desc;
        this.loader = loader;
    }


    public PluginDescription getDescription() {
        return desc;
    }

    public PluginClassLoader getClassLoader() {
        return loader;
    }

    public AbstractModelPlugin getInstance() {
        return instance;
    }

    public void setInstance(AbstractModelPlugin plugin) {
        if (instance != null) {
            throw new IllegalStateException("Plugin instance already set for " + desc.name);
        }
        this.instance = plugin;
    }
}
