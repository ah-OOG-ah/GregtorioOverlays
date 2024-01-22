package klaxon.klaxon.goverlays.visualprospecting.model;

import com.sinthoras.visualprospecting.integration.model.locations.ILocationProvider;

/**
 * Holds a PollutionChunkPosition as ILocationProvider.
 * Not sure why VisualProspecting has this distiction, it's probably important.
 */
public class PollutionChunkLocation implements ILocationProvider {

    private final PollutionChunkPosition pollutionChunkPosition;
    private final int pollutionChange;

    public PollutionChunkLocation(PollutionChunkPosition pollutionChunkPosition) {

        this.pollutionChunkPosition = pollutionChunkPosition;
        pollutionChange = pollutionChunkPosition.getPollutionChange();
    }

    @Override
    public int getDimensionId() {

        return pollutionChunkPosition.dimensionId;
    }

    @Override
    public double getBlockX() {

        return pollutionChunkPosition.getBlockX() + 0.5;
    }

    @Override
    public double getBlockZ() {

        return pollutionChunkPosition.getBlockZ() + 0.5;
    }

    public int getPollutionChange() {

        return pollutionChange;
    }

    public int getPollution() {

        return pollutionChunkPosition.pollution;
    }
}
