package fr.mrqsdf.plugintrain.api;

public interface IModelPlugin {

    void printText();

    void onLoad() throws Exception;
    void onEnable() throws Exception;
    void onDisable() throws Exception;

}
