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

package klaxon.klaxon.goverlays.utils;

public class Numeric {

    /**
     * Takes log base of num
     *
     * @param num  Number to log
     * @param base Base to log by
     * @return Double representing log base of num
     */
    public static double log(double num, double base) {

        return Math.log(num) / Math.log(base);
    }

    public static double log(long num, long base) {

        return log(num, (double) base);
    }

    public static double log(int num, int base) {

        return log(num, (double) base);
    }

    /**
     * WARNING: THIS IS NOT ACCURATE!
     * Floats and doubles are innacurate by nature, use a dedicated decimal class if you care.
     * I don't, this will be within one of whatever the last decimal is.
     * i.e. if you get 8.67 the correct answer is within +-0.01.
     *
     * @param num
     * @param decimals
     * @return
     */
    public static double round(double num, int decimals) {

        return Math.round(num * Math.pow(10, decimals)) / Math.pow(10, decimals);
    }
}
