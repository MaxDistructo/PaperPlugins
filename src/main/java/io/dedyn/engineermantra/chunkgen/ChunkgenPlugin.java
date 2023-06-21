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
        //The commands interact with the thread so don't enable the commands
        //until the thread is up
        ChunkGeneratorCommand command = new ChunkGeneratorCommand();
        plugin.getCommand("cgstart").setExecutor(command);
        plugin.getCommand("cgstop").setExecutor(command);
    }

    @Override
    public void onDisable() {
        //Shut down process, we need to interrupt the thread so it stops cleanly
        thread.interrupt();
        //Commands shouldn't be running at disable time since we disable it.
    }
}
