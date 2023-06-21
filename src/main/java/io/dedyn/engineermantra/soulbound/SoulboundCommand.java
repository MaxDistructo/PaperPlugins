package io.dedyn.engineermantra.soulbound.commands;

import io.dedyn.engineermantra.soulbound.Soulbound;

import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SoulboundCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(sender instanceof Player)
        {
            Player player = (Player)sender;
            if(label.equals("soulbind"))
            {
                if(!Soulbound.perms.has(sender, "soulbound.soulbind.self"))
                {
                    sender.sendPlainMessage("You do not have permssion to run this command.");
                }
                else {
                    if(Soulbound.perms.has(sender, "soulbound.soulbind.self.nocost") || Soulbound.econ.has((Player)sender, 100000)){
                        if(!Soulbound.perms.has(sender, "soulbound.soulbind.self.nocost")) {
                            Soulbound.econ.withdrawPlayer((Player) sender, 100000);
                            sender.sendPlainMessage("You have paid $100,000 to Soulbind your held item.");
                        }
                    }
                    else{
                        sender.sendPlainMessage("You do not have $100,000 to perform this operation");
                        return true;
                    }
                    ItemStack item = player.getInventory().getItemInMainHand();
                    item.getItemMeta().getPersistentDataContainer().set(Soulbound.key, PersistentDataType.BOOLEAN, true);
                    List<Component> lore = item.lore();
                    lore.add(Component.text("Soulbound I"));
                    item.lore(lore);
                    sender.sendPlainMessage("Held item has been soulbound");
                }
                return true;
            }
            else if(label.equals("gsoulbind"))
            {
                if(!Soulbound.perms.has(sender, "soulbound.soulbind.global")){
                    sender.sendPlainMessage("You do not have permssion to run this command.");
                    return true;
                }
                else {
                    ItemStack heldItem = player.getInventory().getItemInMainHand();
                    Soulbound.globalSoulboundItems.add(heldItem);
                    //This turns into a 2D array iteration of players and inventories
                    for (Player wplayer : player.getWorld().getPlayers()) {
                        for (ItemStack item : wplayer.getInventory().getContents()) {
                            if (item != null && item.equals(heldItem)) {
                                item.getItemMeta().getPersistentDataContainer().set(Soulbound.key, PersistentDataType.BOOLEAN, true);
                                List<Component> lore = item.lore();
                                lore.add(Component.text("Soulbound I"));
                                item.lore(lore);
                            }
                        }
                    }
                    sender.sendPlainMessage("Soulbound items for all players");
                }
                return true;

            }
        }
        sender.sendPlainMessage("Only players may run this command.");
        return true;
    }
}
