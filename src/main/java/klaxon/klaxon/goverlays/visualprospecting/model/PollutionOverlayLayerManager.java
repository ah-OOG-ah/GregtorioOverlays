package klaxon.klaxon.goverlays.visualprospecting.model;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;

import com.sinthoras.visualprospecting.Utils;
import com.sinthoras.visualprospecting.integration.model.layers.LayerManager;
import com.sinthoras.visualprospecting.integration.model.locations.ILocationProvider;

import klaxon.klaxon.goverlays.PollutionFetcher;

public class PollutionOverlayLayerManager extends LayerManager {

    public static final PollutionOverlayLayerManager instance = new PollutionOverlayLayerManager();

    private int oldMaxChunkX = 0;
    private int oldMaxChunkZ = 0;
    private int oldMinChunkX = 0;
    private int oldMinChunkZ = 0;

    public PollutionOverlayLayerManager() {
        super(PollutionOverlayButtonManager.instance);
    }

    // TODO: Force updates on pollution tick
    @Override
    protected boolean needsRegenerateVisibleElements(int minBlockX, int minBlockZ, int maxBlockX, int maxBlockZ) {

        final int minChunkX = Utils.coordBlockToChunk(minBlockX);
        final int minChunkZ = Utils.coordBlockToChunk(minBlockZ);
        final int maxChunkX = Utils.coordBlockToChunk(maxBlockX);
        final int maxChunkZ = Utils.coordBlockToChunk(maxBlockZ);
        if (minChunkX != oldMaxChunkX || maxChunkX != oldMaxChunkZ
            || minChunkZ != oldMinChunkX
            || maxChunkZ != oldMinChunkZ) {
            oldMaxChunkX = minChunkX;
            oldMaxChunkZ = maxChunkX;
            oldMinChunkX = minChunkZ;
            oldMinChunkZ = maxChunkZ;
            return true;
        }
        return false;
    }

    /**
     * Generates list of location providers for rendering.
     * Takes in a rectangle bounded by minBlockX, maxBlockX, minBlockZ, and maxBlockZ.
     * This assumes the player is in the dimension being mapped.
     */
    @Override
    protected List<? extends ILocationProvider> generateVisibleElements(int minBlockX, int minBlockZ, int maxBlockX,
        int maxBlockZ) {

        // Convert to chunk coords
        final int minChunkX = Utils.coordBlockToChunk(minBlockX);
        final int minChunkZ = Utils.coordBlockToChunk(minBlockZ);
        final int maxChunkX = Utils.coordBlockToChunk(maxBlockX);
        final int maxChunkZ = Utils.coordBlockToChunk(maxBlockZ);

        // Get dimension
        final int playerDimensionId = Minecraft.getMinecraft().thePlayer.dimension;

        ArrayList<PollutionChunkLocation> pollutionChunkLocations = new ArrayList<>();

        // Go through chunks one at a time
        for (int chunkX = minChunkX; chunkX <= maxChunkX; chunkX += 1) {
            for (int chunkZ = minChunkZ; chunkZ <= maxChunkZ; chunkZ += 1) {

                // Get the pollution chunk and add it if it exists
                final PollutionChunkPosition pollutionChunk = PollutionFetcher
                    .getByChunkAndDimClient(playerDimensionId, chunkX, chunkZ);
                if (pollutionChunk != null && pollutionChunk.pollution > 0) {
                    pollutionChunkLocations.add(new PollutionChunkLocation(pollutionChunk));
                }
            }
        }

        return pollutionChunkLocations;
    }
}
