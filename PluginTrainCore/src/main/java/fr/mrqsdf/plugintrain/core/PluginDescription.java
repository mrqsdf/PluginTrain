package fr.mrqsdf.plugintrain.core;


import java.util.List;
import java.util.regex.*;
import org.yaml.snakeyaml.Yaml;
import java.io.InputStream;
import java.util.Map;

public final class PluginDescription {
    public final String name;
    public final String mainClass;
    public final String version;
    public final String apiVersion;
    public final List<String> depends;

    private static final Pattern DEP_PATTERN =
            Pattern.compile("^\\s*([\\w-]+)(?:\\s*>=\\s*([\\d.]+))?\\s*$");

    @SuppressWarnings("unchecked")
    public PluginDescription(InputStream yamlStream) {
        Map<String, Object> map = new Yaml().load(yamlStream);
        name       = (String) map.get("name");
        mainClass  = (String) map.get("main-class");
        version    = (String) map.getOrDefault("version", "0.0.0");
        apiVersion = (String) map.getOrDefault("api-version", "1.0");
        depends    = (List<String>) map.getOrDefault("depends", List.of());
    }

    /** Extrait simplement le nom d’un élément depends ("logger >=1.1" -> "logger"). */
    public static String depName(String raw) {
        var m = DEP_PATTERN.matcher(raw);
        return m.matches() ? m.group(1) : raw.trim();
    }
}
