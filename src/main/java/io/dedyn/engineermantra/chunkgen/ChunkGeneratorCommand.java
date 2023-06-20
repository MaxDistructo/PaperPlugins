package io.dedyn.engineermantra.chunkgen;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ChunkGeneratorCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(label.equals("start"))
        {
            if(ChunkgenPlugin.perms.has(sender, "chunkgen.start"))
            {
                ChunkgenPlugin.thread.notify();
            }
            else{
                sender.sendPlainMessage("You do not have permission to run this command.");
            }
        }
        if(label.equals("stop"))
        {
            if(ChunkgenPlugin.perms.has(sender, "chunkgen.stop"))
            {
                try {
                    ChunkgenPlugin.thread.wait();
                } catch (InterruptedException ignored) {}
            }
            else{
                sender.sendPlainMessage("You do not have permission to run this command.");
            }
        }
        return true;
    }
}
