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

package klaxon.klaxon.goverlays;

public class Constants {

    // Text font constants
    public static int TEXT_BG_COLOR = 0x000000;
    public static int TEXT_BG_ALPHA = 180;
    public static int TEXT_COLOR = 0xFFFFFF;
    public static int TEXT_ALPHA = 255;

    // Won't change unless I decide to do serious shenanigans later.
    public static int CHUNK_SIZE = 16;

    // These values are based on the pollution effects in GT5U-GTNH
    // They are undergoing a major rework and still subject to change
    // This is current as of 5.09.41.132-dev
    public enum EffectSteps {

        POLLUTION_1(500_000), // Smog
        POLLUTION_2(750_000), // Poison
        POLLUTION_3(1_000_000), // Dying Plants
        POLLUTION_MAX(1_500_000); // Sour Rain

        public final int val;

        EffectSteps(int val) {
            this.val = val;
        }
    }
}
