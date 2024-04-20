package klaxon.klaxon.goverlays.visualprospecting.model;

import static com.sinthoras.visualprospecting.Utils.coordChunkToBlock;
import static klaxon.klaxon.goverlays.utils.ChunkPos.getX;
import static klaxon.klaxon.goverlays.utils.ChunkPos.getZ;

import com.sinthoras.visualprospecting.integration.model.locations.ILocationProvider;

public class PollutionChunkLocation implements ILocationProvider {

    private final int dimID;
    private final long packedPos;
    private final int pollution;

    public PollutionChunkLocation(int dim, long pos, int pollution) {

        dimID = dim;
        packedPos = pos;
        this.pollution = pollution;
    }

    @Override
    public int getDimensionId() {
        return dimID;
    }

    @Override
    public double getBlockX() {
        return coordChunkToBlock(getX(packedPos)) + 0.5;
    }

    @Override
    public double getBlockZ() {
        return coordChunkToBlock(getZ(packedPos)) + 0.5;
    }

    public int getPollution() {
        return pollution;
    }
}
