/**
 * This file is part of GregtorioOverlays - a mod to put pollution on the map.
 * Copyright (C) 2022, 2024 ah-OOG-ah
 * Copyright (C) 2026, Algent
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

package klaxon.klaxon.goverlays.navigator.journeymap;

import static klaxon.klaxon.goverlays.GregtorioOverlays.MODID;

import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.Collections;

import journeymap.api.v2.client.display.ImageOverlay;
import journeymap.api.v2.client.model.MapImage;
import journeymap.api.v2.common.Context;
import journeymap.api.v2.common.util.BlockPos;
import klaxon.klaxon.goverlays.Constants;
import klaxon.klaxon.goverlays.navigator.Location;

public final class PollutionImageOverlay {

    private PollutionImageOverlay() {}

    public static Collection<ImageOverlay> create(Location location) {
        if (location.getPollution() <= 0) return Collections.emptyList();

        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        image.setRGB(0, 0, location.getAlpha() << 24 | location.getColor());

        int x = (int) Math.floor(location.getBlockX());
        int z = (int) Math.floor(location.getBlockZ());
        ImageOverlay overlay = new ImageOverlay(
            MODID,
            new BlockPos(x, 64, z),
            new BlockPos(x + Constants.CHUNK_SIZE, 64, z + Constants.CHUNK_SIZE),
            new MapImage(image).setBlur(false));
        overlay.setDimension(location.getDimensionId())
            .setOverlayGroupName(Location.class.getName())
            .setActiveUIs(Context.UI.Fullscreen, Context.UI.Minimap);
        return Collections.singletonList(overlay);
    }
}
