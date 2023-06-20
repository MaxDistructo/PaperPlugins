package io.dedyn.engineermantra.core;

import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

abstract class IPluginNBTKey {
    NamespacedKey key = null;

    /**
     * The init method MUST initialize the key to a non-null value when provided the
     * java plugin instance.
     * @param instance The instance of the plugin that the key is being initialized for
     */
    abstract public void init(JavaPlugin instance);

    public NamespacedKey getKey(){
        return key;
    }
}
