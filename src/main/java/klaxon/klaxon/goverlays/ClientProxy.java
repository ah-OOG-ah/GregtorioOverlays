package klaxon.klaxon.goverlays;

import static klaxon.klaxon.goverlays.GregtorioOverlays.LOGGER;

import java.util.List;
import java.util.stream.Collectors;

import com.sinthoras.visualprospecting.VisualProspecting_API;
import com.sinthoras.visualprospecting.integration.journeymap.buttons.LayerButton;
import com.sinthoras.visualprospecting.integration.journeymap.render.LayerRenderer;
import com.sinthoras.visualprospecting.integration.model.locations.ILocationProvider;

import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import gregtech.GT_Mod;
import journeymap.client.render.draw.DrawStep;
import klaxon.klaxon.goverlays.journeymap.PollutionDrawStep;
import klaxon.klaxon.goverlays.visualprospecting.model.PollutionChunkLocation;
import klaxon.klaxon.goverlays.visualprospecting.model.PollutionOverlayLayerManager;

@SuppressWarnings("unused")
public class ClientProxy extends CommonProxy {

    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);

        // Check if pollution is enabled on client
        if (!GT_Mod.gregtechproxy.mPollution) {

            LOGGER.error("Pollution is not enabled! Pollution overlay is disabled.");
            LOGGER.error("You can remove this mod (for now).");
            LOGGER.error("TODO: add non-pollution overlays.");
        } else {

            // Register the pollution overlay
            // Register logical button and layer
            VisualProspecting_API.LogicalClient.registerCustomButtonManager(PollutionOverlayLayerManager.buttonMgr);
            VisualProspecting_API.LogicalClient.registerCustomLayer(PollutionOverlayLayerManager.instance);

            // Register JourneyMap button and renderer
            VisualProspecting_API.LogicalClient
                .registerJourneyMapButton(new LayerButton(PollutionOverlayLayerManager.buttonMgr));
            VisualProspecting_API.LogicalClient
                .registerJourneyMapRenderer(new LayerRenderer(PollutionOverlayLayerManager.instance) {

                    @Override
                    protected List<? extends DrawStep> mapLocationProviderToDrawStep(
                        List<? extends ILocationProvider> visibleElements) {
                        return visibleElements.stream()
                            .map(loc -> new PollutionDrawStep((PollutionChunkLocation) loc))
                            .collect(Collectors.toList());
                    }
                });// */
        }
    }
}
