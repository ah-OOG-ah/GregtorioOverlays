package klaxon.klaxon.goverlays.journeymap;

import java.util.ArrayList;
import java.util.List;

import com.sinthoras.visualprospecting.integration.journeymap.render.LayerRenderer;
import com.sinthoras.visualprospecting.integration.model.locations.ILocationProvider;

import journeymap.client.render.draw.DrawStep;
import klaxon.klaxon.goverlays.visualprospecting.model.PollutionChunkLocation;
import klaxon.klaxon.goverlays.visualprospecting.model.PollutionOverlayLayerManager;

public class PollutionOverlayRenderer extends LayerRenderer {

    public static final PollutionOverlayRenderer instance = new PollutionOverlayRenderer();

    public PollutionOverlayRenderer() {

        super(PollutionOverlayLayerManager.instance);
    }

    @Override
    public List<? extends DrawStep> mapLocationProviderToDrawStep(List<? extends ILocationProvider> visibleElements) {

        final List<PollutionOverlayDrawStep> drawSteps = new ArrayList<>();

        visibleElements.stream()
            .map(element -> (PollutionChunkLocation) element)
            .forEach(location -> drawSteps.add(new PollutionOverlayDrawStep(location)));
        return drawSteps;
    }
}
