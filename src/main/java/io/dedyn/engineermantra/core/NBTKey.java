package io.dedyn.engineermantra.core;

import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class NBTKey{

    HashMap<String, NamespacedKey> keys = new HashMap<>();
    JavaPlugin plugin;

    public void init(JavaPlugin plugin)
    {
        this.plugin = plugin;
    }
    public NamespacedKey requestKey(String name)
    {
        if(keys.containsKey(name))
        {
            return keys.get(name);
        }
        NamespacedKey key = new NamespacedKey(plugin, name);
        keys.put(name, key);
        return key;
    }
}
