package klaxon.klaxon.goverlays;

import static klaxon.klaxon.goverlays.GregtorioOverlays.*;

import com.gtnewhorizon.gtnhlib.config.ConfigException;
import com.gtnewhorizon.gtnhlib.config.ConfigurationManager;

import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import klaxon.klaxon.goverlays.network.pollution.PollutionMessage;
import klaxon.klaxon.goverlays.network.pollution.PollutionMessageHandler;

public class CommonProxy {

    public final PollutionManager pollution = new PollutionManager();

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

    @SubscribeEvent
    public void onServerTick(TickEvent.WorldTickEvent tickEvent) {
        if (tickEvent.side != Side.SERVER) return;
        if (tickEvent.world.getTotalWorldTime() % ticksPerUpdate != ticksPerUpdate / 2) return;

        // Dispatch! Chunks in the cache should be updated on change by the update backend
        pollution.updateDim(tickEvent.world.provider.dimensionId);
    }
}
