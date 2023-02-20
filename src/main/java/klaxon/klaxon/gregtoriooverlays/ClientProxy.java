package klaxon.klaxon.gregtoriooverlays;

import cpw.mods.fml.common.event.*;
import cpw.mods.fml.relauncher.Side;
import gregtech.GT_Mod;
import klaxon.klaxon.gregtoriooverlays.utils.network.pollution.PollutionMessage;
import klaxon.klaxon.gregtoriooverlays.utils.network.pollution.PollutionMessageHandler;
import klaxon.klaxon.gregtoriooverlays.visualprospecting.RegisterOverlays;

public class ClientProxy extends CommonProxy {

    // preInit "Run before anything else. Read your config, create blocks, items,
    // etc, and register them with the GameRegistry."
    public void preInit(FMLPreInitializationEvent event) {

        // Register packet
        GregtorioOverlays.dispatcher.registerMessage(
                PollutionMessageHandler.class,
                PollutionMessage.class,
                GregtorioOverlays.pollutionMessageId,
                Side.CLIENT);

        super.preInit(event);
    }

    // load "Do your mod setup. Build whatever data structures you care about. Register recipes."
    public void init(FMLInitializationEvent event) {
        super.init(event);
    }

    // postInit "Handle interaction with other mods, complete your setup based on this."
    public void postInit(FMLPostInitializationEvent event) {

        super.postInit(event);

        // Check if pollution is enabled on client
        if (!GT_Mod.gregtechproxy.mPollution) {

            GregtorioOverlays.error("Pollution is not enabled! Pollution overlay is disabled.");
            GregtorioOverlays.error("You can remove this mod (for now).");
            GregtorioOverlays.error("TODO: add non-pollution overlays.");
        } else {

            // Register the pollution overlay
            RegisterOverlays.registerPollutionOverlay();
        }
    }

    public void serverAboutToStart(FMLServerAboutToStartEvent event) {
        super.serverAboutToStart(event);
    }

    // register server commands in this event handler
    public void serverStarting(FMLServerStartingEvent event) {
        super.serverStarting(event);
    }

    public void serverStarted(FMLServerStartedEvent event) {
        super.serverStarted(event);
    }

    public void serverStopping(FMLServerStoppingEvent event) {
        super.serverStopping(event);
    }

    public void serverStopped(FMLServerStoppedEvent event) {
        super.serverStopped(event);
    }
}
