package klaxon.klaxon.gregtoriooverlays.gregtech;

import klaxon.klaxon.gregtoriooverlays.visualprospecting.model.PollutionChunkPosition;

import gregtech.common.GT_Pollution;
import net.minecraftforge.common.DimensionManager;

public class PollutionFetcher {

    public static PollutionChunkPosition getByChunkAndDim(int dimensionId, int chunkX, int chunkZ) {

        int pollution = GT_Pollution.getPollution(DimensionManager.getWorld(dimensionId),
                                              chunkX,
                                              chunkZ);
        return new PollutionChunkPosition(dimensionId, chunkX, chunkZ, pollution);
    }
}
