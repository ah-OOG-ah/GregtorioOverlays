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

package klaxon.klaxon.goverlays.config;

import static klaxon.klaxon.goverlays.GregtorioOverlays.MODID;

import com.gtnewhorizon.gtnhlib.config.Config;

@Config(modid = MODID)
public class GOConfig {

    @Config.Comment("Always show pollution amount")
    @Config.DefaultBoolean(false)
    public static boolean alwaysShowAmt;

    @Config.Comment("Use gibbl as the base unit instead of bbl")
    @Config.DefaultBoolean(true)
    public static boolean useGibblBase;

    @Config.Comment("Use binary prefixes instead of decimal")
    @Config.DefaultBoolean(false)
    public static boolean useBinaryPrefixes;

    @Config.Comment("The maximum alpha value for pollution")
    @Config.DefaultFloat(200F)
    @Config.RangeFloat(min = 0F, max = 255F)
    public static float maxAlpha;

    @Config.Comment("The number of different steps to display.")
    @Config.DefaultInt(8)
    @Config.RangeInt(min = 1, max = 255)
    public static int alphaSteps;

    @Config.Comment("The hex color to display for pollution")
    @Config.DefaultString("0xFF0000")
    public static String color;
}
