package klaxon.klaxon.goverlays.navigator;

import static com.gtnewhorizons.navigator.api.util.Util.coordChunkToBlock;
import static klaxon.klaxon.goverlays.utils.ChunkPos.getX;
import static klaxon.klaxon.goverlays.utils.ChunkPos.getZ;

import com.gtnewhorizons.navigator.api.model.locations.ILocationProvider;

public class PollutionChunkLocation implements ILocationProvider {

    final int dimID;
    final long packedPos;
    final int pollution;

    public PollutionChunkLocation(int dimID, long packedPos, int pollution) {
        this.dimID = dimID;
        this.packedPos = packedPos;
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
