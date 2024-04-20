package klaxon.klaxon.goverlays.journeymap;

import static klaxon.klaxon.goverlays.Constants.EffectSteps.POLLUTION_MAX;

import java.awt.geom.Point2D;

import journeymap.client.render.draw.DrawStep;
import journeymap.client.render.draw.DrawUtil;
import journeymap.client.render.map.GridRenderer;
import klaxon.klaxon.goverlays.Constants;
import klaxon.klaxon.goverlays.GOConfig;
import klaxon.klaxon.goverlays.utils.FancyText;
import klaxon.klaxon.goverlays.visualprospecting.model.PollutionChunkLocation;

public class PollutionDrawStep implements DrawStep {

    private final PollutionChunkLocation pollutionChunkLocation;

    public PollutionDrawStep(PollutionChunkLocation pollutionChunkLocation) {

        this.pollutionChunkLocation = pollutionChunkLocation;
    }

    @Override
    public void draw(double draggedPixelX, double draggedPixelY, GridRenderer gridRenderer, float drawScale,
        double fontScale, double rotation) {

        int pollution = pollutionChunkLocation.getPollution();

        if (pollution > 0) {

            // This gets the size of a block and the pixel corresponding to the center of a polluted chunk
            final double blockSize = Math.pow(2, gridRenderer.getZoom());
            final Point2D.Double blockAsPixel = gridRenderer
                .getBlockPixelInGrid(pollutionChunkLocation.getBlockX(), pollutionChunkLocation.getBlockZ());
            final Point2D.Double pixel = new Point2D.Double(
                blockAsPixel.getX() + draggedPixelX,
                blockAsPixel.getY() + draggedPixelY);

            // Set color and alpha of pollution
            int borderColor = Integer.decode(GOConfig.color);

            // Have yet to decide whether pollution should be smooth
            // This supports an arbitrary number of steps from [1, POLLUTION_MAX_ALPHA]
            // If more than POLLUTION_MAX_ALPHA steps are specified, some steps will have the same alpha values due to
            // rounding
            // Steps are clamped to the configured max
            int steps = Math.round(((float) pollution) / POLLUTION_MAX.pollution * GOConfig.alphaSteps);
            steps = (steps > GOConfig.alphaSteps) ? (int) GOConfig.alphaSteps : steps;

            final int pollutionAlpha = Math.round(GOConfig.maxAlpha * steps / GOConfig.alphaSteps);

            // Actually draw the chunk overlay
            DrawUtil.drawRectangle(
                pixel.getX(),
                pixel.getY(),
                Constants.CHUNK_SIZE * blockSize,
                Constants.CHUNK_SIZE * blockSize,
                borderColor,
                pollutionAlpha);

            // Draw a label on it
            boolean drawShadow = false;
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
                drawShadow,
                rotation);
        }
    }
}
