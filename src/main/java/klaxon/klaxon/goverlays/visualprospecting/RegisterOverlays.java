package klaxon.klaxon.goverlays.visualprospecting;

import com.sinthoras.visualprospecting.VisualProspecting_API;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import klaxon.klaxon.goverlays.journeymap.PollutionOverlayButton;
import klaxon.klaxon.goverlays.journeymap.PollutionOverlayRenderer;
import klaxon.klaxon.goverlays.visualprospecting.model.PollutionOverlayButtonManager;
import klaxon.klaxon.goverlays.visualprospecting.model.PollutionOverlayLayerManager;

@SideOnly(Side.CLIENT)
public class RegisterOverlays {

    public static void registerPollutionOverlay() {

        // Register logical button and layer
        VisualProspecting_API.LogicalClient.registerCustomButtonManager(PollutionOverlayButtonManager.instance);
        VisualProspecting_API.LogicalClient.registerCustomLayer(PollutionOverlayLayerManager.instance);

        // Register JourneyMap button and renderer
        VisualProspecting_API.LogicalClient.registerJourneyMapButton(PollutionOverlayButton.instance);
        VisualProspecting_API.LogicalClient.registerJourneyMapRenderer(PollutionOverlayRenderer.instance);
    }
}
