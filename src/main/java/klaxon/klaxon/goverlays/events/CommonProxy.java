package klaxon.klaxon.goverlays.events;

import static klaxon.klaxon.goverlays.GregtorioOverlays.LOGGER;
import static klaxon.klaxon.goverlays.compat.Compat.BACKEND;
import static klaxon.klaxon.goverlays.compat.Compat.ENABLED;

import com.gtnewhorizon.gtnhlib.config.ConfigException;
import com.gtnewhorizon.gtnhlib.config.ConfigurationManager;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import klaxon.klaxon.goverlays.Backend;
import klaxon.klaxon.goverlays.GregtorioOverlays;
import klaxon.klaxon.goverlays.PollutionManager;
import klaxon.klaxon.goverlays.compat.GT5U;
import klaxon.klaxon.goverlays.config.GOConfig;
import klaxon.klaxon.goverlays.network.pollution.PollutionMessage;
import klaxon.klaxon.goverlays.network.pollution.PollutionMessageHandler;

public class CommonProxy {

    public final PollutionManager pollution = new PollutionManager();
    private final CommonEvents handler = new CommonEvents();

    public void preInit(FMLPreInitializationEvent event) {
        LOGGER.info("Come with me/and you can see/the results of your industrialization!");

        try {
            ConfigurationManager.registerConfig(GOConfig.class);
        } catch (ConfigException e) {
            throw new RuntimeException(e);
        }

        // Hello? Is this thing on?
        if (BACKEND == Backend.GT5U && !GT5U.isPollutionOn()) {
            ENABLED = false;

            LOGGER.warn("Pollution is disabled!");
            LOGGER.warn("Not sure why I'm here...");
            // TODO: add non-pollution overlays
        }

        if (ENABLED) {
            switch (BACKEND) {
                case GT5U -> GT5U.preInit();
                case HBM_NTM, NONE -> {}
            }
        }

        LOGGER.info("Registering packets...");
        GregtorioOverlays.dispatcher.registerMessage(
            PollutionMessageHandler.class,
            PollutionMessage.class,
            GregtorioOverlays.pollutionMessageId,
            event.getSide());

        FMLCommonHandler.instance()
            .bus()
            .register(handler);
    }

    public void postInit(FMLPostInitializationEvent event) {}
}
