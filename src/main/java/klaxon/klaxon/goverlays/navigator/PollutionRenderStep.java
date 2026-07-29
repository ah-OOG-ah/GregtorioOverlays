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

package klaxon.klaxon.goverlays.navigator;

import static klaxon.klaxon.goverlays.config.GOConfig.alwaysShowAmt;

import net.minecraft.client.Minecraft;

import com.gtnewhorizons.navigator.api.model.steps.UniversalRenderStep;
import com.gtnewhorizons.navigator.api.util.DrawUtils;
import com.gtnewhorizons.navigator.api.util.Util;

import klaxon.klaxon.goverlays.Constants;
import klaxon.klaxon.goverlays.utils.FancyText;

public class PollutionRenderStep extends UniversalRenderStep<Location> {

    public PollutionRenderStep(Location location) {
        super(location);
        setSize(Constants.CHUNK_SIZE);
        setFontScale(0.2);
    }

    @Override
    public void draw(double x, double y, float drawScale, double zoom) {
        int pollution = location.getPollution();
        if (pollution <= 0) return;

        if (!isJourneyMap || !Util.isJourneyMapV6Installed()) {
            DrawUtils.drawRect(x, y, getAdjustedWidth(), getAdjustedHeight(), location.getColor(), location.getAlpha());
        }
        if (alwaysShowAmt) {
            double labelScale = isXaero ? getFontScale() * 0.7 : getFontScale() * getZoomScale(1.2, 3, 3, 5);
            DrawUtils.drawLabel(
                FancyText.formatPollution(pollution),
                x + getAdjustedWidth() / 2,
                y + (getAdjustedHeight() - Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT * labelScale) / 2,
                Constants.TEXT_COLOR,
                Constants.TEXT_BG_COLOR,
                true,
                labelScale);
        }
    }
}
