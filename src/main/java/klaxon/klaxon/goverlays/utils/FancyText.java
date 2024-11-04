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

package klaxon.klaxon.goverlays.utils;

import klaxon.klaxon.goverlays.config.GOConfig;

public class FancyText {

    public enum PrefixType {
        SI,
        BINARY
    }

    public static String[] siPrefixes = { "", "k", "M", "G", "T", "P", "E", "Z", "Y", "R", "Q" };

    // robi- (Ri) and quebi- (Qi) aren't official yet as of Nov. 2022, adding them because why not
    public static String[] binaryPrefixes = { "", "Ki", "Mi", "Gi", "Ti", "Pi", "Ei", "Zi", "Yi", "Ri", "Qi" };

    /**
     * Converts pollution (stored as int in GT5u) to a formatted string
     */
    public static String formatPollution(int pollution) {

        String sPollution;
        String prefix;
        long lPollution = pollution;

        if (!GOConfig.useGibblBase) {

            // Break it down to bbl
            // Max value of pollution is ~2.1B, longs are actually long enough to store this
            lPollution *= 1_073_741_824L;
        }

        if (!GOConfig.useBinaryPrefixes) {

            // Figure out what SI power it should be in
            // 0 = none, 1 = kilo, 2 = Mega, etc.
            byte power = (byte) Math.floor(Numeric.log(lPollution, 1000));

            // Get displayed pollution
            // actual pollution = dPollution * 1000^power (units)
            double dPollution = lPollution / Math.pow(1000, power);

            // Printf my beloved
            // Format float, right pad with space, replace space with 0, trim if it's too long
            sPollution = String.format("%-#5f", dPollution)
                .replace(' ', '0')
                .substring(0, 5);

            // Get the prefix
            prefix = FancyText.siPrefixes[power];
        } else {

            // Get the binary power
            // 0 = none, 1 = ki, 2 = Mi, etc.
            byte power = (byte) Math.floor(Numeric.log(lPollution, 1024));

            // Get displayed pollution
            double dPollution = lPollution / Math.pow(1024, power);

            // Printf my beloved
            // Format float, right pad with space, replace space with 0, trim if it's too long
            sPollution = String.format("%-#5f", dPollution)
                .replace(' ', '0')
                .substring(0, 5);

            // Get prefix
            prefix = FancyText.binaryPrefixes[power];
        }

        if (!GOConfig.useGibblBase) {

            // Make the label
            sPollution += " " + prefix + "bbl";
        } else {

            // Make the label
            sPollution += prefix + " gibbl";
        }

        return sPollution;
    }
}
