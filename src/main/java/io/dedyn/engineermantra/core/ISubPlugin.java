package io.dedyn.engineermantra.core;

import org.bukkit.plugin.java.JavaPlugin;

public interface ISubPlugin {
    void onEnable(JavaPlugin pluginInstance);
    void onDisable();
}
