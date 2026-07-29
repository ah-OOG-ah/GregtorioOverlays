/**
 * This file is part of GregtorioOverlays - a mod to put pollution on the map.
 * Copyright (C) 2022, 2024 ah-OOG-ah
 * Copyright (C) 2026, Algent
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jetbrains.annotations.Nullable;

import com.gtnewhorizons.navigator.api.model.SupportedMods;
import com.gtnewhorizons.navigator.api.model.layers.LayerManager;
import com.gtnewhorizons.navigator.api.model.layers.LayerRenderer;
import com.gtnewhorizons.navigator.api.model.layers.UniversalLayerRenderer;
import com.gtnewhorizons.navigator.api.model.locations.ILocationProvider;
import com.gtnewhorizons.navigator.api.util.Util;

import it.unimi.dsi.fastutil.longs.Long2IntMap;
import it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap;
import klaxon.klaxon.goverlays.GregtorioOverlays;
import klaxon.klaxon.goverlays.navigator.journeymap.PollutionImageOverlay;
import klaxon.klaxon.goverlays.utils.ChunkPos;

public class PollutionLayerManager extends LayerManager {

    public static final PollutionLayerManager INSTANCE = new PollutionLayerManager();

    public PollutionLayerManager() {
        super(PollutionButtonManager.INSTANCE);
    }

    @Nullable
    @Override
    protected LayerRenderer addLayerRenderer(LayerManager manager, SupportedMods mod) {
        UniversalLayerRenderer renderer = new UniversalLayerRenderer(manager)
            .withRenderStep(location -> new PollutionRenderStep((Location) location));
        if (Util.isJourneyMapV6Installed()) {
            renderer.withJourneyMapV6Overlays(location -> PollutionImageOverlay.create((Location) location));
        }
        return renderer;
    }

    @Override
    @Nullable
    protected ILocationProvider generateLocation(int cx, int cz, int dimID) {
        final long key = ChunkPos.pack(cx, cz);
        final int pollution = GregtorioOverlays.proxy.pollution.getCache(dimID)
            .get(key);
        if (pollution == 0) return null;
        return new Location(dimID, key);
    }

    @Override
    protected Collection<? extends ILocationProvider> generateVisibleLocations(int minBlockX, int minBlockZ,
        int maxBlockX, int maxBlockZ, int dimension) {
        int minChunkX = Util.coordBlockToChunk(minBlockX);
        int minChunkZ = Util.coordBlockToChunk(minBlockZ);
        int maxChunkX = Util.coordBlockToChunk(maxBlockX);
        int maxChunkZ = Util.coordBlockToChunk(maxBlockZ);
        Long2IntOpenHashMap cache = GregtorioOverlays.proxy.pollution.getCache(dimension);
        long viewportChunks = ((long) maxChunkX - minChunkX + 1) * ((long) maxChunkZ - minChunkZ + 1);
        if (viewportChunks <= cache.size()) return null;

        List<Location> locations = new ArrayList<>();
        for (Long2IntMap.Entry entry : cache.long2IntEntrySet()) {
            long packedPos = entry.getLongKey();
            int chunkX = ChunkPos.getX(packedPos);
            int chunkZ = ChunkPos.getZ(packedPos);
            if (entry.getIntValue() > 0 && chunkX >= minChunkX
                && chunkX <= maxChunkX
                && chunkZ >= minChunkZ
                && chunkZ <= maxChunkZ) {
                locations.add(new Location(dimension, packedPos));
            }
        }
        return locations;
    }
}
