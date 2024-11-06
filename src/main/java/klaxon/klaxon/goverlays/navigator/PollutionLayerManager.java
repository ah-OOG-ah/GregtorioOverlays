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

import org.jetbrains.annotations.Nullable;

import com.gtnewhorizons.navigator.api.model.SupportedMods;
import com.gtnewhorizons.navigator.api.model.layers.LayerManager;
import com.gtnewhorizons.navigator.api.model.layers.LayerRenderer;
import com.gtnewhorizons.navigator.api.model.locations.ILocationProvider;

import klaxon.klaxon.goverlays.GregtorioOverlays;
import klaxon.klaxon.goverlays.navigator.journeymap.JMPollutionLocation;
import klaxon.klaxon.goverlays.navigator.journeymap.PollutionLayerRenderer;
import klaxon.klaxon.goverlays.utils.ChunkPos;

public class PollutionLayerManager extends LayerManager {

    public static final PollutionLayerManager INSTANCE = new PollutionLayerManager();
    private static SupportedMods BACKEND;

    public PollutionLayerManager() {
        super(PollutionButtonManager.INSTANCE);
    }

    @Nullable
    @Override
    protected LayerRenderer addLayerRenderer(LayerManager manager, SupportedMods mod) {
        BACKEND = mod;
        if (mod == SupportedMods.JourneyMap) {
            return PollutionLayerRenderer.INSTANCE;
        }
        BACKEND = null;
        return null;
    }

    @Override
    @Nullable
    protected ILocationProvider generateLocation(int cx, int cz, int dimID) {
        final long key = ChunkPos.pack(cx, cz);
        final int pollution = GregtorioOverlays.proxy.pollution.getCache(dimID)
            .get(key);
        if (pollution == 0) return null;
        if (BACKEND == null) return null;
        return new JMPollutionLocation(dimID, key);
    }
}
