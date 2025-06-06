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

package klaxon.klaxon.goverlays.compat;

import static klaxon.klaxon.goverlays.GregtorioOverlays.ticksPerUpdate;
import static klaxon.klaxon.goverlays.mixins.late.gregtech.PollutionAccessor.getCycleLen;

import gregtech.GTMod;

/**
 * This class should only be loaded when GT5u is loaded!
 */
public class GT5U {

    public static boolean isPollutionOn() {
        return GTMod.gregtechproxy.mPollution;
    }

    public static void preInit() {
        ticksPerUpdate = getCycleLen();
    }
}
