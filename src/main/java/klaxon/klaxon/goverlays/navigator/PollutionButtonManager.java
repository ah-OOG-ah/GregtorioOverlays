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

package klaxon.klaxon.goverlays.navigator;

import static klaxon.klaxon.goverlays.GregtorioOverlays.MODID;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import com.gtnewhorizons.navigator.api.model.SupportedMods;
import com.gtnewhorizons.navigator.api.model.buttons.ButtonManager;

public class PollutionButtonManager extends ButtonManager {

    public static final PollutionButtonManager INSTANCE = new PollutionButtonManager();
    private static final ResourceLocation ICON_LOC = new ResourceLocation(MODID, "textures/icons/pollution.png");

    @Override
    public ResourceLocation getIcon(SupportedMods mod, String theme) {
        return ICON_LOC;
    }

    @Override
    public String getButtonText() {
        return StatCollector.translateToLocal("goverlays.button.pollution");
    }
}
