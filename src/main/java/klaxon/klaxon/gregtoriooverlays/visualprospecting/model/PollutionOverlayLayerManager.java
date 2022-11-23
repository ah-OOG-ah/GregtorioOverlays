package klaxon.klaxon.gregtoriooverlays.visualprospecting.model;

import com.sinthoras.visualprospecting.Utils;
import com.sinthoras.visualprospecting.integration.model.layers.LayerManager;
import com.sinthoras.visualprospecting.integration.model.locations.ILocationProvider;
import java.util.ArrayList;
import java.util.List;
import klaxon.klaxon.gregtoriooverlays.gregtech.PollutionFetcher;
import net.minecraft.client.Minecraft;

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
        if (minChunkX != oldMaxChunkX
                || maxChunkX != oldMaxChunkZ
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

    @Override
    protected List<? extends ILocationProvider> generateVisibleElements(
            int minBlockX, int minBlockZ, int maxBlockX, int maxBlockZ) {
        final int minChunkX = Utils.coordBlockToChunk(minBlockX);
        final int minChunkZ = Utils.coordBlockToChunk(minBlockZ);
        final int maxChunkX = Utils.coordBlockToChunk(maxBlockX);
        final int maxChunkZ = Utils.coordBlockToChunk(maxBlockZ);
        final int playerDimensionId = Minecraft.getMinecraft().thePlayer.dimension;

        ArrayList<PollutionChunkLocation> pollutionChunkLocations = new ArrayList<>();

        // Go through chunks one at a time
        for (int chunkX = minChunkX; chunkX <= maxChunkX; chunkX += 1) {
            for (int chunkZ = minChunkZ; chunkZ <= maxChunkZ; chunkZ += 1) {

                final PollutionChunkPosition pollutionChunk =
                        PollutionFetcher.getByChunkAndDim(playerDimensionId, chunkX, chunkZ);

                if (pollutionChunk.pollution > 0) {
                    pollutionChunkLocations.add(new PollutionChunkLocation(pollutionChunk));
                }
            }
        }

        return pollutionChunkLocations;
    }
}
