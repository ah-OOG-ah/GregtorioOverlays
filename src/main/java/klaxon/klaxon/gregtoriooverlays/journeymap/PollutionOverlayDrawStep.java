package klaxon.klaxon.gregtoriooverlays.journeymap;

import journeymap.client.render.draw.DrawStep;
import journeymap.client.render.draw.DrawUtil;
import journeymap.client.render.map.GridRenderer;
import klaxon.klaxon.gregtoriooverlays.GregtorioOverlays;
import klaxon.klaxon.gregtoriooverlays.utils.FancyText;
import klaxon.klaxon.gregtoriooverlays.utils.Numeric;
import klaxon.klaxon.gregtoriooverlays.visualprospecting.model.PollutionChunkLocation;

import java.awt.geom.Point2D;

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
        GregtorioOverlays.debug("Drawing a drawStep");

        double pollution = pollutionChunkLocation.getPollution();

        if (pollution > 0) {

            // This gets the size of a block and the pixel corresponding to the center of a polluted chunk
            final double blockSize = Math.pow(2, gridRenderer.getZoom());
            final Point2D.Double blockAsPixel = gridRenderer.getBlockPixelInGrid(
                pollutionChunkLocation.getBlockX(), pollutionChunkLocation.getBlockZ());
            final Point2D.Double pixel =
                new Point2D.Double(blockAsPixel.getX() + draggedPixelX,
                    blockAsPixel.getY() + draggedPixelY);

            // Set color and alpha of pollution
            int borderColor = GregtorioOverlays.POLLUTION_COLOR;
            // Have yet to decide whether pollution should be smooth
            // This supports an arbitrary number of steps from [1, POLLUTION_MAX_ALPHA]
            // In practice, some steps might not have different alpha values due to rounding
            int steps = Math.round(((float)pollution/ GregtorioOverlays.POLLUTION_MAX) * GregtorioOverlays.POLLUTION_ALPHA_STEPS);
            if (steps > GregtorioOverlays.POLLUTION_ALPHA_STEPS) {

                steps = (int) GregtorioOverlays.POLLUTION_ALPHA_STEPS;
            }
            final int pollutionAlpha = Math.round(GregtorioOverlays.POLLUTION_MAX_ALPHA * steps/ GregtorioOverlays.POLLUTION_ALPHA_STEPS);

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

                    // Get the power
                    int power = (int)Math.floor(Math.log10(siPollution));

                    // Get the prefix and truncate
                    // Do a magic trick! power is log10, but for SI we need log1000
                    // Divide it by log10(1000) to get log1000
                    // Numeric.log does this but I need the intermediate value too to determine num of decimals
                    // power % 3 is the number of digits left after truncation, minus 1
                    // So 3 - power % 3 is the number of decimals to round to to keep 5 chars (x.xxx through xxx.x)
                    // long/long technically gives long, but these longs SHOULD always give dividend in set (0, 1000)
                    pollution = Numeric.round(siPollution/((long)Math.pow(1000, (power/Math.log10(1000)))),
                                              3 - power % 3);
                    String prefix = FancyText.siPrefixes[power/(int)Math.log10(1000)];

                    // Make the label
                    String sPollution = GregtorioOverlays.numFormat.format(pollution);
                    label = sPollution + " " + prefix + "bbl";

                    // There's definitely a better way to do this, but I was bored so excessively complex math it is!
                    // And yes, log10(1000) is always 3. It's written out so I can read it at 2AM.

                } else if (GregtorioOverlays.prefixes == FancyText.PrefixType.BINARY) {

                    // Get binary prefix. No need to break it down, it's already in binary
                    int power = (int)Math.floor(Numeric.log(pollution, 1024));

                    // Get prefix and truncate
                    // Pull a similar magic trick
                    // But this time less nonsense
                    int digits = (int)Math.floor(Math.log10(pollution/Math.pow(1024, power)));
                    pollution = Numeric.round(pollution/Math.pow(1024, power), 3 - power % 3);
                    // Add two because it starts at Gi
                    String prefix = FancyText.binaryPrefixes[power + 2];

                    String sPollution = GregtorioOverlays.numFormat.format(pollution);
                    label = sPollution + " " + prefix + "bbl";
                } else {

                    GregtorioOverlays.error("Neither SI nor binary prefixes selected!");
                    GregtorioOverlays.error("Defaulting to binary");

                    // Get binary prefix. No need to break it down, it's already in binary
                    int power = (int)Math.floor(Numeric.log(pollution, 1024));

                    // Get prefix and truncate
                    // Pull a similar magic trick
                    // But this time less nonsense
                    int digits = (int)Math.floor(Math.log10(pollution/Math.pow(1024, power)));
                    pollution = Numeric.round(pollution/Math.pow(1024, power), 3 - power % 3);
                    // Add two because it starts at Gi
                    String prefix = FancyText.binaryPrefixes[power + 2];

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
