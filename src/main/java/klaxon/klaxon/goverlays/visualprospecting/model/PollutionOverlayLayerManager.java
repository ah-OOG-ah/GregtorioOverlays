package klaxon.klaxon.goverlays.visualprospecting.model;

import static com.sinthoras.visualprospecting.Utils.chunkCoordsToKey;

import java.util.List;

import net.minecraft.client.Minecraft;

import com.sinthoras.visualprospecting.Utils;
import com.sinthoras.visualprospecting.integration.model.buttons.ButtonManager;
import com.sinthoras.visualprospecting.integration.model.layers.LayerManager;
import com.sinthoras.visualprospecting.integration.model.locations.ILocationProvider;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import klaxon.klaxon.goverlays.GregtorioOverlays;

public class PollutionOverlayLayerManager extends LayerManager {

    public static final ButtonManager buttonMgr = new ButtonManager("goverlays.button.pollution", "pollution");
    public static final PollutionOverlayLayerManager instance = new PollutionOverlayLayerManager();

    private int oldMaxChunkX = 0;
    private int oldMaxChunkZ = 0;
    private int oldMinChunkX = 0;
    private int oldMinChunkZ = 0;

    public PollutionOverlayLayerManager() {
        super(buttonMgr);
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
        final var pollution = GregtorioOverlays.proxy.pollution.getCache(playerDimensionId);
        final var locations = new ObjectArrayList<PollutionChunkLocation>();

        if (pollution.isEmpty()) return locations;

        // Go through chunks one at a time
        for (int chunkX = minChunkX; chunkX <= maxChunkX; ++chunkX) {
            for (int chunkZ = minChunkZ; chunkZ <= maxChunkZ; ++chunkZ) {
                // Get the pollution chunk and add it if it exists
                final long k = chunkCoordsToKey(chunkX, chunkZ);
                final int p = pollution.get(k);
                if (p > 0) {
                    locations.add(new PollutionChunkLocation(playerDimensionId, k, p));
                }
            }
        }

        return locations;
    }
}
