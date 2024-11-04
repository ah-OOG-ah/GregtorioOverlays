/**
 * This file is part of GregtorioOverlays - a mod to put pollution on the map.
 * Copyright (C) 2024 ah-OOG-ah
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

package klaxon.klaxon.goverlays.events;

import static klaxon.klaxon.goverlays.GregtorioOverlays.*;
import static klaxon.klaxon.goverlays.compat.Compat.ENABLED;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;

public class CommonEvents {

    @SubscribeEvent
    public void onServerTick(TickEvent.WorldTickEvent tickEvent) {
        if (tickEvent.side != Side.SERVER || !ENABLED) return;
        if (tickEvent.world.getTotalWorldTime() % ticksPerUpdate != ticksPerUpdate / 2) return;

        // Dispatch! Chunks in the cache should be updated on change by the update backend
        proxy.pollution.updateDim(tickEvent.world.provider.dimensionId);
    }
}
