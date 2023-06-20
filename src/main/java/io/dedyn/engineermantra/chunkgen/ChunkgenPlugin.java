package io.dedyn.engineermantra.chunkgen;

import io.dedyn.engineermantra.core.ISubPlugin;
import io.dedyn.engineermantra.core.PluginsCore;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.plugin.java.JavaPlugin;

public class ChunkgenPlugin implements ISubPlugin {
    public static JavaPlugin plugin;
    public static Permission perms;
    static ChunkGenerator.ChunkGeneratorThread thread;

    @Override
    public void onEnable(JavaPlugin pluginInstance) {
        plugin = pluginInstance;
        perms = PluginsCore.perms;
        thread = new ChunkGenerator.ChunkGeneratorThread(plugin, 30000);
        thread.start();
    }

    @Override
    public void onDisable() {

    }
}
