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

import cpw.mods.fml.common.Loader;
import klaxon.klaxon.goverlays.Backend;

public class Compat {

    public static final boolean GT5U = Loader.isModLoaded("gregtech");
    public static final boolean HBM_NTM = Loader.isModLoaded("hbm");

    public static Backend BACKEND = GT5U ? Backend.GT5U : HBM_NTM ? Backend.HBM_NTM : Backend.NONE;
    // This only reflects the client's state. We should still be able to connect to enabled servers and working
    public static boolean ENABLED = true;
}
