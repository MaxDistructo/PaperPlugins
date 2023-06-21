package io.dedyn.engineermantra.core;

import io.dedyn.engineermantra.chunkgen.ChunkgenPlugin;
import io.dedyn.engineermantra.soulbound.SoulboundPlugin;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class PluginsCore extends JavaPlugin {
    public static Permission perms;
    public static Economy econ;
    public static NBTKey nbtKey = new NBTKey();
    static ArrayList<ISubPlugin> plugins = new ArrayList<>();

    @Override
    public void onEnable() {

        //Start by initializing all sub-plugin instances in our plugins array
        plugins.add(new SoulboundPlugin());
        plugins.add(new ChunkgenPlugin());

        //Start by loading the config for us.
        this.saveDefaultConfig();
        nbtKey.init(this);

        //Now that we are operational, setup our integrations
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
