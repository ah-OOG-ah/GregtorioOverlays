package klaxon.klaxon.goverlays;

import static klaxon.klaxon.goverlays.GregtorioOverlays.LOGGER;
import static klaxon.klaxon.goverlays.GregtorioOverlays.SIDE;

import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import gregtech.GT_Mod;
import klaxon.klaxon.goverlays.visualprospecting.RegisterOverlays;

public class ClientProxy extends CommonProxy {

    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);

        SIDE = Side.CLIENT;

        // Check if pollution is enabled on client
        if (!GT_Mod.gregtechproxy.mPollution) {

            LOGGER.error("Pollution is not enabled! Pollution overlay is disabled.");
            LOGGER.error("You can remove this mod (for now).");
            LOGGER.error("TODO: add non-pollution overlays.");
        } else {

            // Register the pollution overlay
            RegisterOverlays.registerPollutionOverlay();
        }
    }
}
