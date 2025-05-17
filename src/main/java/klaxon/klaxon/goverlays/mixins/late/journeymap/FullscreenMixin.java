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

import journeymap.client.ui.component.JmUI;
import journeymap.client.ui.fullscreen.Fullscreen;
import klaxon.klaxon.goverlays.config.GOConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("UnusedMixin")
@Mixin(Fullscreen.class)
public abstract class FullscreenMixin extends JmUI {

    public FullscreenMixin(String title) {
        super(title);
    }

    @Inject(method = "keyTyped", at = @At("HEAD"))
    protected void keyTyped(char c, int i, CallbackInfo ci) {
        if (isPressed(toggleLabels)) {
            GOConfig.alwaysShowAmt = !GOConfig.alwaysShowAmt;
        }
    }
}
