package klaxon.klaxon.goverlays;

import com.gtnewhorizon.gtnhlib.config.ConfigException;
import com.gtnewhorizon.gtnhlib.config.ConfigurationManager;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerAboutToStartEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppedEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.relauncher.Side;
import klaxon.klaxon.goverlays.utils.network.pollution.DummyPollutionMessageHandler;
import klaxon.klaxon.goverlays.utils.network.pollution.PollutionMessage;

public class CommonProxy {

    // preInit "Run before anything else. Read your config, create blocks, items,
    // etc, and register them with the GameRegistry."
    public void preInit(FMLPreInitializationEvent event) {

        try {
            ConfigurationManager.registerConfig(GOConfig.class);
        } catch (ConfigException e) {
            throw new RuntimeException(e);
        }

        GregtorioOverlays.info("Come with me/and you can see/the results of your industrialization!");

        GregtorioOverlays.info("Registering packets...");
        GregtorioOverlays.dispatcher.registerMessage(
            DummyPollutionMessageHandler.class,
            PollutionMessage.class,
            GregtorioOverlays.pollutionMessageId,
            Side.SERVER);
    }

    // load "Do your mod setup. Build whatever data structures you care about. Register recipes."
    public void init(FMLInitializationEvent event) {}

    // postInit "Handle interaction with other mods, complete your setup based on this."
    public void postInit(FMLPostInitializationEvent event) {}

    public void serverAboutToStart(FMLServerAboutToStartEvent event) {}

    // register server commands in this event handler
    public void serverStarting(FMLServerStartingEvent event) {}

    public void serverStarted(FMLServerStartedEvent event) {}

    public void serverStopping(FMLServerStoppingEvent event) {}

    public void serverStopped(FMLServerStoppedEvent event) {}
}
