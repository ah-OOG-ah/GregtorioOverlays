package klaxon.klaxon.goverlays;

import java.util.HashMap;

import javax.annotation.Nullable;

import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;

import org.apache.commons.lang3.tuple.MutablePair;

import gregtech.common.GT_Pollution;
import gregtech.common.render.GT_PollutionRenderer;
import klaxon.klaxon.goverlays.visualprospecting.model.PollutionChunkPosition;

public class PollutionFetcher {

    /**
     * Returns PollutionChunkPosition for a given chunk and dimension. Returns null if no pollution is found.
     * This uses GT_Pollution.getPollution, which on the client-side only uses the client world.
     * I might keep this for a low-network mode, but ideally I want to get EVERY polluted chunk.
     *
     * @param dimensionId Dimension ID
     * @param chunkX      X-coord of chunk
     * @param chunkZ      Z-coord of chunk
     * @return Null or the chunk
     */
    @Nullable
    public static PollutionChunkPosition getByChunkAndDimCommon(int dimensionId, int chunkX, int chunkZ) {

        int pollution = 0;

        // Get from the world.
        // Check if the world exists
        WorldServer world = DimensionManager.getWorld(dimensionId);
        if (world == null) {

            // If not, use the client side
            pollution = GT_PollutionRenderer.getKnownPollution(chunkX << 4, chunkZ << 4);
        } else {

            // Apparently this calls the server method
            // I'm not sure how I feel about that
            pollution = GT_Pollution.getPollution(world, chunkX, chunkZ);
        }
        if (pollution > 0) {

            return new PollutionChunkPosition(dimensionId, chunkX, chunkZ, pollution);
        } else {

            return null;
        }
    }

    @Nullable
    public static PollutionChunkPosition getByChunkAndDimClient(int dimensionId, int chunkX, int chunkZ) {

        // We're clientside, so normal means are less effective BUT we have a local cache
        // Try local world first
        PollutionChunkPosition chunk = getByChunkAndDimCommon(dimensionId, chunkX, chunkZ);
        if (chunk != null) {
            return chunk;
        }

        // Get from the cache. This is less accurate but needed outside of the client world.
        return PollutionCache.getChunk(dimensionId, new MutablePair<>(chunkX, chunkZ));
    }

    /**
     * No overrides or extra methods, this class is just for clarity
     */
    public static class PollutionCacheByDim extends HashMap<MutablePair<Integer, Integer>, PollutionChunkPosition> {
    }

    /**
     * This class has a pollution cache on the client-side; the server doesn't need one, it has the full loaded world.
     * It should recieve data in two modes: limited by the server, and unlimited for a given dimension.
     */
    public static class PollutionCache {

        // Pollution cache
        public static HashMap<Integer, PollutionCacheByDim> cache = new HashMap<>();

        /**
         * Dump a chunk into the pollution cache. If one with the same X and Z was already in the cache, it is replaced.
         *
         * @param chunk
         */
        public static void setChunk(PollutionChunkPosition chunk) {

            // Generate the key
            MutablePair<Integer, Integer> key = new MutablePair<>(chunk.chunkX, chunk.chunkZ);

            // Set the cache, or create and set it
            PollutionCache.cache.computeIfAbsent(chunk.dimensionId, integer -> new PollutionCacheByDim())
                .put(key, chunk);
        }

        /**
         * Get a chunk from the cache given it's X and Z values in a MutablePair.
         * If polluton in that dimension has not been loaded yet, return null.
         *
         * @param key
         */
        @Nullable
        public static PollutionChunkPosition getChunk(Integer dimensionId, MutablePair<Integer, Integer> key) {

            PollutionCacheByDim dimPollution = PollutionCache.cache.get(dimensionId);
            if (dimPollution == null) {

                return null;
            }
            PollutionChunkPosition chunk = dimPollution.get(key);
            return chunk;
        }
    }
}
