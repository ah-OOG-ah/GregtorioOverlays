package klaxon.klaxon.goverlays;

import static klaxon.klaxon.goverlays.GregtorioOverlays.LOGGER;

import com.gtnewhorizon.gtnhlib.config.ConfigException;
import com.gtnewhorizon.gtnhlib.config.ConfigurationManager;

import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import klaxon.klaxon.goverlays.utils.network.pollution.PollutionMessage;
import klaxon.klaxon.goverlays.utils.network.pollution.PollutionMessageHandler;

public class CommonProxy {
    public void preInit(FMLPreInitializationEvent event) {

        try {
            ConfigurationManager.registerConfig(GOConfig.class);
        } catch (ConfigException e) {
            throw new RuntimeException(e);
        }

        LOGGER.info("Come with me/and you can see/the results of your industrialization!");

        LOGGER.info("Registering packets...");
        GregtorioOverlays.dispatcher.registerMessage(
            PollutionMessageHandler.class,
            PollutionMessage.class,
            GregtorioOverlays.pollutionMessageId,
            event.getSide());
    }

    public void postInit(FMLPostInitializationEvent event) {}
}
