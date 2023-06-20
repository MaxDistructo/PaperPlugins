package io.dedyn.engineermantra.core;

import io.dedyn.engineermantra.soulbound.Soulbound;
import io.dedyn.engineermantra.soulbound.commands.SoulboundCommand;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PluginsCore extends JavaPlugin {
    public static Permission perms;
    public static Economy econ;
    public static NBTKey nbtKey = new NBTKey();
    static ArrayList<ISubPlugin> plugins = new ArrayList<>();

    @Override
    public void onEnable() {

        //Start by initializing all sub-plugin instances in our plugins array
        plugins.add(new Soulbound());

        //Start by loading the config for us. This contains the cached list of global soulbound items
        this.saveDefaultConfig();
        nbtKey.init(this);

        //Now that we are operational, setup our integrations and enable the command to add more soulbound items
        //This is so players do not lose money until we are ready to add new items
        setupPermissions();
        setupEconomy();

        //Since we have done all the prep work we need to do, start up all the sub-plugins.
        //They keep a pointer to the instance of us
        for(ISubPlugin plugin : plugins)
        {
            plugin.onEnable(this);
        }
    }

    private boolean setupEconomy(){
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }

    @Override
    public void onDisable() {
        for (ISubPlugin plugin : plugins)
        {
            plugin.onDisable();
        }
    }
}
