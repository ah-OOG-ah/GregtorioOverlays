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

import static com.gtnewhorizons.navigator.api.util.Util.coordBlockToChunk;
import static klaxon.klaxon.goverlays.utils.ChunkPos.pack;

import java.util.List;

import net.minecraft.client.Minecraft;

import org.jetbrains.annotations.Nullable;

import com.gtnewhorizons.navigator.api.model.SupportedMods;
import com.gtnewhorizons.navigator.api.model.layers.LayerManager;
import com.gtnewhorizons.navigator.api.model.layers.LayerRenderer;
import com.gtnewhorizons.navigator.api.model.locations.ILocationProvider;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import klaxon.klaxon.goverlays.GregtorioOverlays;
import klaxon.klaxon.goverlays.navigator.journeymap.PollutionLayerRenderer;

public class PollutionLayerManager extends LayerManager {

    public static final PollutionLayerManager INSTANCE = new PollutionLayerManager();

    public PollutionLayerManager() {
        super(PollutionButtonManager.INSTANCE);
    }

    @Nullable
    @Override
    protected LayerRenderer addLayerRenderer(LayerManager manager, SupportedMods mod) {
        if (mod == SupportedMods.JourneyMap) return PollutionLayerRenderer.INSTANCE;
        return null;
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
        final int minCX = coordBlockToChunk(minBlockX);
        final int minCZ = coordBlockToChunk(minBlockZ);
        final int maxCX = coordBlockToChunk(maxBlockX);
        final int maxCZ = coordBlockToChunk(maxBlockZ);

        // Get dimension
        final int dimId = Minecraft.getMinecraft().thePlayer.dimension;
        final var pollution = GregtorioOverlays.proxy.pollution.getCache(dimId);
        final var locations = new ObjectArrayList<PollutionChunkLocation>();

        if (pollution.isEmpty()) return locations;

        // Go through chunks one at a time
        for (int cX = minCX; cX <= maxCX; ++cX) {
            for (int cZ = minCZ; cZ <= maxCZ; ++cZ) {
                // Get the pollution chunk and add it if it exists
                final long k = pack(cX, cZ);
                final int p = pollution.get(k);
                if (p > 0) {
                    locations.add(new PollutionChunkLocation(dimId, k, p));
                }
            }
        }

        return locations;
    }
}
