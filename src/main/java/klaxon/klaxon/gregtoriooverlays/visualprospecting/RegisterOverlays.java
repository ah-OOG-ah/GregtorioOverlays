package klaxon.klaxon.gregtoriooverlays.visualprospecting;

import klaxon.klaxon.gregtoriooverlays.journeymap.PollutionOverlayButton;
import klaxon.klaxon.gregtoriooverlays.journeymap.PollutionOverlayRenderer;
import klaxon.klaxon.gregtoriooverlays.visualprospecting.model.PollutionOverlayButtonManager;
import klaxon.klaxon.gregtoriooverlays.visualprospecting.model.PollutionOverlayLayerManager;

import com.sinthoras.visualprospecting.VisualProspecting_API;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RegisterOverlays {

    public static void registerPollutionOverlay() {

        // Register logical button and layer
        VisualProspecting_API.LogicalClient.registerCustomButtonManager(
            PollutionOverlayButtonManager.instance);
        VisualProspecting_API.LogicalClient.registerCustomLayer(
            PollutionOverlayLayerManager.instance);

        // Register JourneyMap button and renderer
        VisualProspecting_API.LogicalClient.registerJourneyMapButton(
            PollutionOverlayButton.instance);
        VisualProspecting_API.LogicalClient.registerJourneyMapRenderer(
            PollutionOverlayRenderer.instance);
    }
}
