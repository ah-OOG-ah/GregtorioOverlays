package klaxon.klaxon.goverlays.journeymap;

import static java.lang.Math.min;
import static klaxon.klaxon.goverlays.Constants.EffectSteps.POLLUTION_MAX;
import static klaxon.klaxon.goverlays.GOConfig.alwaysShowAmt;
import static org.joml.Math.lerp;
import static org.joml.Math.max;

import java.awt.geom.Point2D;
import java.util.List;

import com.sinthoras.visualprospecting.integration.journeymap.drawsteps.ClickableDrawStep;
import com.sinthoras.visualprospecting.integration.model.locations.IWaypointAndLocationProvider;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import journeymap.client.render.draw.DrawUtil;
import journeymap.client.render.map.GridRenderer;
import klaxon.klaxon.goverlays.Constants;
import klaxon.klaxon.goverlays.GOConfig;
import klaxon.klaxon.goverlays.utils.FancyText;
import klaxon.klaxon.goverlays.visualprospecting.model.PollutionChunkLocation;
import net.minecraft.client.gui.FontRenderer;

public class PollutionDrawStep implements ClickableDrawStep {

    private final double blockX;
    private final double blockZ;

    private final int pollution;

    private double minX;
    private double minY;
    private double maxX;
    private double maxY;

    public PollutionDrawStep(PollutionChunkLocation loc) {

        this.blockX = loc.getBlockX();
        this.blockZ = loc.getBlockZ();
        this.pollution = loc.getPollution();
    }

    @Override
    public void draw(double draggedPixelX, double draggedPixelY, GridRenderer gridRenderer, float drawScale,
        double fontScale, double rotation) {

        if (pollution > 0) {

            // This gets the size of a block and the pixel corresponding to the center of a polluted chunk
            final double blockSize = Math.pow(2, gridRenderer.getZoom());
            final Point2D.Double blockAsPixel = gridRenderer
                .getBlockPixelInGrid(blockX, blockZ);
            final Point2D.Double pixel = new Point2D.Double(
                blockAsPixel.getX() + draggedPixelX,
                blockAsPixel.getY() + draggedPixelY);

            // Set color and alpha of pollution
            int borderColor = Integer.decode(GOConfig.color);

            int steps = (int) lerp(0, GOConfig.alphaSteps, (float) pollution / POLLUTION_MAX.val);
            steps = min(steps, GOConfig.alphaSteps);
            final int pollutionAlpha = Math.round(GOConfig.maxAlpha * steps / GOConfig.alphaSteps);

            // Set the bounds
            minX = pixel.getX();
            minY = pixel.getY();
            maxX = minX + Constants.CHUNK_SIZE * blockSize;
            maxY = minY + Constants.CHUNK_SIZE * blockSize;

            // Actually draw the chunk overlay
            DrawUtil.drawRectangle(
                pixel.getX(),
                pixel.getY(),
                Constants.CHUNK_SIZE * blockSize,
                Constants.CHUNK_SIZE * blockSize,
                borderColor,
                pollutionAlpha);

            // Draw a label on it
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

    @Override
    public List<String> getTooltip() {
        var ret = new ObjectArrayList<String>(1);
        ret.add(FancyText.formatPollution(pollution));
        return ret;
    }

    @Override
    public void drawTooltip(FontRenderer fontRenderer, int mouseX, int mosueY, int displayWidth, int displayHeight) {}

    @Override
    public boolean isMouseOver(int mouseX, int mouseY) {
        return mouseX >= minX && mouseX < maxX && mouseY >= minY && mouseY < maxY;
    }

    @Override
    public void onActionKeyPressed() { }

    @Override
    public IWaypointAndLocationProvider getLocationProvider() { return null; }
}
