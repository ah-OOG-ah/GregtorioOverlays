package klaxon.klaxon.goverlays.visualprospecting.model;

import java.io.Serializable;

import com.sinthoras.visualprospecting.Utils;

/**
 * This holds the position, dimension, and pollution level of a chunk.
 */
public class PollutionChunkPosition implements Serializable {

    public final int dimensionId;
    public final int chunkX;
    public final int chunkZ;
    public final int pollution;

    public PollutionChunkPosition(int dimensionId, int chunkX, int chunkZ, int pollution) {

        this.dimensionId = dimensionId;
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.pollution = pollution;
    }

    public int getBlockX() {

        return Utils.coordChunkToBlock(chunkX);
    }

    public int getBlockZ() {

        return Utils.coordChunkToBlock(chunkZ);
    }

    /**
     * TODO: Return pollution delta for last tick/second/minute/hour
     *
     * @return Current pollution in chunk.
     */
    public int getPollutionChange() {

        return pollution;
    }

    public boolean equals(PollutionChunkPosition other) {
        return dimensionId == other.dimensionId && chunkX == other.chunkX
            && chunkZ == other.chunkZ
            && pollution == other.pollution;
    }
}
