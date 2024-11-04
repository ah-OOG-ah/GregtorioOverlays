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

/**
 * As of right now, it's not possible to run GT5NH and HBM together, and GT6 doesn't have pollution, so there's no point
 * supporting multiple backends in parallel. My hope is that if GT6 adds pollution it'll cooperate with NTM so I don't
 * have to make another one...
 */
public enum Backend {
    GT5U,
    HBM_NTM, // shhhh
    NONE
}
