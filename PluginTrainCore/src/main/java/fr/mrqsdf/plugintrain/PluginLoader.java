package fr.mrqsdf.plugintrain;

import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.*;
import java.util.*;
import java.util.jar.*;
import org.yaml.snakeyaml.Yaml;

public class PluginLoader {
    private static final Path PLUGIN_DIR = Paths.get("plugins");

    public List<IModelPlugin> load() throws Exception {
        List<IModelPlugin> loaded = new ArrayList<>();
        System.out.println(PLUGIN_DIR.toAbsolutePath());
        if (!Files.isDirectory(PLUGIN_DIR)) return loaded;
        System.out.println("Recherche de plugins dans le répertoire : " + PLUGIN_DIR.toAbsolutePath());

        try (DirectoryStream<Path> jars = Files.newDirectoryStream(PLUGIN_DIR, "*.jar")) {
            System.out.print("Plugins trouvés :");
            for (Path jarPath : jars) {
                System.out.println(" - " + jarPath.getFileName());
                try (JarFile jar = new JarFile(jarPath.toFile())) {
                    JarEntry entry = jar.getJarEntry("model.yml");
                    if (entry == null) continue;
                    System.out.println("Chargement du plugin : " + jarPath.getFileName());

                    try (InputStream in = jar.getInputStream(entry)) {
                        Map<String, Object> yaml =
                                new Yaml().load(in);                      // SnakeYAML
                        String mainClass = (String) yaml.get("main-class");

                        URL[] urls = { jarPath.toUri().toURL() };
                        try (URLClassLoader cl = new URLClassLoader(urls, getClass().getClassLoader())) {
                            Class<?> cls = cl.loadClass(mainClass);
                            if (!IModelPlugin.class.isAssignableFrom(cls)) continue;

                            IModelPlugin plugin = (IModelPlugin) cls.getDeclaredConstructor().newInstance();
                            loaded.add(plugin);
                        }
                    }
                }
            }
        }
        return loaded;
    }
}
