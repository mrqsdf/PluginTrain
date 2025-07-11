package fr.mrqsdf.plugintrain;

import fr.mrqsdf.plugintrain.api.AbstractModelPlugin;
import fr.mrqsdf.plugintrain.core.*;

import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;
import java.util.jar.JarFile;

public class PluginLoader {

    public static final String API_VERSION = "1.0";               // mise à jour à chaque breaking change
    private static final Path PLUGINS_DIR = Paths.get("plugins");

    private final PluginManager manager;

    public PluginLoader(PluginManager manager) { this.manager = manager; }

    /** Charge tous les plug-ins, gère dépendances et cycle de vie. */
    public void loadAll() throws Exception {

        // 1) Découverte brutale des JAR
        List<PluginContainer> discovered = new ArrayList<>();
        if (Files.isDirectory(PLUGINS_DIR)) {
            try (DirectoryStream<Path> ds = Files.newDirectoryStream(PLUGINS_DIR, "*.jar")) {
                for (Path jarPath : ds) {
                    PluginContainer pc = inspectJar(jarPath);
                    if (pc != null) discovered.add(pc);
                }
            }
        }

        // 2) Tri topologique par dépendances
        List<PluginContainer> sorted = DependencyResolver.sort(discovered);

        // 3) Cycle de vie
        for (PluginContainer pc : sorted) {
            AbstractModelPlugin plugin =
                    (AbstractModelPlugin) pc.getClassLoader().loadClass(pc.getDescription().mainClass)
                            .getDeclaredConstructor().newInstance();
            plugin.onLoad();
            pc.setInstance(plugin);
        }

        for (PluginContainer pc : sorted) {          // onEnable après que tous les onLoad aient réussi
            pc.getInstance().onEnable();
            manager.register(pc.getInstance());
            System.out.println("✔ Plug-in activé : " + pc.getDescription().name + " " + pc.getDescription().version);
        }
    }

    /** Inspecte un JAR, renvoie null si pas de plugin.yml ou api-version incompatible. */
    private PluginContainer inspectJar(Path jarPath) throws IOException {
        try (JarFile jar = new JarFile(jarPath.toFile())) {
            var entry = jar.getJarEntry("model.yml");
            if (entry == null) return null;

            try (InputStream in = jar.getInputStream(entry)) {
                PluginDescription desc = new PluginDescription(in);

                // Vérif API
                if (!API_VERSION.equals(desc.apiVersion)) {
                    System.err.printf("⚠ %s : api-version \"%s\" incompatible (core=%s)%n",
                            jarPath.getFileName(), desc.apiVersion, API_VERSION);
                    return null;
                }

                // Chargeur isolé
                URL jarUrl = jarPath.toUri().toURL();
                PluginClassLoader cl = new PluginClassLoader(jarUrl, getClass().getClassLoader());

                return new PluginContainer(desc, cl);
            }
        }
    }
}
