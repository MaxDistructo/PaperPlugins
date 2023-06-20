package io.dedyn.engineermantra.soulbound;

import io.dedyn.engineermantra.core.ISubPlugin;
import io.dedyn.engineermantra.core.PluginsCore;
import io.dedyn.engineermantra.soulbound.commands.SoulboundCommand;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;

public class Soulbound implements ISubPlugin {
    public static NamespacedKey key;
    public static Permission perms;
    public static Economy econ;
    public static List<ItemStack> globalSoulboundItems;
    public static JavaPlugin plugin;

    @Override
    public void onEnable(JavaPlugin plugin) {
        Soulbound.plugin = plugin;
        // Plugin startup logic
        //Start by loading the config for us. This contains the cached list of global soulbound items
        //TODO: Fix global item soulbind persistance. It only applies to all items that are in player's inventorys
        //TODO: at the time of the command being ran rather than being whenever the item is pulled out
        /*
        for (String item : plugin.getConfig().getStringList("items")){
            new HashMap<String,Object>();
            globalSoulboundItems.add(ItemStack.deserialize(new HashMap<String,Object>(item)));
        }
        */
        //The listener uses this so setup our namespace
        key = PluginsCore.nbtKey.requestKey("soulbound");
        //Then setup our listener to catch the events that matter to us performing soulbinding on player death
        //and enforcing our global soulbinds
        plugin.getServer().getPluginManager().registerEvents(new SoulboundListener(), plugin);

        //Now that we are operational, setup our integrations and enable the command to add more soulbound items
        //This is so players do not lose money until we are ready to add new items
        if(PluginsCore.perms != null && PluginsCore.econ != null){
            plugin.getCommand("soulbind").setExecutor(new SoulboundCommand());
        }
    }

    @Override
    public void onDisable() {
        //On close out, dump the list of soulbound items to the config file so we don't lose the new settings
        plugin.getConfig().set("items", globalSoulboundItems);
        plugin.saveConfig();
    }
}
