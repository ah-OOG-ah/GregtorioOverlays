package klaxon.klaxon.goverlays.navigator.journeymap;

import java.util.List;

import com.gtnewhorizons.navigator.api.journeymap.render.JMLayerRenderer;
import com.gtnewhorizons.navigator.api.model.locations.ILocationProvider;
import com.gtnewhorizons.navigator.api.model.steps.RenderStep;

import it.unimi.dsi.fastutil.objects.ObjectImmutableList;
import klaxon.klaxon.goverlays.navigator.PollutionChunkLocation;
import klaxon.klaxon.goverlays.navigator.PollutionLayerManager;

public class PollutionLayerRenderer extends JMLayerRenderer {

    public static final PollutionLayerRenderer INSTANCE = new PollutionLayerRenderer();

    public PollutionLayerRenderer() {
        super(PollutionLayerManager.INSTANCE);
    }

    @Override
    protected List<? extends RenderStep> generateRenderSteps(List<? extends ILocationProvider> visibleElements) {
        return visibleElements.stream()
            .map(element -> new PollutionDrawStep((PollutionChunkLocation) element))
            .collect(ObjectImmutableList.toListWithExpectedSize(visibleElements.size()));
    }
}
