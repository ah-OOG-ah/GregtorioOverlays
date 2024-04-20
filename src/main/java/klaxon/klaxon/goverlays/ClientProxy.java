package klaxon.klaxon.goverlays;

import static klaxon.klaxon.goverlays.GregtorioOverlays.LOGGER;

import com.sinthoras.visualprospecting.VisualProspecting_API;

import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import gregtech.GT_Mod;
import klaxon.klaxon.goverlays.journeymap.PollutionOverlayButton;
import klaxon.klaxon.goverlays.journeymap.PollutionOverlayRenderer;
import klaxon.klaxon.goverlays.visualprospecting.model.PollutionOverlayLayerManager;

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
            VisualProspecting_API.LogicalClient.registerJourneyMapButton(PollutionOverlayButton.instance);
            VisualProspecting_API.LogicalClient.registerJourneyMapRenderer(PollutionOverlayRenderer.instance);
        }
    }
}
