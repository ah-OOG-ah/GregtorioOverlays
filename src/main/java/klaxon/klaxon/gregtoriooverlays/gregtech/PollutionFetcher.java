package klaxon.klaxon.gregtoriooverlays.gregtech;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.common.GT_Pollution;
import java.util.HashMap;
import javax.annotation.Nullable;
import klaxon.klaxon.gregtoriooverlays.GregtorioOverlays;
import klaxon.klaxon.gregtoriooverlays.visualprospecting.model.PollutionChunkPosition;
import net.minecraftforge.common.DimensionManager;
import org.apache.commons.lang3.tuple.MutablePair;

public class PollutionFetcher {

    /**
     * Returns PollutionChunkPosition for a given chunk and dimension. Returns null if no pollution is found.
     * This uses GT_Pollution.getPollution, which on the client-side only uses the client world.
     * I might keep this for a low-network mode, but ideally I want to get EVERY polluted chunk.
     * @param dimensionId
     * @param chunkX
     * @param chunkZ
     * @return
     */
    @Nullable
    public static PollutionChunkPosition getByChunkAndDimCommon(int dimensionId, int chunkX, int chunkZ) {

        // Get from the world.
        int pollution = GT_Pollution.getPollution(DimensionManager.getWorld(dimensionId), chunkX, chunkZ);
        if (pollution > 0) {

            // GregtorioOverlays.info("Pulling pollution from world!");
            return new PollutionChunkPosition(dimensionId, chunkX, chunkZ, pollution);
        } else {

            return null;
        }
    }

    @Nullable
    @SideOnly(Side.CLIENT)
    public static PollutionChunkPosition getByChunkAndDimClient(int dimensionId, int chunkX, int chunkZ) {

        // We're clientside, so normal means are less effective BUT we have a local cache
        // Try the local cache first
        PollutionChunkPosition chunk =
                PollutionCache.getChunk(dimensionId, new MutablePair<Integer, Integer>(chunkX, chunkZ));
        if (chunk != null) {
            return chunk;
        }

        // Get from the client world. This is less effective but needed in-between server updates.
        // It's also more accurate within range
        return getByChunkAndDimCommon(dimensionId, chunkX, chunkZ);
    }

    /**
     * No overrides or extra methods, this class is just for clarity
     */
    public class PollutionCacheByDim extends HashMap<MutablePair<Integer, Integer>, PollutionChunkPosition> {}

    /**
     * This class has a pollution cache on the client-side; the server doesn't need one, it has the full loaded world.
     * It should recieve data in two modes: limited by the server, and unlimited for a given dimension.
     */
    @SideOnly(Side.CLIENT)
    public static class PollutionCache {

        // Pollution cache
        public static HashMap<Integer, PollutionCacheByDim> cache = new HashMap<>();

        /**
         * Dump a chunk into the pollution cache. If one with the same X and Z was already in the cache, it is replaced.
         * @param chunk
         */
        public static void setChunk(PollutionChunkPosition chunk) {

            MutablePair<Integer, Integer> key = new MutablePair<Integer, Integer>(chunk.chunkX, chunk.chunkZ);
            PollutionCache.cache.get(chunk.dimensionId).put(key, chunk);
        }

        /**
         * Get a chunk from the cache given it's X and Z values in a MutablePair.
         * If polluton in that dimension has not been loaded yet, return null.
         * @param key
         */
        public static PollutionChunkPosition getChunk(Integer dimensionId, MutablePair<Integer, Integer> key) {

            PollutionCacheByDim dimPollution = PollutionCache.cache.get(dimensionId);
            if (dimPollution == null) {

                // GregtorioOverlays.info("Dim pollution cache is null!");
                return null;
            }
            PollutionChunkPosition chunk = dimPollution.get(key);
            GregtorioOverlays.info("Pollution: " + String.valueOf(chunk.pollution));
            return chunk;
        }
    }
}
