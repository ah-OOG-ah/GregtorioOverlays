/**
 * This file is part of GregtorioOverlays - a mod to put pollution on the map.
 * Copyright (C) 2022-2024 ah-OOG-ah
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

import static java.lang.Math.min;
import static klaxon.klaxon.goverlays.Constants.EffectSteps.POLLUTION_MAX;
import static klaxon.klaxon.goverlays.GregtorioOverlays.proxy;
import static klaxon.klaxon.goverlays.config.GOConfig.alwaysShowAmt;
import static org.joml.Math.lerp;

import java.awt.geom.Point2D;

import com.gtnewhorizons.navigator.api.journeymap.drawsteps.JMRenderStep;

import journeymap.client.render.draw.DrawUtil;
import journeymap.client.render.map.GridRenderer;
import klaxon.klaxon.goverlays.Constants;
import klaxon.klaxon.goverlays.config.GOConfig;
import klaxon.klaxon.goverlays.navigator.Location;
import klaxon.klaxon.goverlays.utils.FancyText;

public class JMPollutionLocation extends Location implements JMRenderStep {

    public JMPollutionLocation(int dimID, long packedPos) {
        super(dimID, packedPos);
    }

    @Override
    public void draw(double draggedPixelX, double draggedPixelY, GridRenderer gridRenderer, float drawScale,
        double fontScale, double rotation) {
        int pollution = proxy.pollution.getCache(dimID).get(packedPos);
        if (pollution <= 0) return;

        // This gets the size of a block and the pixel corresponding to the center of a polluted chunk
        final double blockSize = Math.pow(2, gridRenderer.getZoom());
        final Point2D.Double blockAsPixel = gridRenderer.getBlockPixelInGrid(getBlockX(), getBlockZ());
        final Point2D.Double pixel = new Point2D.Double(
            blockAsPixel.getX() + draggedPixelX,
            blockAsPixel.getY() + draggedPixelY);

        // Set color and alpha of pollution
        int borderColor = Integer.decode(GOConfig.color);

        int steps = (int) lerp(0, GOConfig.alphaSteps, (float) pollution / POLLUTION_MAX.val);
        steps = min(steps, GOConfig.alphaSteps);
        final int pollutionAlpha = Math.round(GOConfig.maxAlpha * steps / GOConfig.alphaSteps);

        // Actually draw the chunk overlay
        DrawUtil.drawRectangle(
            pixel.getX(),
            pixel.getY(),
            Constants.CHUNK_SIZE * blockSize,
            Constants.CHUNK_SIZE * blockSize,
            borderColor,
            pollutionAlpha);

        // Draw a label on it.
        if (alwaysShowAmt) {
            String label = FancyText.formatPollution(pollution);

            DrawUtil.drawLabel(
                label,
                pixel.getX() + Constants.CHUNK_SIZE * 0.5 * blockSize,
                pixel.getY() + Constants.CHUNK_SIZE * 0.5 * blockSize,
                DrawUtil.HAlign.Center,
                DrawUtil.VAlign.Middle,
                Constants.TEXT_BG_COLOR,
                Constants.TEXT_BG_ALPHA,
                Constants.TEXT_COLOR,
                Constants.TEXT_ALPHA,
                fontScale,
                false,
                rotation);
        }
    }
}
