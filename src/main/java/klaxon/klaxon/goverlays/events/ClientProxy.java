package klaxon.klaxon.goverlays.events;

import net.minecraft.client.settings.KeyBinding;

import org.lwjgl.input.Keyboard;

import com.gtnewhorizons.navigator.api.NavigatorApi;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import klaxon.klaxon.goverlays.navigator.PollutionLayerManager;

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
        NavigatorApi.registerLayerManager(PollutionLayerManager.INSTANCE);
    }
}
