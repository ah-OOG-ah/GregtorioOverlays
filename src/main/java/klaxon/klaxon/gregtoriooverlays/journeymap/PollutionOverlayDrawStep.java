package klaxon.klaxon.gregtoriooverlays.journeymap;

import static klaxon.klaxon.gregtoriooverlays.GregtorioOverlays.EffectSteps.*;

import java.awt.geom.Point2D;
import journeymap.client.render.draw.DrawStep;
import journeymap.client.render.draw.DrawUtil;
import journeymap.client.render.map.GridRenderer;
import klaxon.klaxon.gregtoriooverlays.GregtorioOverlays;
import klaxon.klaxon.gregtoriooverlays.utils.FancyText;
import klaxon.klaxon.gregtoriooverlays.utils.Numeric;
import klaxon.klaxon.gregtoriooverlays.visualprospecting.model.PollutionChunkLocation;

/**
 * Draws a polluted chunk... this is gonna be PAIN
 */
public class PollutionOverlayDrawStep implements DrawStep {

    private final PollutionChunkLocation pollutionChunkLocation;

    public PollutionOverlayDrawStep(PollutionChunkLocation pollutionChunkLocation) {

        this.pollutionChunkLocation = pollutionChunkLocation;
    }

    @Override
    public void draw(
            double draggedPixelX,
            double draggedPixelY,
            GridRenderer gridRenderer,
            float drawScale,
            double fontScale,
            double rotation) {

        double pollution = pollutionChunkLocation.getPollution();

        if (pollution > 0) {

            // This gets the size of a block and the pixel corresponding to the center of a polluted chunk
            final double blockSize = Math.pow(2, gridRenderer.getZoom());
            final Point2D.Double blockAsPixel = gridRenderer.getBlockPixelInGrid(
                    pollutionChunkLocation.getBlockX(), pollutionChunkLocation.getBlockZ());
            final Point2D.Double pixel =
                    new Point2D.Double(blockAsPixel.getX() + draggedPixelX, blockAsPixel.getY() + draggedPixelY);

            // Set color and alpha of pollution
            int borderColor = GregtorioOverlays.POLLUTION_COLOR;
            // Have yet to decide whether pollution should be smooth
            // This supports an arbitrary number of steps from [1, POLLUTION_MAX_ALPHA]
            // In practice, some steps might not have different alpha values due to rounding
            int steps =
                    Math.round(((float) pollution / POLLUTION_MAX.pollution) * GregtorioOverlays.POLLUTION_ALPHA_STEPS);
            if (steps > GregtorioOverlays.POLLUTION_ALPHA_STEPS) {

                steps = (int) GregtorioOverlays.POLLUTION_ALPHA_STEPS;
            }
            final int pollutionAlpha =
                    Math.round(GregtorioOverlays.POLLUTION_MAX_ALPHA * steps / GregtorioOverlays.POLLUTION_ALPHA_STEPS);

            // Actually draw the chunk overlay
            DrawUtil.drawRectangle(
                    pixel.getX(),
                    pixel.getY(),
                    GregtorioOverlays.CHUNK_SIZE * blockSize,
                    GregtorioOverlays.CHUNK_SIZE * blockSize,
                    borderColor,
                    pollutionAlpha);

            // Draw a label on it
            boolean drawShadow = false;
            String label = "Placeholder";

            if (!GregtorioOverlays.USE_DOUBLE_PREFIXES) {

                if (GregtorioOverlays.prefixes == FancyText.PrefixType.SI) {

                    // Convert pollution back to base unit (BBL). It's stored in GiBBL, or 1024^3
                    long siPollution = (long) pollution * (long) Math.pow(1024, 3);

                    // Figure out what SI power it should be in
                    // 0 = none, 1 = kilo, 2 = Mega, etc.
                    int power = (int) Math.floor(Numeric.log(siPollution, 1000));

                    // Get displayed pollution
                    pollution = siPollution / Math.pow(1000, power);

                    // Figure out how many decimals to have
                    // Decimals before the point, will be between 1 and 3
                    byte decimals = (byte) (Math.floor(Math.log(pollution)) + 1);
                    decimals = (byte) (5 - decimals); // And subtract it from 5 (the target length)

                    // Truncate
                    pollution = Numeric.round(pollution, decimals);

                    // Get the prefix
                    String prefix = FancyText.siPrefixes[power];

                    // Make the label
                    String sPollution = GregtorioOverlays.numFormat.format(pollution);
                    label = sPollution + " " + prefix + "bbl";

                } else {

                    // Get the binary power
                    // 0 = Gi, 1 = Ti, etc.
                    byte power = (byte) Math.floor(Numeric.log(pollution, 1024));

                    // Get displayed pollution
                    pollution /= Math.pow(1024, power);

                    // Figure out how many decimals to have
                    // Decimals before the point, will be between 1 and 4
                    byte decimals = (byte) (Math.floor(Math.log(pollution)) + 1);
                    decimals = (byte) (5 - decimals); // And subtract it from 5 (the target length)

                    // Truncate
                    pollution = Numeric.round(pollution, decimals);

                    // Get prefix, add two because it starts at Gi
                    String prefix = FancyText.binaryPrefixes[power + 2];

                    // Make the label
                    String sPollution = GregtorioOverlays.numFormat.format(pollution);
                    label = sPollution + " " + prefix + "bbl";
                }
            }

            DrawUtil.drawLabel(
                    label,
                    pixel.getX() + GregtorioOverlays.CHUNK_SIZE * 0.5 * blockSize,
                    pixel.getY() + GregtorioOverlays.CHUNK_SIZE * 0.5 * blockSize,
                    DrawUtil.HAlign.Center,
                    DrawUtil.VAlign.Middle,
                    GregtorioOverlays.TEXT_BG_COLOR,
                    GregtorioOverlays.TEXT_BG_ALPHA,
                    GregtorioOverlays.TEXT_COLOR,
                    GregtorioOverlays.TEXT_ALPHA,
                    fontScale,
                    drawShadow,
                    rotation);
        }
    }
}
