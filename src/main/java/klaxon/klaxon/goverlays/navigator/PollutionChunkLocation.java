/**
 * This file is part of GregtorioOverlays - a mod to put pollution on the map.
 * Copyright (C) 2022, 2024 ah-OOG-ah
 *
 * GregtorioOverlays is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * GregtorioOverlays is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

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
