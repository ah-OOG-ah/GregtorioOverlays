package klaxon.klaxon.goverlays.events;

import java.util.List;
import java.util.stream.Collectors;

import net.minecraft.client.settings.KeyBinding;

import org.lwjgl.input.Keyboard;

import com.sinthoras.visualprospecting.VisualProspecting_API;
import com.sinthoras.visualprospecting.integration.journeymap.buttons.LayerButton;
import com.sinthoras.visualprospecting.integration.journeymap.render.LayerRenderer;
import com.sinthoras.visualprospecting.integration.model.locations.ILocationProvider;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import journeymap.client.render.draw.DrawStep;
import klaxon.klaxon.goverlays.journeymap.PollutionDrawStep;
import klaxon.klaxon.goverlays.visualprospecting.model.PollutionChunkLocation;
import klaxon.klaxon.goverlays.visualprospecting.model.PollutionOverlayLayerManager;

@SuppressWarnings("unused")
public class ClientProxy extends CommonProxy {

    public static KeyBinding toggleLabels;
    private static final String KEYBIND_CATEGORY = "goverlays.key.category";

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);

        // Register a keybind for labels on the overlay
        toggleLabels = new KeyBinding("Toggle labels on the pollution overlay", Keyboard.KEY_P, KEYBIND_CATEGORY);
        ClientRegistry.registerKeyBinding(toggleLabels);

        FMLCommonHandler.instance()
            .bus()
            .register(new ClientEvents());
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);

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
            });
    }
}
