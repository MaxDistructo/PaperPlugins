package io.dedyn.engineermantra.chunkgen;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;

public class ChunkGenerator {

    public static class ChunkGeneratorThread extends Thread {
        private Plugin plugin;
        private int radius;

        public ChunkGeneratorThread(Plugin plugin, int radius) {
            this.plugin = plugin;
            this.radius = radius;
        }


        void generateChunk(int x, int y) {
            World world = plugin.getServer().getWorld("world");
            Chunk chunk = world.getChunkAt(x, y);
            if (!chunk.isGenerated()) {
                world.loadChunk(x, y, true);
                world.unloadChunk(x, y);
            }
        }

        @Override
        public void run() {
            //Chunks are 16x16 so we can increment by 16 each time to speed it up a bit.
            int i = 0;
            while (i < radius) {
                if (plugin.getServer().getTPS()[1] >= 16 && plugin.getServer().getOnlinePlayers().size() <= 5) {
                    generateChunk(i, i);
                    generateChunk(i, -i);
                    generateChunk(-i, i);
                    generateChunk(-i, -i);
                    i += 16;
                } else {
                    //Thread wait while we wait for server lag to resolve. This waits 5 minutes as we are listening
                    //to the 5 minute TPS for checking if we should continue.
                    try {
                        Thread.sleep(300000);
                    } catch (InterruptedException ignored) {
                    }
                }
            }
        }
    }
}
