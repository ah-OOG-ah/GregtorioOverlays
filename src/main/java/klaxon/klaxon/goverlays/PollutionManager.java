/**
 * This file is part of GregtorioOverlays - a mod to put pollution on the map.
 * Copyright (C) 2022-2024 ah-OOG-ah
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

package klaxon.klaxon.goverlays;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap;
import klaxon.klaxon.goverlays.network.pollution.PollutionMessage;

public class PollutionManager {

    // The master cache of all pollution everywhere
    private final Int2ObjectOpenHashMap<Long2IntOpenHashMap> pollutionCache = new Int2ObjectOpenHashMap<>();

    public Long2IntOpenHashMap getCache(int dim) {
        return pollutionCache.computeIfAbsent(dim, i -> new Long2IntOpenHashMap());
    }

    public void updateCache(int dim, long cpos, int pollution) {
        getCache(dim).put(cpos, pollution);
    }

    /**
     * Sends the whole pollution cache in a packet for the client. Should only be called on the server side. While the
     * current implementation includes every chunk, this is not a guarantee! The packet is only guaranteed to hold
     * modified chunks since the last update.
     */
    public void updateDim(int dimID) {
        GregtorioOverlays.dispatcher.sendToDimension(new PollutionMessage(dimID, getCache(dimID)), dimID);
    }
}
