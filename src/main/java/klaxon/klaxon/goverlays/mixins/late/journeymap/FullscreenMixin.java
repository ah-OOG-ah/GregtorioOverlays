/**
 * This file is part of GregtorioOverlays - a mod to put pollution on the map.
 * Copyright (C) 2024-2025 ah-OOG-ah
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

package klaxon.klaxon.goverlays.mixins.late.journeymap;

import static journeymap.client.Constants.isPressed;
import static klaxon.klaxon.goverlays.events.ClientProxy.toggleLabels;

import net.minecraft.client.gui.GuiScreen;

import org.spongepowered.asm.mixin.Mixin;

import journeymap.client.ui.fullscreen.Fullscreen;
import klaxon.klaxon.goverlays.config.GOConfig;

@SuppressWarnings("UnusedMixin")
@Mixin(Fullscreen.class)
public abstract class FullscreenMixin extends GuiScreen {

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        super.keyTyped(typedChar, keyCode);

        if (isPressed(toggleLabels)) {
            GOConfig.alwaysShowAmt = !GOConfig.alwaysShowAmt;
        }
    }
}
