package io.dedyn.engineermantra.soulbound;

import io.papermc.paper.event.player.PlayerInventorySlotChangeEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;

public class SoulboundListener implements Listener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event)
    {
        ArrayList<ItemStack> toKeep = new ArrayList<>();
        for(ItemStack drop : event.getDrops()){
            if(drop.getItemMeta().getPersistentDataContainer().has(SoulboundPlugin.key)){
                if(drop.getItemMeta().getPersistentDataContainer().get(SoulboundPlugin.key, PersistentDataType.BOOLEAN).booleanValue())
                {
                    event.getItemsToKeep().add(drop);
                    toKeep.add(drop);
                }
            }
        }
        event.getDrops().removeAll(toKeep);
    }

    @EventHandler
    public void onInventoryUpdate(PlayerInventorySlotChangeEvent event)
    {
        //If the player pulls one of the globalSoulbound items into their inventory
        //and it's not soulbound, soulbind it
        if(SoulboundPlugin.globalSoulboundItems.contains(event.getNewItemStack())) {
            if (!event.getNewItemStack().getItemMeta().getPersistentDataContainer().has(SoulboundPlugin.key) ||
                    !event.getNewItemStack().getItemMeta().getPersistentDataContainer().get(SoulboundPlugin.key, PersistentDataType.BOOLEAN).booleanValue())
            {
                event.getNewItemStack().getItemMeta().getPersistentDataContainer().set(SoulboundPlugin.key, PersistentDataType.BOOLEAN, true);
            }
        }
    }
}
